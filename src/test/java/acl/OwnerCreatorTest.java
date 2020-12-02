package acl;

import org.junit.jupiter.api.Test;

import java.security.Principal;
import java.security.acl.LastOwnerException;
import java.security.acl.NotOwnerException;
import java.security.acl.Owner;

import static org.junit.jupiter.api.Assertions.*;

class OwnerCreatorTest {

    Principal p1 =  new PrincipalCreator("user1");
    Principal p2 = new PrincipalCreator("user2");
    Principal p3 = new PrincipalCreator("user3");
    Principal p4 = new PrincipalCreator("user4");
    Principal owner = new PrincipalCreator("owner");
    Owner o = new OwnerCreator(owner);

    @Test
    public void addOwner() throws NotOwnerException {
        assertEquals(false, o.addOwner(owner, owner));
        //assertEquals(true, o.addOwner(owner, p1));
    }

    @Test
    public void exceptionTesting() throws LastOwnerException, NotOwnerException {
        Exception exception1 = assertThrows(NotOwnerException.class, () -> o.addOwner(p1, p1));
        Exception exception2 = assertThrows(LastOwnerException.class, () -> o.deleteOwner(owner, owner));
    }

    @Test
    public void deleteOwner() throws NotOwnerException, LastOwnerException {
        o.addOwner(owner, p1);
        o.addOwner(owner, p2);
        assertEquals(true, o.deleteOwner(owner, p2));
    }

    @Test
    public void isOwner() throws NotOwnerException {
        o.addOwner(owner, p1);
        assertEquals(true, o.isOwner(p1));
        assertEquals(false, o.isOwner(p2));
    }
}