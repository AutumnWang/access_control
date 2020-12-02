package acl;

import java.security.*;

public class PrincipalCreator implements Principal {

    private String user;

    /**
     * Construct a principal from a string user name.
     * @param user The string form of the pricipal name.
     */
    public PrincipalCreator(String user) {
        this.user = user;
    }


    /**
     * This function returns true if the object passed matches
     * the pricipal represented in this implementation
     * @param another the Principal to compare with.
     * @return true if the Principal passed is the same as that
     * encapsulated in this object, false otherwise
     */
    @Override
    public boolean equals(Object another) {
        if (another instanceof PrincipalCreator) {
            PrincipalCreator p = (PrincipalCreator) another;
            return user.equals(p.toString());
        } else
            return false;
    }


    /**
     * Prints a stringfield version of the principal
     */
    public String toString() {

        return user;
    }

    /**
     * return a hashcode for the principal.
     */
    @Override
    public int hashCode() {
        return user.hashCode();
    }

    /**
     * return the name of the principal.
     */
    public String getName() {
        return user;
    }

}
