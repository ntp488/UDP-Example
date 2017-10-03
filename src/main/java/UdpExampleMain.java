import java.net.DatagramSocket;

/**
 * Created by Nathan on 10/3/2017.
 */
public class UdpExampleMain {
    public static MainWindow mainWindow;
    public static DatagramSocket socket;
    public volatile static String message;
    public volatile static boolean stopThreads = false, sendMessage = false;

    public static void main(String args[]) {
        mainWindow = new MainWindow();
    }
}
