package ClientSide;



import Test.Packet;
import Test.packetFactory;


public class transportClient {
    public Packet[] creerTrame(byte[] i_message,String i_ip,String filename) {
        packetFactory factory = new packetFactory();
        return factory.createPacketList(i_message,i_ip,filename);
    }
}