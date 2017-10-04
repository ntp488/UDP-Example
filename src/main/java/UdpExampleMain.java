import java.net.DatagramSocket;

public class UdpExampleMain {
    public static MainWindow mainWindow;
    public static DatagramSocket socket;
    public volatile static String message = "This is the default message!", hostName = "127.0.0.1";
    public volatile static boolean stopThreads = false, sendMessage = false;

    public static void main(String args[]) {
        mainWindow = new MainWindow();
    }
}
