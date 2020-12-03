package utils.acl;

/* Note: This sample program is meant just as an example
 * of the types of things that can be done with an
 * implementation of the java.security.acl interfaces.
 * This example uses the implementation supplied by the
 * sun.security.acl package. Please note that sun.* classes
 * are unsupported and subject to change.
 */

import java.security.Principal;
import java.security.acl.*;;
import java.util.Enumeration;

public class Demo {
    public static void main(String args[])
            throws Exception
    {

        Principal p1 = new PrincipalCreator("user1");
        Principal p2 = new PrincipalCreator("user2");
        Principal owner = new PrincipalCreator("owner");

        Permission read = new PermissionCreator("READ");
        Permission write = new PermissionCreator("WRITE");

        System.out.println("Creating a new group with two members: user1 and user2");
        Group g = new GroupCreator("group1");
        g.addMember(p1);
        g.addMember(p2);

        //
        // create a new acl with the name "exampleAcl"
        //
        System.out.println("Creating a new Acl named 'exampleAcl'");
        Acl acl = new AclCreator(owner, "exampleAcl");


        //
        // Allow group all permissions
        //
        System.out.println("Creating a new Acl Entry in exampleAcl for the group, ");
        System.out.println("  with read & write permissions");
        AclEntry entry1 = new AclEntryCreator(g);
        entry1.addPermission(read);
        entry1.addPermission(write);
        acl.addEntry(owner, entry1);

        //
        // Take away WRITE permissions for
        // user1. All others in groups still have
        // WRITE privileges.
        //
        System.out.println("Creating a new Acl Entry in exampleAcl for user1");
        System.out.println("  without write permission");
        AclEntry entry2 = new AclEntryCreator(p1);
        entry2.addPermission(write);
        entry2.setNegativePermissions();
        acl.addEntry(owner, entry2);

        //
        // This enumeration is an enumeration of
        // Permission interfaces. It should return
        // only "READ" permission.
        Enumeration e1 = acl.getPermissions(p1);
        System.out.println("Permissions for user1 are:");
        while (e1.hasMoreElements()) {
            System.out.println("  " + e1.nextElement());
        }

        //
        // This enumeration should have "READ" and"WRITE"
        // permissions.
        Enumeration e2 = acl.getPermissions(p2);
        System.out.println("Permissions for user2 are:");
        while (e2.hasMoreElements()) {
            System.out.println("  " + e2.nextElement());
        }

        // This should return false.
        boolean b1 = acl.checkPermission(p1, write);
        System.out.println("user1 has write permission: " + b1);

        // This should all return true;
        boolean b2 = acl.checkPermission(p1, read);
        boolean b3 = acl.checkPermission(p2, read);
        boolean b4 = acl.checkPermission(p2, write);
        System.out.println("user1 has read permission: " + b2);
        System.out.println("user2 has read permission: " + b3);
        System.out.println("user2 has write permission: " + b4);
    }
}
