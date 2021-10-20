package Test;
import java.math.BigInteger;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import java.util.ArrayList;
import java.util.List;

/**
 * La fonction qui creer un packet avec son contenu et son header
 */
public class Packet {
    int packetNumber;
    int totalNumberOfPacket;
    byte []  contenu;
    String Source="13151354";
    String ipDestination="13151354";
    long crc;

    /**
     * Createur de packet avec l'array de contenu, l'ip de destination, le numero de packet, le nombre total de packet
     * @param i_contenu array de byte contenant le contenu du fichier
     * @param i_ipDestination ip de destination en string
     * @param i_packetNumber int du nombre de paquet
     * @param i_totalNumberOfPacket int du nombre total de packet
     */
    public Packet(byte []  i_contenu, String i_ipDestination,int i_packetNumber,int i_totalNumberOfPacket)
    {
        packetNumber=i_packetNumber;
        contenu=i_contenu;
        ipDestination=i_ipDestination;
        totalNumberOfPacket=i_totalNumberOfPacket;
    }

    /**
     * Createur de packet avec l'ip, le nombre de packet et le nombre total de packet
     * @param i_ipDestination ip de destination en string
     * @param i_packetNumber int du nombre de paquet
     * @param i_totalNumberOfPacket int du nombre total de packet
     */
    public Packet( String i_ipDestination,int i_packetNumber,int i_totalNumberOfPacket)
    {
        packetNumber=i_packetNumber;
        ipDestination=i_ipDestination;
        totalNumberOfPacket=i_totalNumberOfPacket;
    }

    /**
     * Creer le CRC a ajouter
     */
    public void generateCrc(){
        Checksum checksum = new CRC32();
        checksum.update(contenu, 0, contenu.length);
        crc = checksum.getValue();
    }

    /**
     * Creer le header en byte qui doit etre placer devant le contenu ce qui forme le packet final
     * @return array de byte representant l'ensemble du packet
     */
    public byte[] tobyte(){
        String SpacketNumber = Integer.toBinaryString(packetNumber);
        String StotalNumberOfPacket = Integer.toBinaryString(totalNumberOfPacket);
        String SipDestination = convertByteArraysToBinary(ipDestination.getBytes());
        String sipSource = convertByteArraysToBinary(Source.getBytes());
        String Scrc = Long.toBinaryString(crc);
        if (SipDestination.length()!=128){
            while (SipDestination.length()!=128){
                SipDestination = "0"+SipDestination;
            }
        }
        if (sipSource.length()!=128){
            while (sipSource.length()!=128){
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
        String transmission = Scrc + sipSource + SipDestination + SpacketNumber + StotalNumberOfPacket;
        byte[] transmissionIntermediaire;
        transmissionIntermediaire = new BigInteger(transmission, 2).toByteArray();
        byte[] transmissionFinal = new byte[transmissionIntermediaire.length+contenu.length];
        System.arraycopy(transmissionIntermediaire,0,transmissionFinal,0,transmissionIntermediaire.length);
        System.arraycopy(contenu,0,transmissionFinal,transmissionIntermediaire.length-1,contenu.length);
        return transmissionFinal;
    }

    /**
     * set le nombre de packet total de l'array de packet
     * @param i_numberOfPacket int du nombre de packet
     */
    void setTotalNumberOfPacket(int i_numberOfPacket){
        totalNumberOfPacket = i_numberOfPacket;
    }

    /**
     * Convertie les byte en bit
     * @param input array de byte a convertir
     * @return string du bit
     */
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
/*
D:\Simon\Desktop\Repo\APP3_S3\test.txt
*/