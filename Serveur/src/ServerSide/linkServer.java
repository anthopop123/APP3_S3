package ServerSide;



import Test.TransmissionErrorException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.zip.CRC32;
import java.util.zip.Checksum;


/**
 * Represente la couche de liaison de donnée du serveur permettant l'analyse des informations et la generation du log.
 */
public class linkServer {
    int portServeur = 6969;
    long crcClient;
    DatagramSocket socket;
    byte[] entree = new byte[256];
    byte[] fin = new byte[256];

    /**
     *Createur de la classe linkServeur avec parametre
     * @param port int pour initilise le port du socket serveur
     * @throws IOException quand le socket n'est pas libre
     */
    public linkServer(int port) throws IOException {
        socket = new DatagramSocket(port);
        portServeur = port;
    }

    /**
     * Createur de la classe linkServeur sans parametre
     * @throws IOException quand le socket n'est pas libre
     */
    public linkServer() throws IOException {
        socket = new DatagramSocket(portServeur);

    }
    /**
     * Permet de lancer un crc selon un array de byte
     * @param bytes array de byte a traité avec le crc
     * @return valeur du crc de type long
     */
    public static long verify(byte[] bytes){

        Checksum checksum = new CRC32();

        checksum.update(bytes, 0, bytes.length);

        return checksum.getValue();
    }

    /**
     * Fonction receptrice du packet qui analyse le header retire le crc et lance le transportServer et update le Log en cas de probleme
     * @throws IOException Si j'ai une erreur dans les packets qui ne peux pas se generer.
     * @throws TransmissionErrorException throw si j'ai 3 fichier perdu dans le transportServer lors de la verification d'arriver des paquet.
     */
    public void receiveSocket() throws IOException, TransmissionErrorException {
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        entree = new byte[packet.getLength()];
        System.arraycopy(buf, 0,entree,0,entree.length);
        byte[] crcB = new byte[8];
        System.arraycopy(entree, 0,crcB,0,8);
        int portClient = packet.getPort();
        InetAddress addressClient = packet.getAddress();
        String crcS = new String(crcB,StandardCharsets.UTF_8);
        System.out.println(crcS);
        crcClient = Long.parseLong(crcS);
        System.arraycopy(entree, 7,entree,0,entree.length-8);
        long crcResult= verify(entree);
        socket.close();
        transportServer ts = new transportServer(portServeur,portClient,addressClient);
        if(crcResult != crcClient){
            createLog("Recu avec erreur de crc! :P");
            ts.readReceipt(entree,true);
        }
        else{
            ts.readReceipt(entree,false);
        }

    }

    /**
     * Fonction de creation du log et l'ajouts de logs à l'interieur de celui ci
     * @param message le message string a mettre dans le log.
     */
    public static void createLog(String message){
        Logger logger = Logger.getLogger("liasonDeDonnes");
        FileHandler fh;

        try {
            fh = new FileHandler("C:\\Users\\antho\\AppData\\Local\\GitHubDesktop\\app-2.8.0\\APP3_S3\\Serveur\\liasonDeDonnes.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.info(message);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }


    }

}
