package ch.heig.dai.lab.protocoldesign;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Server {

    final int SERVER_PORT = 1234;

    // list of supported commands
    final String[] COMMANDS = {"ADD", "SUB", "MUL"};

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    void run() {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            while (true) {

                try (Socket socket = serverSocket.accept(); var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8)); var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8))) {

                    // welcome message with the list of supported commands
                    String welcome = "SUPPORTED " + String.join(" ", COMMANDS) + "\n";
                    out.write(welcome);
                    out.flush();

                    String line;
                    while ((line = in.readLine()) != null) {
                        System.err.println("Server: received: " + line);
                        // process the line and send the result back
                        String result = process(line);
                        out.write(result + "\n");
                        out.flush();
                    }

                } catch (IOException e) {
                    System.out.println("Server: socket ex.: " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Server: server socket ex.: " + e);
        }
    }

    String process(String line) {
        // split the line into tokens
        String[] tokens = line.split(" ");
        if (tokens.length < 3) {
            return "ERROR 103 Malformed command";
        }

        // check if the command is supported
        boolean supported = false;
        for (String command : COMMANDS) {
            if (command.equals(tokens[0])) {
                supported = true;
                break;
            }
        }

        if (!supported) {
            return "ERROR 102 Unknown operation";
        }

        // parse the operands, can be int or float (int is a special case of float)
        float a;
        float b;
        try {
            a = Float.parseFloat(tokens[1]);
            b = Float.parseFloat(tokens[2]);
        } catch (NumberFormatException e) {
            return "ERROR 101 Invalid operands";
        }

        // compute the result
        float result = switch (tokens[0]) {
            case "ADD" -> a + b;
            case "SUB" -> a - b;
            case "MUL" -> a * b;
            default -> {
                // should not happen but just in case
                yield Float.NaN;
            }
        };

        if (Float.isNaN(result)) {
            return "ERROR 102 Unknown operation";
        }

        // parse as int if the result is an integer
        if (result == (int) result) {
            return "RESULT " + (int) result;
        }

        return "RESULT " + result;
    }
}
