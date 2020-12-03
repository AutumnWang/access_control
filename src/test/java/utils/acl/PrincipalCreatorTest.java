package utils.acl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;

class PrincipalCreatorTest {

    Principal p1 = new PrincipalCreator("user1");
    Principal p2 = new PrincipalCreator("user2");


    @Test
    public void testEquals() {
        assertEquals(false, p1.equals(p2));
        assertEquals(true, p1.equals(p1));
    }

    @Test
    public void getName() {
        assertEquals("user1", p1.getName());
    }
}