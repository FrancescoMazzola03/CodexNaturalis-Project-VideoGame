package it.polimi.ingsw.Model;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class ResourceTest extends TestCase {
    Resource res;

    @Before
    public void setUp(){
        res = Resource.ANIMAL;
    }

    @Test
    public void testGetResName() {
        assertEquals(res.getResName(),"Animal");
    }
}