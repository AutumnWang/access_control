package acl;

import org.junit.jupiter.api.Test;

import java.security.Principal;
import java.security.acl.AclEntry;
import java.security.acl.Permission;

import static org.junit.jupiter.api.Assertions.*;

class AclEntryCreatorTest {

    Principal p1 = new PrincipalCreator("user1");
    Permission per1 = new PermissionCreator("read");
    Permission per2 = new PermissionCreator("write");
    AclEntry ae = new AclEntryCreator(p1);


    @Test
    void getPrincipal() {
        assertEquals(p1, ae.getPrincipal());
    }

    @Test
    void isNegative() {
        ae.setNegativePermissions();
        assertEquals(true, ae.isNegative());
    }

    @Test
    void checkPermission() {
        ae.addPermission(per1);
        ae.addPermission(per2);
        assertEquals(true, ae.checkPermission(per1));
        ae.removePermission(per2);
        assertEquals(false, ae.checkPermission(per2));
    }

    @Test
    void testClone() {
        ae.addPermission(per1);
        Object cloned = ae.clone();
        AclEntry ae2 = (AclEntry) cloned;
        assertEquals(p1, ae2.getPrincipal());
        assertEquals(true, ae2.checkPermission(per1));
        assertEquals(false, ae2.checkPermission(per2));
    }
}