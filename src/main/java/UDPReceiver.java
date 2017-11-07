import java.net.*;

public class UDPReceiver implements Runnable {
    private MulticastSocket socket = null;
    private byte[] buffer = new byte[256];

    @Override
    public void run() {
        try {
            socket = new MulticastSocket(UdpExampleMain.portNumber);
            InetAddress group = InetAddress.getByName(UdpExampleMain.hostName);
            socket.joinGroup(group);

            while (UdpExampleMain.stopThreads == false) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String messageAssembler = new String(packet.getData(), 0, packet.getLength());
                UdpExampleMain.PostMessage(messageAssembler);
            }
            socket.leaveGroup(group);
            socket.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
