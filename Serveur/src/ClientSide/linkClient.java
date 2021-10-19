package ClientSide;
/*
import ServerSide.transportServer;
import Test.Packet;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class linkClient {
    void init(String nom, String ip){

    }
    public void CRC(Packet[] transmission){
        for(int i=0; i<transmission.length; i++){
            transmission[i].generateCrc();
        }

    }
    void send(){
        /*
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        entree = new byte[packet.getLength()];
        System.arraycopy(buf, 0,entree,0,entree.length);

        System.out.println(new String(entree, StandardCharsets.UTF_8));
        int portClient = packet.getPort();
        InetAddress addressClient = packet.getAddress();
        //crcClient = entree[0];
        //System.arraycopy(entree, 1,entree,0,entree.length-1);
        long crcResult= verify(entree);
        socket.close();

    }
    void close() {

    }


    boolean Verify(bits[] args){

    }

    File createStat(){

    }
}
*/