import java.io.*;
import java.net.*;
import java.util.*;

public class UDPReceiver extends Thread{
    public static void main (String args[]) {
        try {
            DatagramSocket socket = new DatagramSocket(2000);
            System.out.println ("Bound to local port " + socket.getLocalPort());
            DatagramPacket packet = new DatagramPacket( new byte[256], 256 );

            // Receive a packet - remember by default this is a blocking operation
            socket.receive(packet);

            System.out.println ("Packet received at " + new Date( ));
            InetAddress remoteAddress = packet.getAddress();
            System.out.println ("Sender: " + remoteAddress.getHostAddress( ) );
            System.out.println ("from Port: " + packet.getPort());

            ByteArrayInputStream bin = new ByteArrayInputStream
                    (packet.getData());

            // Display only up to the length of the original UDP packet
            for (int i=0; i < packet.getLength(); i++)  {
                int data = bin.read();
                if (data == -1)
                    break;
                else
                    System.out.print ( (char) data) ;
            }
            socket.close( );
        }
        catch (IOException e) 	{
            System.out.println ("Error - " + e);
        }
    }
}
