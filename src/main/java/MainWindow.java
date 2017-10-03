import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.DatagramSocket;

/**
 * Created by Nathan on 10/3/2017.
 */
public class MainWindow extends JFrame {
    JButton startThreadsButton, stopThreadsButton, sendMessageButton;
    Thread receiverThread, senderThread;
    public MainWindow() {
        this.setMinimumSize(new Dimension(300, 500));
        this.setName("UDP-Example");
        this.setTitle("UDP-Example");
        this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
        CreateComponents();
        this.setVisible(true);
    }

    private void CreateComponents() {
        startThreadsButton = new JButton("Start");
        startThreadsButton.setMaximumSize(new Dimension(100, 30));
        startThreadsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startThreadsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (UdpExampleMain.stopThreads != false) {
                    UdpExampleMain.stopThreads = false;
                }
                try {
                    UdpExampleMain.socket = new DatagramSocket(2000);
                    receiverThread = new Thread(new UDPReceiver());
                    senderThread = new Thread(new UDPSender());
                    receiverThread.start();
                    senderThread.start();
                    sendMessageButton.setEnabled(true);
                }
                catch (java.net.SocketException e1) {
                    e1.printStackTrace();
                }
            }
        });
        this.add(startThreadsButton);

        stopThreadsButton = new JButton("Stop");
        stopThreadsButton.setMaximumSize(new Dimension(100, 30));
        stopThreadsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        stopThreadsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UdpExampleMain.stopThreads = true;
                sendMessageButton.setEnabled(false);
                UdpExampleMain.socket.close();
            }
        });
        this.add(stopThreadsButton);

        sendMessageButton = new JButton("Send Message");
        sendMessageButton.setMaximumSize(new Dimension(100, 30));
        sendMessageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sendMessageButton.setEnabled(false);
        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UdpExampleMain.sendMessage = true;
            }
        });
        this.add(sendMessageButton);
    }
}
