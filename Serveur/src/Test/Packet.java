package Test;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import java.util.ArrayList;
import java.util.List;
public class Packet {
    int packetNumber;
    int totalNumberOfPacket;
    byte []  contenu;
    String Source="13151354";
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
        String SipDestination = convertByteArraysToBinary(ipDestination.getBytes());
        String sipSource = convertByteArraysToBinary(Source.getBytes());
        StringBuilder SBcontenu= new StringBuilder();
        for (int i=0; i<contenu.length;i++){
             SBcontenu.append(Integer.toBinaryString(contenu[i]));
        }
        String SContenu = SBcontenu.toString();
        String Scrc = Long.toBinaryString(crc);
        if (SipDestination.length()!=64){
            while (SipDestination.length()!=64){
                SipDestination = "0"+SipDestination;
            }
        }
        if (sipSource.length()!=64){
            while (sipSource.length()!=64){
                sipSource = "0"+sipSource;
            }
        }
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
        if (Scrc.length()!=64){
            while (Scrc.length()!=64){
                Scrc = "0"+Scrc;
            }
        }

        String transmission = Scrc + sipSource + SipDestination + SpacketNumber + StotalNumberOfPacket + SContenu;
        byte[] transmissionFinal;
        transmissionFinal = transmission.getBytes();
        return transmissionFinal;
    }
    void setTotalNumberOfPacket(int i_numberOfPacket){
        totalNumberOfPacket = i_numberOfPacket;
    }
    public static String convertByteArraysToBinary(byte[] input) {
        StringBuilder result = new StringBuilder();
        for (byte b : input) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                result.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return result.toString();
    }
}
