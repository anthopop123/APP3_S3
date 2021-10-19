package Test;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
public class Packet {
    int packetNumber;
    int totalNumberOfPacket;
    byte []  contenu;
    String ipDestination;
    long crc;
    Packet(byte []  i_contenu, String i_ipDestination,int i_packetNumber,int i_totalNumberOfPacket)
    {
        packetNumber=i_packetNumber;
        contenu=i_contenu;
        ipDestination=i_ipDestination;
        totalNumberOfPacket=i_totalNumberOfPacket;
    }
    Packet( String i_ipDestination,int i_packetNumber,int i_totalNumberOfPacket)
    {
        packetNumber=i_packetNumber;
        ipDestination=i_ipDestination;
        totalNumberOfPacket=i_totalNumberOfPacket;
    }
    void generateCrc(){
        Checksum checksum = new CRC32();
        checksum.update(contenu, 0, contenu.length);
        crc = checksum.getValue();
    }
    void setTotalNumberOfPacket(int i_numberOfPacket){
        totalNumberOfPacket = i_numberOfPacket;
    }
}
