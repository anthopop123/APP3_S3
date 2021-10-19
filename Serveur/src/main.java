import ClientSide.linkClient;
import ServerSide.linkServer;
import ServerSide.transportServer;
import Test.TransmissionErrorException;

import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class main {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        byte[] buf = new byte[256];
        InetAddress address = InetAddress.getByName(args[0]);
        int port = 25500;
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);
        //DatagramPacket packet2 = new DatagramPacket(buf, buf.length);
        //socket.receive(packet2);
        // get response
        transportServer ts = new transportServer();
        buf = new byte[256];
        String header = "ceci est un test";
        Charset charset = StandardCharsets.US_ASCII;
        buf = charset.encode(header).array();
        ts.getAddress(address);
        ts.getPort(port);
        try {
            ts.readReceipt(buf);
        } catch (TransmissionErrorException | IOException e) {
            e.printStackTrace();
        }

        long un = linkServer.verify(buf);
        long deux = linkClient.verify(buf);
        if(un == deux)
        {
            System.out.println("meme check");
        }
        linkServer.createStat("test1");
        socket.close();
    }
}
