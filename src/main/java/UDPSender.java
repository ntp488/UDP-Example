import java.net.*;
import java.io.*;
import java.util.*;

public class UDPSender extends Thread {
    public static void main (String args[]) {
        //use localhost to experiment on a standalone computer, change to correct ip address in order to communicate
        String hostname="localhost", message = "HELLO USING UDP!";
        try {
            // Create a datagram socket, look for the first available port
            DatagramSocket socket = new DatagramSocket();
            System.out.println ("Using local port: " + socket.getLocalPort());
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            PrintStream pOut = new PrintStream(bOut);
            pOut.print(message);

            byte [ ] bArray = bOut.toByteArray();
            DatagramPacket packet=new DatagramPacket( bArray, bArray.length );

            System.out.println("Looking for hostname " + hostname);
            InetAddress remoteAddress = InetAddress.getByName(hostname);
            //check its IP number
            System.out.println("Hostname has IP address = " +
                    remoteAddress.getHostAddress());

            packet.setAddress(remoteAddress);
            packet.setPort(2000);

            socket.send(packet);
            System.out.println ("Packet sent at!" + new Date());

            // Display packet information
            System.out.println ("Sent by  : " + remoteAddress.getHostAddress() );
            System.out.println ("Send from: " + packet.getPort());
        }
        catch (UnknownHostException ue){
            System.out.println("Unknown host "+hostname);
        }
        catch (IOException e)	{
            System.out.println ("Error - " + e);
        }

    }
}
