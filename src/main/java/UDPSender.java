import java.net.*;
import java.io.*;

public class UDPSender {
    public static void SendMessage() {
        try {
            DatagramSocket dSocket;
            DatagramPacket packet;
            byte [] bArray;
            System.out.println("Looking for hostname " + UdpExampleMain.hostName);
            InetAddress remoteAddress = InetAddress.getByName(UdpExampleMain.hostName);

            dSocket = new DatagramSocket();

            bArray = UdpExampleMain.outputMessage.getBytes();
            packet = new DatagramPacket(
                bArray,
                bArray.length,
                remoteAddress,
                UdpExampleMain.portNumber
            );
            dSocket.send(packet);
            dSocket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
