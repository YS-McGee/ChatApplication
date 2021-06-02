import com.sun.xml.internal.ws.api.pipe.ClientPipeAssemblerContext;

import javax.swing.*;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class ChatClient {

    // Outer Container
    // Keep swing obj static
    static JFrame chatWindow = new JFrame("Chat Application");
    static JTextArea chatArea = new JTextArea(22, 40);
    static JTextField textField = new JTextField(40);           // para means columns
    static JLabel blankLabel = new JLabel("         ");            // between chat area and textfield
    static JLabel nameLabel = new JLabel("        ");
    static JButton sendButton = new JButton("Send");               // Send Btn

    static BufferedReader in;
    static PrintWriter out;

    ChatClient() {
        chatWindow.setLayout(new FlowLayout());                         // arrange component

        chatWindow.add(nameLabel);
        chatWindow.add(new JScrollPane(chatArea));                      // scroller
        chatWindow.add(blankLabel);
        chatWindow.add(textField);
        chatWindow.add(sendButton);

        chatWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      // enable close action to close the application
        chatWindow.setSize(475, 500);
        chatWindow.setVisible(true);                                    // Display these things on screen

        textField.setEditable(false);                                   // set true once server connection established
        chatArea.setEditable(false);                                    // chatArea is only for displaying

        sendButton.addActionListener(new Listener());                   // Bind sendBtn with the Listener
        textField.addActionListener(new Listener());                    // Bind ENTER to Listener
    }

    // Main logic for chatClient
    void startChat() throws Exception {

        String ipAddress = (String) JOptionPane.showInputDialog(chatWindow,
                                                "Enter IP Address:",
                                                    "IP Address Required!!",
                                                        JOptionPane.PLAIN_MESSAGE, null, null,
                                        "localhost"
                                                        );

        Socket soc = new Socket(ipAddress, 8888);
        in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        out = new PrintWriter(soc.getOutputStream(), true);

        while (true) {
            String str = in.readLine();
            if (str.equals("NAME_REQUIRED")) {
                String name = JOptionPane.showInputDialog(chatWindow,
                                                "Enter a unique name:",
                                                    "Name Required!",
                                                        JOptionPane.PLAIN_MESSAGE);
                out.println(name);
            } else if (str.equals("NAME_ALREADY_EXIST")) {
                String name = JOptionPane.showInputDialog(chatWindow,
                                                    "Try another name:",
                                                    "Name Already Exists!!",
                                                        JOptionPane.WARNING_MESSAGE);
                out.println(name);
            } else if (str.startsWith("NAME_ACCEPTED")) {
                textField.setEditable(true);
                textField.requestFocusInWindow();
                nameLabel.setText("You are logged in as: " + str.substring(13));
            } else
                chatArea.append(str + "\n");                                            // msg from other clients
        }
    }

    public static void main(String[] args) throws Exception {
        ChatClient client = new ChatClient();
        client.startChat();
    }
}

class Listener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        ChatClient.out.println(ChatClient.textField.getText());                 // send whatever inside textField
        ChatClient.textField.setText("");                                       // Clear textField
    }
}