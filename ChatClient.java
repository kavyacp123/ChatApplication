
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) throws IOException {
        String serverAddress = "localhost";  // or server IP
        int port = 12345;

        Socket socket = new Socket(serverAddress, port);
        new Thread(new IncomingMessagesHandler(socket)).start(); // Handle incoming messages

        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            // Keep sending messages to the server
            while (true) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine();
                out.println(message);  // Send message to server
            }
        }
    }

    // Thread to handle incoming messages from the server
    private static class IncomingMessagesHandler implements Runnable {
        private Socket socket;

        public IncomingMessagesHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String incomingMessage;
                while ((incomingMessage = in.readLine()) != null) {
                    System.out.println("Server: " + incomingMessage);  // Print messages from server
                }
            } catch (IOException e) {
                System.out.println("Connection closed.");
            }
        }
    }
}
