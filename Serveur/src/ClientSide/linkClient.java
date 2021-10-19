package ClientSide;


import Test.Packet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

public class linkClient {
    int actuelPos= 0;
    DatagramSocket socket = null;
    DatagramPacket packet= null;

    void init(String nom, String ip) {

    }

    public void CRC(Packet[] transmission, String arg) throws IOException {
        for (int i = 0; i < transmission.length; i++) {
            transmission[i].generateCrc();
        }
        send(transmission, arg);
    }

    public void send(Packet[] transmission, String arg) throws IOException {
        socket = new DatagramSocket(25501);
        InetAddress addressClient = InetAddress.getByName(arg);

        packet = new DatagramPacket(transmission[0].tobyte(), transmission[0].tobyte().length, addressClient, 25500);
        System.out.println(Arrays.toString(packet.getData()));
        socket.send(packet);
        //while(actuelPos != transmission.length){
            verifySend();
        //}

        socket.close();
    }
    public void verifySend() throws IOException {
        byte[] buf = new byte[256];
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        byte[] sortie = new byte[packet.getLength()];
        String received = new String(sortie, 0, sortie.length);
        if(received.split("/", 0)[0].equals("Success"))
        {
            actuelPos++;
        }
        System.out.println(actuelPos);
        received.replace("/", " ");
        //System.arraycopy(buf, 0, sortie, 0, sortie.length);
        System.out.println("reussi voici le message de retour : " + received);
    }
}


