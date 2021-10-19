package ClientSide;
/*
import ServerSide.transportServer;
import Test.Packet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class linkClient {
    void init(String nom, String ip){

    }
    public void CRC(Packet[] transmission,String arg) throws IOException {
        for(int i=0; i<transmission.length; i++){
            transmission[i].generateCrc();
        }
        send(transmission,arg);
    }
    void send(Packet[] transmission,String arg) throws IOException {
        DatagramSocket socket = null;
        socket = new DatagramSocket(25501);
        InetAddress addressClient = InetAddress.getByName(arg);
        DatagramPacket packet = new DatagramPacket(transmission[i].tobyte(),transmission[i].tobyte().length,addressClient,25500);
        socket.send(packet);

        byte [] buf = new byte[256];
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        byte[] sortie = new byte[packet.getLength()-4];
        System.arraycopy(buf, 0,sortie,0,sortie.length);
        String received = new String(sortie, 0, sortie.length);
        System.out.println("reussi voici le message de retour : " + received);
        socket.close();
	}
}