public class UdpExampleMain {
    public static MulticastMainWindow mainWindow;
    public static int portNumber = 4001;
    public volatile static String outputMessage = "This is the default message!", hostName = "224.0.0.1",
        chatHandle = "";
    public volatile static boolean stopThreads = false, sendMessage = false;

    public static void main(String args[]) {
        mainWindow = new MulticastMainWindow();
    }

    public static void PostMessage(String newMessage){
        mainWindow.AddMessageToList(newMessage);
        mainWindow.revalidate();
        mainWindow.repaint();
    }
}
