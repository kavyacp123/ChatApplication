
import java.awt.*;
import java.awt.event.*;

class ChatClientGUI extends Frame implements ActionListener {
    // GUI Components
    private TextArea chatArea;
    private TextField inputField;
    private Button sendButton;

    // Constructor
    ChatClientGUI(String title) {
        super(title); // Set the title of the Frame

        // Initialize GUI Components
        chatArea = new TextArea();
        chatArea.setEditable(false); // Users should not edit the chat history directly

        inputField = new TextField();
        sendButton = new Button("Send");

        // Set Layout Manager
        setLayout(new BorderLayout());

        // Add Components to Frame
        add(chatArea, BorderLayout.CENTER); // Chat display area in the center

        // Panel to hold input field and send button
        Panel inputPanel = new Panel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH); // Input area at the bottom

        // Add Action Listeners
        sendButton.addActionListener(this);
        inputField.addActionListener(this); // Allows pressing Enter to send message

        // Window Closing Event
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose(); // Close the window
                System.exit(0); // Terminate the program
            }
        });

        // Set Frame Size and Make It Visible
        setSize(500, 400);
        setVisible(true);
    }

    // Handle Actions (Button Click or Enter Key Press)
    @Override
    public void actionPerformed(ActionEvent ae) {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            appendMessage("You: " + message); // Display the sent message
            inputField.setText(""); // Clear the input field

            // TODO: Implement message sending to the server here
        }
    }

    // Method to Append Messages to the Chat Area
    public void appendMessage(String message) {
        chatArea.append(message + "\n");
    }

    // Main Method to Launch the GUI
    public static void main(String[] args) {
        new ChatClientGUI("AWT Chat Client");
    }
}
