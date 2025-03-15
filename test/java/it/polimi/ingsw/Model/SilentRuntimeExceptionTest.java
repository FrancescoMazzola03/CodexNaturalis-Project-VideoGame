package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.NoCauseException;
import it.polimi.ingsw.Model.Exceptions.SilentRuntimeException;
import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class SilentRuntimeExceptionTest extends TestCase {
    public void testToString(){
        SilentRuntimeException exc = new SilentRuntimeException(new Throwable());
        assertEquals(exc.toString(),"");
    }


    public void testFillInStackTrace() {
        SilentRuntimeException exc = new SilentRuntimeException(new Throwable());
        assertEquals(exc.fillInStackTrace(),exc);
    }

    public void testGetMessage() {
        SilentRuntimeException exc = new SilentRuntimeException(new Throwable());
        assertEquals(exc.getMessage(),"");
    }

    public void testPrintStackTrace(){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        SilentRuntimeException exc = new SilentRuntimeException(new Throwable());
        exc.printStackTrace();
        System.setOut(originalOut);
        String output = outputStream.toString().trim();
        assertEquals("", output);
    }
}
