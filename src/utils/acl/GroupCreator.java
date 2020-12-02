package acl;

import java.util.*;
import java.security.*;
import java.security.acl.*;

public class GroupCreator implements Group {

    private Vector<Principal> groupMembers = new Vector<>(50, 100);
    private String group;

    /**
     * Constructs a Group Object with no members.
     * @param groupName the name of the group
     */
    public GroupCreator(String groupName) {
        this.group = groupName;
    }


    /**
     * adds the specified member to the group.
     * @param user The principal to add to the group
     * @return true if the member was added - flase if the
     * member could not be added
     */
    public boolean addMember(Principal user){
        if (groupMembers.contains(user))
            return false;

        // do not allow groups to be added to itself
        if (group.equals(user.toString()))
            throw new IllegalArgumentException();

        groupMembers.addElement(user);
        return true;
    }


    /**
     * removes the specified member from the group.
     * @param user The principal to remove from the group.
     * @return true if the principal was removed, flase if
     * the principal was not a member
     */
    public boolean removeMember(Principal user) {
        return groupMembers.removeElement(user);
    }

    /**
     * return the enumeration of the members in the group.
     */
    public Enumeration<? extends Principal> members() {
        return groupMembers.elements();
    }


    /**
     * This function returns true if the gourp passed matched
     * the group represented in this interface.
     * @param another The group to compare this group to.
     */public boolean equals(Object another){
         if (another instanceof Group) {
             Group g = (Group) another;
             return group.equals(g.toString());
         }
         else
             return false;
    }

    /**
     * Prints a stringfield version of the group.
     */
    public String toString() {
        return group;
    }

    /**
     * return a hashcode for the group.
     */
    public int hashCode() {
        return group.hashCode();
    }


    /**
     * return true if the passed pricipal is a member of the group.
     * @param member The Principal whose membership must be checked for.
     * @return true if the principal is a member of this grou, false otherwise
     */
    public boolean isMember(Principal member){
        if (groupMembers.contains(member)) {
            return true;
        } else {
            Vector<Group> alreadySeen = new Vector<>(10);
            return isMemberRecurse(member, alreadySeen);
        }

    }


    /**
     * return the name of the group.
     */
    public String getName() {
        return group;
    }


    /**
     * Recursive search of groups for this implementation of the Gourp.
     * The search proceeds building up a vector of already seen groups.
     * Only new groups are considered, thereby avoiding loops.
     * @param member the Principal
     * @alreadySeen a vector of already seen groups
     */
    boolean isMemberRecurse(Principal member, Vector<Group> alreadySeen){
        Enumeration<? extends Principal> e = members();

        while (e.hasMoreElements()) {
            boolean mem = false;
            Principal p = (Principal) e.nextElement();

            // if the member is in this collection, return true
            if (p.equals(member)) {
                return true;
            } else if (p instanceof GroupCreator) {
                GroupCreator g = (GroupCreator) p;
                alreadySeen.addElement(this);

                if (!alreadySeen.contains(g))
                    mem = g.isMemberRecurse(member, alreadySeen);
            } else if (p instanceof Group){
                Group g = (Group) p;
                if (!alreadySeen.contains(g))
                    mem = g.isMember(member);
            }

            if(mem)
                return mem;
        }
        return false;
    }
}
