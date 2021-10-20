package ClientSide;



import Test.Packet;
import Test.packetFactory;

/**
 * La couche de transport du cpte client qui lancera la creation des paquet a partir du fichier a envoyer
 */
public class transportClient {
    /**
     * Createur de l'ensemble des paquets necessaires
     * @param i_message array de byte contenant le buffer contenant l'information du fichier creer par l'application
     * @param i_ip Addresse ip du serveur
     * @param filename le nom du file a envoyer
     * @return array de packet
     */
    public Packet[] creerTrame(byte[] i_message,String i_ip,String filename) {
        packetFactory factory = new packetFactory();
        return factory.createPacketList(i_message,i_ip,filename);
    }
}