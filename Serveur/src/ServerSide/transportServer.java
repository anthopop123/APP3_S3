package ServerSide;


import Test.TransmissionErrorException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * La couche de transport du cote serveur de communication permettant la verification du juste transfert des divers paquet et envoie renvois les confirmation au client tout en ajoutant des logs des evenements.
 */
public class transportServer {
    DatagramSocket socket;
    int position = 0;
    int erreur = 0;
    int portClient = 0;
    int portServeur = 6969;
    int baseball = 0;
    int fileCompletSize = 0;
    byte[] fileComplet = new byte[1000];
    String filename = "test.txt";

    InetAddress noAddresse;

    /**
     * Createur vide de la couche transportServeur, initialise le port de base
     *
     * @throws IOException
     */
    public transportServer() throws IOException {
        socket = new DatagramSocket(portServeur);

    }

    /**
     * Le createur avec tout les arguments pour la couche transportServeur
     * @param pServeur
     * @param pClient
     * @param address
     * @throws IOException
     */
    public transportServer(int pServeur, int pClient, InetAddress address) throws IOException {
        socket = new DatagramSocket(pServeur);
        portClient = pClient;
        portServeur = pServeur;
        noAddresse = address;

    }


    /**
     * Permet de changer le port serveur
     * @param port int du port desirer
     * @throws SocketException si le port est deja utilise
     */
    public void setPortServeur(int port) throws SocketException {
        portServeur = port;
        socket = new DatagramSocket(port);
    }

    /**
     * Permet de changer le port d'envoie
     * @param port int du port desirer
     */
    public void setPortClient(int port) {
        portClient = port;
    }

    /**
     * Changer l'addresse ip de communication
     * @param address InetAddress de l'ip
     */
    public void setAddress(InetAddress address) {
        noAddresse = address;
    }

    /**
     * Permet de lire les informations du header et de verifier s'il y a eu perte de packet
     * @param packet array de byte contenant le packet
     * @throws TransmissionErrorException S'il y a une perte de 3 paquets
     */

    public void readReceipt(byte[] packet,boolean crc,linkServer server) throws TransmissionErrorException {
        int[] listPosition = new int[131072];
        byte[] pos = new byte[5];
        System.arraycopy(packet, 15, pos, 0, 2);
        position = pos[1];
        position |= pos[0] << 8;

        listPosition[position] = 1;

        for (int i = 0; i < listPosition.length; i++) {
            if (listPosition[i] == 1 && listPosition[i + 1] == 0 && listPosition[i + 2] == 1 || crc) {
                baseball++;
                erreur = i + 1;
                try {
                    sendReceipt(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (baseball == 3) {
                baseball = 0;
                throw new TransmissionErrorException("Il y a perte de connexion (3 paquets perdu)");
            }
        }

        try {
            sendReceipt(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendApp(packet,server);
    }

    /**
     * Permet d'envoyer la valeur de retour au client
     * @param isReceipt valeur boolean de success de la lecture des information de readReceipt
     * @throws IOException Si le packet a creer n'existe pas par exemple avec un length negatif
     */
    public void sendReceipt(boolean isReceipt) throws IOException {

        byte[] buf;
        DatagramPacket packet;

        if (isReceipt) {
            linkServer.createLog("Paquet recu !!! :)");
            String header = "Success/lors de la reception du paquet no " + erreur + "! :)";
            Charset charset = StandardCharsets.UTF_8;
            buf = charset.encode(header).array();
        } else {
            linkServer.createLog("Paquet perdu !!! :(");
            String header = "Erreur/lors de l'envoie du paquet no " + erreur + "! :(";
            Charset charset = StandardCharsets.UTF_8;
            buf = charset.encode(header).array();
        }
        packet = new DatagramPacket(buf, buf.length, noAddresse, portClient);
        socket.send(packet);
        socket.close();
    }

    /**
     * Permet  l'application Serveur avec le bon formatage en retirant le header et ensuite envoyer l'ensemble du packet
     * @param entree de byte qui refere le packet actuelle
     */
    public void sendApp(byte[] entree,linkServer server){
        applicationServer app = new applicationServer();
        byte[] sortie = new byte[entree.length];
        if(position == 0){
            byte[] filenameArray = new byte[5];
            System.arraycopy(entree, 19, filenameArray, 0, entree.length-20);
            System.arraycopy(entree, 17, sortie, 0, 2);
            fileCompletSize = sortie[1];
            fileCompletSize |= sortie[0] << 8;
            fileComplet = new byte[fileCompletSize*200];
            filename = new String(filenameArray, StandardCharsets.UTF_8);
        }
        else if(position == fileCompletSize-1){
            System.arraycopy(entree, 19, sortie, 0, entree.length-20);
            System.arraycopy(sortie, 0, fileComplet, position*200, sortie.length);
            app.appReceive(fileComplet, filename);
        }
        else{
            System.arraycopy(entree, 19, sortie, 0, entree.length-20);
            System.arraycopy(sortie, 0, fileComplet, position*200, entree.length-1);
            try {
                server.receiveSocket();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TransmissionErrorException e) {
                e.printStackTrace();
            }
        }

    }
}

