import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {

    static ArrayList<String> userName = new ArrayList<String>();
    static ArrayList<PrintWriter> printWriters = new ArrayList<PrintWriter>();      // Send msg to all client

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
    String name;

    public ConversationHandler(Socket socket) throws IOException {
        this.socket = socket;
    }
    public void run() {

        try {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            int count = 0;
            while (true) {

                if(count > 0)
                    out.println("NAME_ALREADY_EXIST");
                else
                    out.println("NAME_REQUIRED");

                name = in.readLine();
                if (name == null)
                    return;
                if (!ChatServer.userName.contains(name)) {
                    ChatServer.userName.add(name);
                    break;
                }
                ++count;
            }
            out.println("NAME_ACCEPTED"+name);
            ChatServer.printWriters.add(out);

            while (true) {
                String message = in.readLine();

                if (message == null)
                    return;
                for (PrintWriter writer : ChatServer.printWriters)              // For all writer in printWriter
                    writer.println(name + ": " + message);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
