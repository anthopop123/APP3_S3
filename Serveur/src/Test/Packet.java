package Test;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
public class Packet {
    int packetNumber;
    int totalNumberOfPacket;
    byte []  contenu;
    String Source;
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
    public void generateCrc(){
        Checksum checksum = new CRC32();
        checksum.update(contenu, 0, contenu.length);
        crc = checksum.getValue();
    }
    public byte[] tobyte(){
        String SpacketNumber = Integer.toBinaryString(packetNumber);
        String StotalNumberOfPacket = Integer.toBinaryString(totalNumberOfPacket);
        StringBuilder SBcontenu= new StringBuilder();
        for (int i=0; i<contenu.length;i++){
             SBcontenu.append(Integer.toBinaryString(contenu[i]));
        }
        String SContenu = SBcontenu.toString();
        String Scrc = Long.toBinaryString(crc);
        if (SpacketNumber.length()!=16){
            while (SpacketNumber.length()!=16){
                SpacketNumber = "0"+SpacketNumber;
            }
        }
        if (StotalNumberOfPacket.length()!=16){
            while (StotalNumberOfPacket.length()!=16){
                StotalNumberOfPacket = "0"+StotalNumberOfPacket;
            }
        }
        if (Scrc.length()!=8){
            while (Scrc.length()!=8){
                Scrc = "0"+Scrc;
            }
        }
        String transmission = Scrc + Source + ipDestination + SpacketNumber + StotalNumberOfPacket + SContenu;
        byte[] transmissionFinal = new byte[transmission.length()];
        transmissionFinal = transmission.getBytes();
        return transmissionFinal;
    }
    void setTotalNumberOfPacket(int i_numberOfPacket){
        totalNumberOfPacket = i_numberOfPacket;
    }
}
