package utils.acl;

import org.junit.jupiter.api.Test;

import java.security.acl.Permission;

import static org.junit.jupiter.api.Assertions.*;

class PermissionCreatorTest {

    Permission per1 = new PermissionCreator("read");
    Permission per2 = new PermissionCreator("write");

    @Test
    public void testEquals() {
        assertEquals(true, per1.equals(per1));
        assertEquals(false, per1.equals(per2));
    }

    @Test
    public void testToString() {
        assertEquals("read", per1.toString());
        assertEquals("write", per2.toString());
    }
}