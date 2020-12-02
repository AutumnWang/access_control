package utils.acl;

import org.junit.jupiter.api.Test;

import java.security.Principal;
import java.security.acl.Group;

import static org.junit.jupiter.api.Assertions.*;

class GroupCreatorTest {

    Principal p1 =  new PrincipalCreator("user1");
    Principal p2 = new PrincipalCreator("user2");
    Principal p3 = new PrincipalCreator("user3");
    Principal p4 = new PrincipalCreator("user4");
    Group g = new GroupCreator("group1");

    @Test
    public void addMember() {
        assertEquals(true, g.addMember(p1));
        assertEquals(false, g.addMember(p1));
        assertEquals(true, g.addMember(p2));
    }


    @Test
    public void removeMember() {
        g.addMember(p1);
        assertEquals(true, g.removeMember(p1));
    }



    @Test
    public void testEquals() {
        assertEquals(true, g.equals(g));
    }

    @Test
    public void isMember() {
        g.addMember(p2);
        g.addMember(p3);
        g.addMember(p4);
        g.addMember(p3);
        assertEquals(false, g.isMember(p1));
        assertEquals(true, g.isMember(p2));
        assertEquals(true, g.isMember(p3));
    }

    @Test
    public void getName() {
        assertEquals("group1", g.getName());
    }


}