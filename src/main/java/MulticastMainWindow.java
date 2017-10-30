import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        messageInputField.setText("messageInputField");
        chatGroupIpLabel.setText("Chat Group IP");
        ipInputField.setText(UdpExampleMain.hostName);;
        portLabel.setText("Port");
        portInputField.setText("portInputField");
        joinChatButton.setText("Join Chat");
        sendMessageButton.setText("Send Message");
        leaveChatButton.setText("Leave Chat");
        exitButton.setText("Exit");
        chatHandleLabel.setText("Chat Handle Name");
        chatHandleInputField.setText("chatHandleInputField");

        leaveChatButton.setEnabled(false);
        sendMessageButton.setEnabled(false);

        messageArea.setColumns(20);
        messageArea.setLineWrap(true);
        messageArea.setRows(5);
        messageArea.setWrapStyleWord(true);
        messageAreaScrollPane.setViewportView(messageArea);
        messageArea.setText("messageArea");

        AddActionListeners();
        CreateLayout();
    }

    private void AddActionListeners() {
        joinChatButton.addActionListener(new ActionListener() {
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
                    leaveChatButton.setEnabled(true);
                    leaveChatButton.setEnabled(false);
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        leaveChatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UdpExampleMain.stopThreads = true;
                sendMessageButton.setEnabled(false);
                UdpExampleMain.socket.close();
                leaveChatButton.setEnabled(false);
                leaveChatButton.setEnabled(true);
            }
        });

        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UdpExampleMain.outputMessage = messageInputField.getText();
                UdpExampleMain.sendMessage = true;
                messageInputField.setText("");
            }
        });
    }

    private void CreateLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(ipInputField, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                                .addGap(113, 113, 113)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(joinChatButton)
                                                .addGap(29, 29, 29)
                                                .addComponent(sendMessageButton))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(leaveChatButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(exitButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(chatHandleLabel)
                                .addGap(26, 26, 26)
                                .addComponent(chatHandleInputField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(chatGroupIpLabel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(messageLabel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(messageInputField, GroupLayout.PREFERRED_SIZE, 316, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(45, 45, 45)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(portInputField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(portLabel))))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(messageAreaScrollPane)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(chatHandleLabel)
                                        .addComponent(chatHandleInputField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(11, 11, 11)
                                .addComponent(messageAreaScrollPane, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(messageLabel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(messageInputField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(chatGroupIpLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(portLabel)
                                        .addComponent(joinChatButton)
                                        .addComponent(sendMessageButton))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(leaveChatButton)
                                                .addComponent(exitButton))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(portInputField)
                                                        .addComponent(ipInputField))
                                                .addContainerGap())))
        );

        pack();
    }

    public void AddMessageToList(JLabel newLabel) {
        messageArea.append(newLabel.getText());
        messageAreaScrollPane.setViewportView(messageArea);
    }

}