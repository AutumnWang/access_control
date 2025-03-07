package utils.acl;

import java.util.*;
import java.security.Principal;
import java.security.acl.*;


public class AclEntryCreator implements AclEntry {

    private Principal user = null;
    private Vector<Permission> permissionSet = new Vector<>(10, 10);
    private boolean negative = false;

    /**
     * Construct a null ACL entry
     */
    public AclEntryCreator() {

    }


    /**
     * Construct an ACL entry that associates a user with permissions in the ACL.
     * @param user The user that is associated with this entry.
     */
    public AclEntryCreator(Principal user) {
        this.user = user;
    }

    /**
     * Sets the principal in the entity. If a group or a principal had already been set, a false value is returned,
     * otherwise, a true value is returned.
     * @param user The user that is associated with this entry.
     * @return true if the principal is set, false if there is one already.
     */
    public boolean setPrincipal (Principal user){
        if(this.user != null)
            return false;
        this.user = user;
        return true;
    }


    /**
     * Return the Principal associated in this ACL entry.'
     * The method returns null if the entry uses a group instead of a principal.
     */
    public Principal getPrincipal() {
        return user;
    }



    /**
     * This method sets the ACL to have negative permissions.
     * That is the user or group is denied the permission set specified in the entry.
     */
    public void setNegativePermissions() {
        negative = true;
    }

    /**
     * Return true if this is a negative ACL
     */
    public boolean isNegative() {
        return negative;
    }


    /**
     * A principal or a group can be associated with multiple permissions.
     * This method adds a permission to the ACL entry.
     * @param permission The permission to be associated with the principal or the group in the entry.
     * @return true if the permission was added, false if the permission was already part of the permission set.
     */
    public boolean addPermission(Permission permission) {
        if(permissionSet.contains(permission))
            return false;

        permissionSet.addElement(permission);

        return true;
    }


    /**
     * The method disassociates the permission from the Principal or the Group in this ACL entry.
     * @param permission The permission to be disassociated with the principal or the group in the entry.
     * @return true if the permission is removed, false if the permission is not part of the permission set.
     */
    public boolean removePermission(Permission permission) {
        return permissionSet.removeElement(permission);
    }

    /**
     * Check if the passed permission is part of the allowed permission set in this entry.
     * @param permission The permission that has to be part of the permission set in the entry.
     * @return true if the permission passed is part of the permission set in the entry, false otherwise.
     */
    public boolean checkPermission(Permission permission){
        return permissionSet.contains(permission);
    }

    /**
     * return an enumeration of the permission in this ACL entry.
     */
    public Enumeration<Permission> permissions() {
        return permissionSet.elements();
    }

    /**
     * Return a string representation of the contents of the ACL entry.
     */
    public String toString() {
        StringBuffer s = new StringBuffer();
        if (negative)
            s.append("-");
        else
            s.append("+");
        if(user instanceof  Group)
            s.append("Group.");
        else
            s.append("User.");
        s.append(user + "=");
        Enumeration<Permission> e = permissions();
        while(e.hasMoreElements()) {
            Permission p = e.nextElement();
            s.append(p);
            if(e.hasMoreElements())
                s.append(",");
        }
        return new String(s);
    }

    /**
     * Clones an AclEntry.
     */
    @SuppressWarnings("unchecked")
    public synchronized Object clone() {
        AclEntryCreator cloned;
        cloned = new AclEntryCreator(user);
        cloned.permissionSet = (Vector<Permission>) permissionSet.clone();
        cloned.negative = negative;
        return cloned;
    }

}
