import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public static void main(String[] args) throws Exception{
        System.out.println("Waiting for clients...");

        ServerSocket ss = new ServerSocket(8888);
        // Rx incoming conn
        while (true) {
            Socket soc = ss.accept();
            System.out.println("Connection Established");
            ConversationHandler handler = new ConversationHandler(soc);
            handler.start();
        }
    }
}

class ConversationHandler extends Thread {
    Socket socket;
    BufferedReader in;
    PrintWriter out;

    public ConversationHandler(Socket socket) throws IOException {
        this.socket = socket;
    }
    public void run() {

        try {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
