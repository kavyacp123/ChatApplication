
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static Set<PrintWriter> clientWriters = new HashSet<>();

    public static void main(String[] args) throws IOException {
        System.out.println("Chat server started...");
        ServerSocket serverSocket = new ServerSocket(12345);  // Port 12345

        try {
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } finally {
            serverSocket.close();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                // Create I/O streams for this client
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Add the client output stream to the set of all client output streams
                synchronized (clientWriters) {
                    clientWriters.add(out);
                }

                // Read messages from the client and broadcast to all
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received: " + message);
                    synchronized (clientWriters) {
                        for (PrintWriter writer : clientWriters) {
                            writer.println(message);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error closing socket: " + e.getMessage());
                }

                // Remove the client writer from the set when the client disconnects
                synchronized (clientWriters) {
                    clientWriters.remove(out);
                }
            }
        }
    }
}
