import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.MulticastSocket;

public class MainWindow extends JFrame {
    JButton startThreadsButton, stopThreadsButton, sendMessageButton;
    JTextArea textArea;
    JScrollPane messageList;
    JPanel messageListPanel;
    JTextField ipInputField;
    JLabel hostnameLabel, messageLabel;
    Thread receiverThread, senderThread;
    BoxLayout layout;

    public MainWindow() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(300, 400));
        this.setName("UDP-Example");
        this.setTitle("UDP-Example");
        layout = new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS);
        CreateComponents();
        this.setLayout(layout);
        this.setVisible(true);
    }

    private void CreateComponents() {
        this.add(Box.createRigidArea(new Dimension(0, 15)));

        hostnameLabel = new JLabel("Host Name:");
        hostnameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(hostnameLabel);

        ipInputField = new JTextField();
        ipInputField.setMaximumSize(new Dimension(200, 25));
        ipInputField.setAlignmentX(Component.CENTER_ALIGNMENT);
        ipInputField.setHorizontalAlignment(JTextField.CENTER);
        ipInputField.setText(UdpExampleMain.hostName);
        this.add(ipInputField);

        this.add(Box.createRigidArea(new Dimension(0, 20)));

        startThreadsButton = new JButton("Start");
        startThreadsButton.setMaximumSize(new Dimension(150, 30));
        startThreadsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startThreadsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (UdpExampleMain.stopThreads != false) {
                    UdpExampleMain.stopThreads = false;
                }
                try {
                    UdpExampleMain.socket = new MulticastSocket(2000);
                    UdpExampleMain.hostName = ipInputField.getText();
                    receiverThread = new Thread(new UDPReceiver());
                    senderThread = new Thread(new UDPSender());
                    receiverThread.start();
                    senderThread.start();
                    sendMessageButton.setEnabled(true);
                    stopThreadsButton.setEnabled(true);
                    startThreadsButton.setEnabled(false);
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        this.add(startThreadsButton);

        this.add(Box.createRigidArea(new Dimension(0, 15)));

        stopThreadsButton = new JButton("Stop");
        stopThreadsButton.setMaximumSize(new Dimension(150, 30));
        stopThreadsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        stopThreadsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UdpExampleMain.stopThreads = true;
                sendMessageButton.setEnabled(false);
                UdpExampleMain.socket.close();
                stopThreadsButton.setEnabled(false);
                startThreadsButton.setEnabled(true);
            }
        });
        stopThreadsButton.setEnabled(false);
        this.add(stopThreadsButton);

        this.add(Box.createRigidArea(new Dimension(0, 20)));

        messageLabel = new JLabel("Message:");
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(messageLabel);

        textArea = new JTextArea();
        textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        textArea.setLineWrap(true);
        JScrollPane textPane = new JScrollPane(textArea);
        textPane.setPreferredSize(new Dimension(250, 75));
        this.add(textPane);

        this.add(Box.createRigidArea(new Dimension(0, 15)));

        sendMessageButton = new JButton("Send Message");
        sendMessageButton.setMaximumSize(new Dimension(150, 30));
        sendMessageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sendMessageButton.setEnabled(false);
        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UdpExampleMain.outputMessage = textArea.getText();
                UdpExampleMain.sendMessage = true;
                textArea.setText("");
            }
        });
        this.add(sendMessageButton);

        this.add(Box.createRigidArea(new Dimension(0, 15)));

        messageList = new JScrollPane();
        messageList.setPreferredSize(new Dimension(250, 75));
        this.add(messageList);

        this.add(Box.createVerticalGlue());

        messageListPanel = new JPanel();
        BoxLayout messageListLayout = new BoxLayout(messageListPanel, BoxLayout.Y_AXIS);
        messageListPanel.setLayout(messageListLayout);

    }

    public void AddMessageToList(JLabel newLabel) {
        messageListPanel.add(newLabel);
        messageList.setViewportView(messageListPanel);
    }
}
