package utils.acl;

import org.junit.jupiter.api.Test;

import java.security.Principal;
import java.security.acl.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AclCreatorTest {


    @Test
    void getName() {
        Principal owner = new PrincipalCreator("owner");
        Acl acl = new AclCreator(owner, "exampleAcl");
        assertEquals("exampleAcl", acl.getName());
    }

    @Test
    void addEntry() throws NotOwnerException {
        Principal p1 = new PrincipalCreator("user1");
        Principal owner = new PrincipalCreator("owner");
        Group g = new GroupCreator("group1");
        AclEntry entry1 = new AclEntryCreator(g);
        AclEntry entry2 = new AclEntryCreator(p1);
        Acl acl = new AclCreator(owner, "exampleAcl");
        assertEquals(true, acl.addEntry(owner, entry1));
        assertEquals(true, acl.addEntry(owner, entry2));
    }


    @Test
    void checkPermission() throws NotOwnerException {
        Principal p1 = new PrincipalCreator("user1");
        Principal p2 = new PrincipalCreator("user2");
        Principal owner = new PrincipalCreator("owner");

        Permission read = new PermissionCreator("READ");
        Permission write = new PermissionCreator("WRITE");
        Group g = new GroupCreator("group1");
        g.addMember(p1);
        g.addMember(p2);
        Acl acl = new AclCreator(owner, "exampleAcl");
        AclEntry entry1 = new AclEntryCreator(g);
        AclEntry entry2 = new AclEntryCreator(p2);
        entry1.addPermission(read);
        entry1.addPermission(write);
        entry2.addPermission(write);
        entry2.setNegativePermissions();
        acl.addEntry(owner, entry1);
        acl.addEntry(owner, entry2);

        assertEquals(true, acl.checkPermission(p1, write));
        assertEquals(false, acl.checkPermission(p2, write));
    }
}