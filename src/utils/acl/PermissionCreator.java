package acl;

import java.security.Principal;
import java.security.acl.*;

public class PermissionCreator implements Permission {

    private String permission;

    /**
     * Construct a permission object using a string.
     * @param permission the stringfied version of the permission.
     */
    public PermissionCreator(String permission){
        this.permission = permission;
    }

    /**
     * This function returns true if the object passed matches the permission
     * represented in this interface.
     * @param another The Permission object to compare with.
     * @return true if the Permission objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object another) {
        if(another instanceof Permission) {
            Permission p = (Permission) another;
            return permission.equals(p.toString());
        } else {
            return false;
        }
    }

    /**
     * Prints a stringfield version of the permission.
     * @return the string representation of the Permission.
     */
    public String toString() {
        return permission;
    }


    /**
     * Returns a hashcode for this PermissionCreator
     * @return a hashcode for this PermissionCreator
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
