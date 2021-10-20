package ClientSide;


import Test.Packet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

/**
 * Classe creant la couche de liaison du client qui produira le crc et l'envoie des fichier
 */
public class linkClient {
    int actuelPos= 0;
    DatagramSocket socket = null;
    DatagramPacket packet= null;

    /**
     * Creer le CRC32 du cote client et l'inclue dnas le paquet respectif
     * @param transmission array de packet de tous les paquets de la communication
     * @param arg le string contenant le nom du l'appareil pour obtenir sont ip local
     * @throws IOException throw si le socket a creer n'est pas libre
     */
    public void CRC(Packet[] transmission, String arg) throws IOException {
        for (int i = 0; i < transmission.length; i++) {
            transmission[i].generateCrc();
        }
        send(transmission, arg);
    }

    /**
     * Produit l'envoie d'un paquet un par un et le revois si on obtient un message du serveur de packet perdu et passe ainsi tous les packets
     * @param transmission array de packet de tous les paquets de la communication
     * @param arg le string contenant le nom du l'appareil pour obtenir sont ip local
     * @throws IOException throw si le socket a creer n'est pas libre
     */
    public void send(Packet[] transmission, String arg) throws IOException {
        socket = new DatagramSocket(25501);
        InetAddress addressClient = InetAddress.getByName(arg);

        for(actuelPos=0; actuelPos<transmission.length; actuelPos++) {
            packet = new DatagramPacket(transmission[actuelPos].tobyte(), transmission[actuelPos].tobyte().length, addressClient, 25500);
            System.out.println(Arrays.toString(packet.getData()));
            socket.send(packet);
            verifySend();
        }
        socket.close();
    }

    /**
     * La fonction recevant le messsage de retour du serveur et influence s'il faut renvoyer le packet precedent
     * @throws IOException si le socket n'est pas libre
     */
    public void verifySend() throws IOException {
        byte[] buf = new byte[256];
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength()-4);
        if(!received.split("/", 0)[0].equals("Success"))
        {
            actuelPos--;
        }
        received.replace("/", " ");
        //System.arraycopy(buf, 0, sortie, 0, sortie.length);
        System.out.println("reussi voici le message de retour : " + received);
    }
}


