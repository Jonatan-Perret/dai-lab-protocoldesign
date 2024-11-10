package ch.heig.dai.lab.protocoldesign;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ServerTest {

    @Test
    public void testAddIntOperation() {
        Server server = new Server();
        String result = server.process("ADD 2 3");
        assertEquals("RESULT 5", result);
    }

    @Test
    public void testSubIntOperation() {
        Server server = new Server();
        String result = server.process("SUB 5 3");
        assertEquals("RESULT 2", result);
    }

    @Test
    public void testMulIntOperation() {
        Server server = new Server();
        String result = server.process("MUL 2 3");
        assertEquals("RESULT 6", result);
    }

    @Test
    public void testAddFloatOperation() {
        Server server = new Server();
        String result = server.process("ADD 2.3 6.4");
        assertEquals("RESULT 8.7", result);
    }

    @Test
    public void testSubFloatOperation() {
        Server server = new Server();
        String result = server.process("SUB 10.0 3.3");
        assertEquals("RESULT 6.7", result);
    }

    @Test
    public void testMulFloatOperation() {
        Server server = new Server();
        String result = server.process("MUL 2 3.4");
        assertEquals("RESULT 6.8", result);
    }

    @Test
    public void testMalformedOperand() {
        Server server = new Server();
        String result = server.process("ADD 2 a");
        assertEquals("ERROR 101 Invalid operands", result);
    }

    @Test
    public void testUnknownOperation() {
        Server server = new Server();
        String result = server.process("DIV 2 3");
        assertEquals("ERROR 102 Unknown operation", result);
    }

    @Test
    public void testMalformedCommand() {
        Server server = new Server();
        String result = server.process("ADD 2");
        assertEquals("ERROR 103 Malformed command", result);
    }
}
