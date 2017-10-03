import java.io.*;
import java.net.*;
import java.util.*;

public class UDPReceiver implements Runnable{
    @Override
    public void run() {
        try {
            System.out.println ("Bound to local port " + UdpExampleMain.socket.getLocalPort());
            DatagramPacket packet = new DatagramPacket( new byte[256], 256 );
            ByteArrayInputStream bin;

            while (UdpExampleMain.stopThreads == false) {
                // Receive a packet - remember by default this is a blocking operation
                UdpExampleMain.socket.receive(packet);
                System.out.println ("Packet received at " + new Date( ));
                InetAddress remoteAddress = packet.getAddress();
                System.out.println ("Sender: " + remoteAddress.getHostAddress( ) );
                System.out.println ("from Port: " + packet.getPort());
                bin = new ByteArrayInputStream(packet.getData());

                // Display only up to the length of the original UDP packet
                for (int i=0; i < packet.getLength(); i++)  {
                    int data = bin.read();
                    if (data == -1)
                        break;
                    else
                        System.out.print ( (char) data) ;
                }
            }
            System.out.println("Stopping receiver.");
        }
        catch (IOException e) 	{
            System.out.println ("Error - " + e);
        }
    }
}
