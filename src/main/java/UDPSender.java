import java.net.*;
import java.io.*;
import java.util.*;

public class UDPSender implements Runnable {
    @Override
    public void run() {
        //use localhost to experiment on a standalone computer, change to correct ip address in order to communicate
        String hostname="localhost", message = "HELLO USING UDP!";
        try {
            // Create a datagram socket, look for the first available port
            System.out.println ("Using local port: " + UdpExampleMain.socket.getLocalPort());
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            PrintStream pOut = new PrintStream(bOut, true);
            DatagramPacket packet;
            byte [] bArray;
            System.out.println("Looking for hostname " + hostname);
            InetAddress remoteAddress = InetAddress.getByName(hostname);
            //check its IP number
            System.out.println("Hostname has IP address = " +
                    remoteAddress.getHostAddress());

            while (UdpExampleMain.stopThreads == false) {
                //TODO: change so that message can be changed and update sending packet
                if (UdpExampleMain.sendMessage == true) {
                    pOut.println(message);
                    bArray = bOut.toByteArray();
                    packet = new DatagramPacket( bArray, bArray.length );
                    packet.setAddress(remoteAddress);
                    packet.setPort(2000);
                    UdpExampleMain.socket.send(packet);
                    System.out.println ("Packet sent at!" + new Date());
                    UdpExampleMain.sendMessage = false;
                }
            }
        }
        catch (UnknownHostException ue){
            System.out.println("Unknown host "+hostname);
        }
        catch (IOException e)	{
            System.out.println ("Error - " + e);
        }
    }
}
