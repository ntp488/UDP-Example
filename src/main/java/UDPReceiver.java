import java.net.*;

public class UDPReceiver implements Runnable {
    volatile MulticastSocket socket = null;
    volatile byte[] buffer = new byte[256];

    @Override
    public void run() {
        try {
            socket = new MulticastSocket(4001);
            InetAddress group = InetAddress.getByName("224.0.0.1");
            socket.joinGroup(group);

            while (UdpExampleMain.stopThreads == false) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String messageAssembler = new String(packet.getData(), 0, packet.getLength());
                //UdpExampleMain.PostMessage(messageAssembler);
            }
            socket.leaveGroup(group);
            socket.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
