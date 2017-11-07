import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastMainWindow extends JFrame {
    private JButton joinChatButton, sendMessageButton, leaveChatButton, exitButton;
    private JLabel messageLabel, chatGroupIpLabel, portLabel, chatHandleLabel;
    private JScrollPane messageAreaScrollPane;
    private JTextArea messageArea;
    private JTextField messageInputField, ipInputField, portInputField, chatHandleInputField;
    private Thread receiverThread, senderThread;

    public MulticastMainWindow() {
        initComponents();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("UDP-Example");
        this.setVisible(true);
    }

    private void initComponents() {
        messageLabel = new JLabel();
        messageInputField = new JTextField();
        chatGroupIpLabel = new JLabel();
        ipInputField = new JTextField();
        portLabel = new JLabel();
        portInputField = new JTextField();
        joinChatButton = new JButton();
        sendMessageButton = new JButton();
        leaveChatButton = new JButton();
        exitButton = new JButton();
        chatHandleLabel = new JLabel();
        chatHandleInputField = new JTextField();
        messageAreaScrollPane = new JScrollPane();
        messageArea = new JTextArea();

        messageLabel.setText("Message");
        chatGroupIpLabel.setText("Chat Group IP");
        ipInputField.setText(UdpExampleMain.hostName);
        portLabel.setText("Port");
        portInputField.setText(Integer.toString(UdpExampleMain.portNumber));
        joinChatButton.setText("Join Chat");
        sendMessageButton.setText("Send Message");
        leaveChatButton.setText("Leave Chat");
        exitButton.setText("Exit");
        chatHandleLabel.setText("Chat Handle Name");

        leaveChatButton.setEnabled(false);
        sendMessageButton.setEnabled(false);

        messageArea.setColumns(20);
        messageArea.setLineWrap(true);
        messageArea.setRows(5);
        messageArea.setWrapStyleWord(true);
        messageAreaScrollPane.setViewportView(messageArea);

        AddActionListeners();
        CreateLayout();
    }

    private void AddActionListeners() {
        joinChatButton.addActionListener(e -> {
            if (UdpExampleMain.stopThreads) {
                UdpExampleMain.stopThreads = false;
            }
            try {
                UdpExampleMain.socket = new MulticastSocket(Integer.parseInt(portInputField.getText()));
                UdpExampleMain.hostName = ipInputField.getText();
                UdpExampleMain.chatHandle = chatHandleInputField.getText();
                receiverThread = new Thread(new UDPReceiver());
                senderThread = new Thread(new UDPSender());
                receiverThread.start();
                senderThread.start();
                UdpExampleMain.socket.joinGroup(InetAddress.getByName(UdpExampleMain.hostName));
                sendMessageButton.setEnabled(true);
                leaveChatButton.setEnabled(true);
                joinChatButton.setEnabled(false);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        leaveChatButton.addActionListener(e -> {
            UdpExampleMain.stopThreads = true;
            sendMessageButton.setEnabled(false);
            UdpExampleMain.socket.close();
            leaveChatButton.setEnabled(false);
            joinChatButton.setEnabled(true);
        });

        sendMessageButton.addActionListener(e -> {
            UdpExampleMain.outputMessage = UdpExampleMain.chatHandle + ":" + messageInputField.getText();
            UdpExampleMain.sendMessage = true;
            messageInputField.setText("");
        });

        exitButton.addActionListener(e -> System.exit(0));
    }

    private void CreateLayout() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = .3; gbc.weighty = .2;
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        this.add(chatHandleLabel, gbc);



        gbc.gridx = 3; gbc.gridy = 0; gbc.weightx = .3; gbc.weighty = .2;
        gbc.insets = new Insets(10, 0, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        this.add(chatHandleInputField, gbc);




        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = .3; gbc.weighty = .2;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 4;
        this.add(messageAreaScrollPane, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = .3; gbc.weighty = .2;
        gbc.insets = new Insets(0, 10, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        this.add(messageLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = .3; gbc.weighty = .2;
        gbc.insets = new Insets(0, 0, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        this.add(messageInputField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = .3; gbc.weighty = .2;
        gbc.insets = new Insets(0, 10, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        this.add(chatGroupIpLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = .3; gbc.weighty = .2;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        this.add(portLabel, gbc);

        gbc.gridx = 2; gbc.gridy = 3; gbc.weightx = .3; gbc.weighty = .2;
        gbc.insets = new Insets(5, 10, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        this.add(joinChatButton, gbc);

        gbc.gridx = 3;gbc.gridy = 3;gbc.weightx = .3;gbc.weighty = .2;
        gbc.insets = new Insets(5, 5, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        this.add(sendMessageButton, gbc);

        gbc.gridx = 0;gbc.gridy = 4;gbc.weightx = .3;gbc.weighty = .2;
        gbc.insets = new Insets(-25, 10, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        this.add(ipInputField, gbc);

        gbc.gridx = 1;gbc.gridy = 4;gbc.weightx = .3;gbc.weighty = .2;
        gbc.insets = new Insets(-25, 10, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        this.add(portInputField, gbc);

        gbc.gridx = 2;gbc.gridy = 4;gbc.weightx = .3;gbc.weighty = .2;
        gbc.insets = new Insets(5, 10, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        this.add(leaveChatButton, gbc);

        gbc.gridx = 3;gbc.gridy = 4;gbc.weightx = .3;gbc.weighty = .2;
        gbc.insets = new Insets(5, 5, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        this.add(exitButton, gbc);

        this.setMinimumSize(new Dimension(500, 0));
        pack();
    }

    public void AddMessageToList(String newMessage) {
        messageArea.append(newMessage);
        messageAreaScrollPane.setViewportView(messageArea);
    }

}