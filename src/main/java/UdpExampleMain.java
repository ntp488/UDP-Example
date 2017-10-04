import javax.swing.*;
import java.net.DatagramSocket;

public class UdpExampleMain {
    public static MainWindow mainWindow;
    public static DatagramSocket socket;
    public volatile static String outputMessage = "This is the default message!", hostName = "127.0.0.1";
    public volatile static boolean stopThreads = false, sendMessage = false;

    public static void main(String args[]) {
        mainWindow = new MainWindow();
    }

    public static void PostReceivedMessage(String newMessage){
        JLabel temp = new JLabel(newMessage);
        temp.setHorizontalAlignment(JLabel.CENTER);
        mainWindow.AddMessageToList(temp);
        mainWindow.revalidate();
        mainWindow.repaint();
    }
}
