import com.sun.xml.internal.ws.api.pipe.ClientPipeAssemblerContext;

import javax.swing.*;

import java.awt.FlowLayout;
import java.io.*;
import java.net.Socket;

public class ChatClient {

    // Outer Container
    // Keep swing obj static
    static JFrame chatWindow = new JFrame("Chat Application");
    static JTextArea chatArea = new JTextArea(22, 40);
    static JTextField textField = new JTextField(40);            // para means columns
    static JLabel blankLabel = new JLabel("         ");             // between chat area and textfield
    static JButton sendButton = new JButton("Send");                // Send Btn
    private static Object ChatClient;

    static BufferedReader in;
    static PrintWriter out;


    ChatClient() {
        chatWindow.setLayout(new FlowLayout());                          // arrange component

        chatWindow.add(new JScrollPane(chatArea));                       // scroller
        chatWindow.add(blankLabel);
        chatWindow.add(textField);
        chatWindow.add(sendButton);

        chatWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       // enable close action to close the application
        chatWindow.setSize(475, 500);
        chatWindow.setVisible(true);                                     // Display these things on screen


        textField.setEditable(false);                                    // set true once server connection established
        chatArea.setEditable(false);                                     // chatArea is only for displaying


    }

    // Main logic for chatClient
    void startChat() throws Exception {

        String ipAddress = JOptionPane.showInputDialog(chatWindow,
                                                "Enter IP Address:",
                                                    "IP Address Required!!",
                                                        JOptionPane.PLAIN_MESSAGE
                                                        );

        Socket soc = new Socket(ipAddress, 8888);
        in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        out = new PrintWriter(new PrintWriter(soc.getOutputStream(), true));

        while (true) {

        }

    }

    public static void main(String[] args) throws Exception {
        ChatClient client = new ChatClient();
        client.startChat();
    }
}
