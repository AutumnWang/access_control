package utils.acl;

import java.security.acl.Permission;
import java.security.Principal;
import java.security.acl.*;
import java.util.*;
import java.io.*;


public class AclCreator extends OwnerCreator implements Acl {

    private Hashtable<Principal, AclEntry> allowedUsersTable = new Hashtable<> (23);
    private Hashtable<Principal, AclEntry> allowedGroupsTable = new Hashtable<> (23);
    private Hashtable<Principal, AclEntry> deniedUsersTable = new Hashtable<> (23);
    private Hashtable<Principal, AclEntry> deniedGroupsTable = new Hashtable<> (23);

    private String aclName = null;
    private Vector<Permission> zeroSet = new Vector<>(1,1);

    /**
     * Constructor for creating an empty ACL.
     */
    public AclCreator(Principal owner, String name){
        super(owner);
        try {
            setName(owner, name);
        } catch (Exception e) {}
    }

    /**
     * Sets the name of the ACL.
     * @param caller the principal who is invoking this method.
     * @param name the name of the ACL.
     * @exception NotOwnerException if the caller principal is not on the owners list of Acl.
     */
    public void setName(Principal caller, String name) throws NotOwnerException{
        if(!isOwner(caller))
            throw new NotOwnerException();

        this.aclName = name;
    }

    /**
     * Returns the name of the Acl
     * @return the name of the Acl.
     */
    public String getName() {
        return this.aclName;
    }

    /**
     * Adds an ACL entry to this ACL.
     * An entry associates a group or a principal with a set of permissions.
     * Each user or group can have one positive ACL entry and one negative ACL entry.
     * If there is one of the type (negative or positive) already in the table, a false value is returned.
     * The caller principal must be a part of the owners list of the ACL in order to invoke this method.
     * @param caller the pricipal who is invoking this method.
     * @param entry the ACL entry that must be added to the ACL.
     * @return true on success, false if the entry is already present.
     * @exception NotOwnerException if the caller principal is not on the owners list of Acl.
     */
    public synchronized boolean addEntry(Principal caller, AclEntry entry) throws NotOwnerException {
        if (!isOwner(caller))
            throw new NotOwnerException();

        Hashtable<Principal, AclEntry> aclTable = findTable(entry);
        Principal key = entry.getPrincipal();

        if(aclTable.get(key) != null)
            return false;

        aclTable.put(key, entry);
        return true;
    }

    // Find the table that this entry belongs to.
    // There are 4 tables that are maintained. One each for postive and negative ACLs and one each for groups and usres.
    // This method figures out which table is the one that this AclEntry belongs to.
    private Hashtable<Principal, AclEntry> findTable(AclEntry entry) {
        Hashtable<Principal, AclEntry> aclTable = null;

        Principal p = entry.getPrincipal();
        if (p instanceof Group) {
            if (entry.isNegative())
                aclTable = deniedGroupsTable;
            else
                aclTable = allowedGroupsTable;
        } else {
            if (entry.isNegative())
                aclTable = deniedUsersTable;
            else
                aclTable = allowedUsersTable;
        }
        return aclTable;
    }

    /**
     * Removes an ACL entry from this ACL.
     * The caller principal must be part of the owners list of the ACL inorder to invoke this method
     * @param caller the principal who is invoking this method.
     * @param entry the ACL entry that must be removed from the ACL.
     * @return true on success, false if the entry is not part of the ACL.
     * @exception NotOwnerException if the caller pricipal is not the owners list of the Acl.
     */
    public synchronized boolean removeEntry(Principal caller, AclEntry entry) throws NotOwnerException {
        if (!isOwner(caller))
            throw new NotOwnerException();

        Hashtable<Principal, AclEntry> aclTable = findTable(entry);
        Principal key = entry.getPrincipal();

        AclEntry o = aclTable.remove(key);
        return(o != null);
    }


    /**
     * Return the set of allowed permissions for the specified principal.
     * Individual permissions always override the Group permissions.
     * @param user the principal for which the ACL entry is returned.
     * @return The resulting permission set that the principal is allowed.
     */

    public synchronized Enumeration<Permission> getPermissions(Principal user) {
        Enumeration<Permission> individualPositive;
        Enumeration<Permission> individualNegative;
        Enumeration<Permission> groupPositive;
        Enumeration<Permission> groupNegative;

        groupPositive = subtract(getGroupPositive(user), getGroupNegative(user));
        groupNegative = subtract(getGroupNegative(user), getGroupPositive(user));
        individualPositive = subtract(getIndividualPositive(user), getIndividualNegative(user));
        individualNegative = subtract(getIndividualNegative(user), getIndividualPositive(user));

        // net positive permission is ind_Positive + (grp_Positive - grp_Negative)
        Enumeration<Permission> temp1 = subtract(groupPositive, individualNegative);
        Enumeration<Permission> netPositive = union(individualPositive, temp1);

        // recalculate the enumeration because we lost it in performance the subtraction
        individualPositive = subtract(getIndividualPositive(user), getIndividualNegative(user));
        individualNegative = subtract(getIndividualNegative(user), getIndividualPositive(user));

        // net negative permissions is ind_Neg + (grp_Neg - ind_Pos)
        temp1 = subtract(groupNegative, individualPositive);
        Enumeration<Permission> netNegative = union(individualNegative, temp1);

        return subtract(netPositive, netNegative);
    }


    /**
     * This method checks wether or not the specified principal has the required permission.
     * If permission is denied permission false is returned, a true value is returned otherwise.
     * @param principal
     * @param permission
     * @return
     */
    public boolean checkPermission(Principal principal, Permission permission) {
        Enumeration<Permission> permSet = getPermissions(principal);

        while(permSet.hasMoreElements()) {
            Permission p = permSet.nextElement();
            if(p.equals(permission))
                return true;
        }
        return false;
    }


    /**
     * returns an enumeration of the entries in this ACL
     * @return enumeration of the entries in this ACL
     */
    public synchronized Enumeration<AclEntry> entries() {
        return new AclEnumerator(this,
                                allowedUsersTable, allowedGroupsTable,
                                deniedUsersTable, deniedGroupsTable);
    }


    /**
     * return a stringfield version of the ACL
     * @return stringfield version of the ACL
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Enumeration<AclEntry> entries = entries();
        while (entries.hasMoreElements()) {
            AclEntry entry = entries.nextElement();
            sb.append(entry.toString().trim());
            sb.append("\n");
        }

        return sb.toString();
    }





    // returns the set e1 - e2
    private Enumeration<Permission> subtract(Enumeration<Permission> e1, Enumeration<Permission> e2) {
        Vector<Permission> v = new Vector<>(20, 20);

        while (e1.hasMoreElements())
            v.addElement(e1.nextElement());

        while(e2.hasMoreElements()) {
            Permission o = e2.nextElement();
            if(v.contains(o))
                    v.removeElement(o);
        }
        return v.elements();
    }

    // return the set e1 U e2
    private static Enumeration<Permission> union(Enumeration<Permission> e1, Enumeration<Permission> e2) {
        Vector<Permission> v = new Vector<>(20, 20);

        while(e1.hasMoreElements())
            v.addElement(e1.nextElement());

        while(e2.hasMoreElements()) {
            Permission o = e2.nextElement();
            if(!v.contains(o))
                v.addElement(o);
        }
        return v.elements();
    }


    private Enumeration<Permission> getGroupPositive(Principal user) {
        Enumeration<Permission> groupPositive = zeroSet.elements();
        Enumeration<Principal> e = allowedGroupsTable.keys();

        while(e.hasMoreElements()) {
            Group g = (Group) e.nextElement();
            if (g.isMember(user)) {
                AclEntry ae = allowedGroupsTable.get(g);
                groupPositive = union(ae.permissions(), groupPositive);
            }
        }
        return groupPositive;
    }


     private Enumeration<Permission> getGroupNegative(Principal user) {
        Enumeration<Permission> groupNegative = zeroSet.elements();
        Enumeration<Principal> e = deniedGroupsTable.keys();

        while(e.hasMoreElements()) {
            Group g = (Group) e.nextElement();
            if (g.isMember(user)) {
                AclEntry ae = deniedGroupsTable.get(g);
                groupNegative = union(ae.permissions(), groupNegative);
            }
        }
        return groupNegative;
    }


    private Enumeration<Permission> getIndividualPositive(Principal user) {
        Enumeration<Permission> individualPositive = zeroSet.elements();
        AclEntry ae = allowedUsersTable.get(user);
        if(ae != null)
            individualPositive = ae.permissions();
        return individualPositive;
    }

    private Enumeration<Permission> getIndividualNegative(Principal user) {
        Enumeration<Permission> individualNegative = zeroSet.elements();
        AclEntry ae = deniedUsersTable.get(user);
        if(ae != null)
            individualNegative = ae.permissions();
        return individualNegative;
    }


    final class AclEnumerator implements Enumeration<AclEntry> {
        Acl acl;
        Enumeration<AclEntry> u1, u2, g1, g2;

        AclEnumerator(Acl acl, Hashtable<?, AclEntry> u1, Hashtable<?, AclEntry> g1,
                      Hashtable<?, AclEntry> u2, Hashtable<?, AclEntry> g2) {
            this.acl = acl;
            this.u1 = u1.elements();
            this.u2 = u2.elements();
            this.g1 = g1.elements();
            this.g2 = g2.elements();
        }

        public boolean hasMoreElements() {
            return(u1.hasMoreElements() || u2.hasMoreElements() || g1.hasMoreElements() || g2.hasMoreElements());
        }


        public AclEntry nextElement() {
            AclEntry o;
            synchronized (acl) {
                if(u1.hasMoreElements())
                    return u1.nextElement();
                if(u2.hasMoreElements())
                    return u2.nextElement();
                if(g1.hasMoreElements())
                    return g1.nextElement();
                if(g2.hasMoreElements())
                    return g2.nextElement();
            }
            throw new NoSuchElementException("Acl Enumerator");
        }
    }
}
