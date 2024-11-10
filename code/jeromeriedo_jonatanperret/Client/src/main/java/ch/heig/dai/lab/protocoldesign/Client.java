package ch.heig.dai.lab.protocoldesign;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    private final static String SERVER_DEFAULT_ADDRESS = "127.0.0.1";
    private final static int SERVER_DEFAULT_PORT = 1234;
    private final String serverAddress;
    private final int serverPort;

    public Client(String aServerAddress, int aServerPort){
        serverAddress = aServerAddress;
        serverPort = aServerPort;
    }

    public static void main(String[] args) {
        String serverAddress = SERVER_DEFAULT_ADDRESS;
        int serverPort = SERVER_DEFAULT_PORT;

        System.out.println("Program started" + (args.length!=2 ? " without args <server_ip> <port>. Default values are used..." : "..."));
            
        if (args.length == 2){
            serverAddress = args[0];
            try {
                serverPort = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port number. Use default port " + SERVER_DEFAULT_PORT);
                serverPort = SERVER_DEFAULT_PORT;
            }
        }

        // Create a new client and run it
        System.out.println("Trying to reach server " + serverAddress + " on port " + serverPort);
        Client client = new Client(serverAddress, serverPort);
        client.run();


        System.out.println("Program finished...");
    }

    private void run() {
         try (Socket socket = new Socket(serverAddress, serverPort);
             var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
             var scanner = new Scanner(System.in)) {

            String inputFromServer;
            String userChoice;

            while(true){

                // wait the welcome message or the answere from the server
                inputFromServer = in.readLine();
                System.out.println(inputFromServer);
                

                // ask the user choice
                System.out.println("Your choice (q to quit): ");
                while ((userChoice = scanner.nextLine()).equals("")){} //empty string not allowed

                // check if the user wants to quit
                if ("q".equalsIgnoreCase(userChoice)){
                    break;
                }

                // send the message
                out.write(userChoice + "\n");
                out.flush();

            }

        } catch (IOException e) {
            System.out.println("Client: exception while using client socket: " + e);
        }
    }
}