package ServerSide;


public class transportServer {
    DatagramPacket readReceipt(){


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
    InetAddress noAddresse;

    /**
     * Createur vide de la couche transportServeur avec le port de base
     *
     * @throws IOException
     */
    public transportServer() throws IOException {
        socket = new DatagramSocket(portServeur);

    }

    public transportServer(int pServeur, int pClient, InetAddress address) throws IOException {
        socket = new DatagramSocket(pServeur);
        portClient = pClient;
        portServeur = pServeur;
        noAddresse = address;

    }
    DatagramPacket sendReceipt(){


    }

}

    public void setPortServeur(int port) throws SocketException {
        portServeur = port;
        socket = new DatagramSocket(port);
    }

    public void setPortClient(int port) {
        portClient = port;
    }

    public void setAddress(InetAddress address) {
        noAddresse = address;
    }

    public void readReceipt(byte[] packet) throws TransmissionErrorException, IOException {
        int[] listPosition = new int[131072];
        byte[] pos = new byte[5];
        System.arraycopy(packet, 4, pos, 0, 2);
        position = pos[1];
        position |= pos[0] << 8;
        listPosition[position] = 1;

        for (int i = 0; i < listPosition.length; i++) {
            if (listPosition[i] == 1 && listPosition[i + 1] == 0 && listPosition[i + 2] == 1) {
                baseball++;
                erreur = i + 1;
                sendReceipt(false);
            }
            if (baseball == 3) {
                baseball = 0;
                throw new TransmissionErrorException("Il y a perte de connexion (3 paquets perdu)");
            }
        }

        sendReceipt(true);
        sendApp(packet);
    }

    public void sendReceipt(boolean isReceipt) throws IOException {

        byte[] buf;
        DatagramPacket packet;

        if (isReceipt) {
            linkServer.createLog("Paquet recu !!! :)");
            String header = "Success lors de la reception du paquet no " + erreur + "! :)";
            Charset charset = StandardCharsets.UTF_8;
            buf = charset.encode(header).array();
        } else {
            linkServer.createLog("Paquet perdu !!! :(");
            String header = "Erreur lors de l'envoie du paquet no " + erreur + " ! :(";
            Charset charset = StandardCharsets.UTF_8;
            buf = charset.encode(header).array();
        }
        packet = new DatagramPacket(buf, buf.length, noAddresse, portClient);
        socket.send(packet);
        socket.close();
    }
    public void sendApp(byte[] entree){
        applicationServer app = new applicationServer();
        byte[] sortie = new byte[entree.length];

        System.arraycopy(entree, 0, sortie, 0, entree.length);
        app.appReceive(sortie);
    }
}

