import java.net.*;
import java.io.*;
import java.util.*;

public class UDPSender implements Runnable {
    @Override
    public void run() {
        //use localhost to experiment on a standalone computer, change to correct ip address in order to communicate
        try {
            // Create a datagram socket, look for the first available port
            //System.out.println ("Using local port: " + UdpExampleMain.socket.getLocalPort());
            ByteArrayOutputStream bOut;
            PrintStream pOut;
            DatagramPacket packet;
            byte [] bArray;
            System.out.println("Looking for hostname " + UdpExampleMain.hostName);
            InetAddress remoteAddress = InetAddress.getByName(UdpExampleMain.hostName);
            //check its IP number
            //System.out.println("Hostname has IP address = " +
            //        remoteAddress.getHostAddress());

            while (UdpExampleMain.stopThreads == false) {
                if (UdpExampleMain.sendMessage == true) {
                    bArray = UdpExampleMain.outputMessage.getBytes();
                    packet = new DatagramPacket(
                        bArray,
                        bArray.length,
                        InetAddress.getByName(UdpExampleMain.hostName),
                        UdpExampleMain.portNumber
                    );
                    UdpExampleMain.socket.send(packet);
                    UdpExampleMain.socket.close();
                    //System.out.println ("Packet sent at!" + new Date());
                    UdpExampleMain.sendMessage = false;
                    //UdpExampleMain.PostMessage(UdpExampleMain.outputMessage);
                }
            }
            System.out.println("Stopping sender.");
        }
        catch (UnknownHostException ue){
            System.out.println("Unknown host " + UdpExampleMain.hostName);
        }
        catch (IOException e)	{
            System.out.println ("Error - " + e);
        }
    }
}
