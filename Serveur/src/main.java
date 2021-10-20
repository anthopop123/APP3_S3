import ServerSide.linkServer;
import ServerSide.transportServer;
import Test.TransmissionErrorException;

import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Main du serveur lancant le receive et est pret a recevoir des file
 */
public class main {
    /**
     * Fonction main du serveur lancant son execution et attend des envois de packets
     * @param args string qui sera le nom du serveur host
     * @throws IOException throw si le port est deja allouer dans le socket
     */
    public static void main(String[] args) throws IOException {
        int portServeur = 25500;
        linkServer ls = new linkServer(portServeur);

        try {
            ls.receiveSocket();
        } catch (TransmissionErrorException e) {
            e.printStackTrace();
        }
    }
}
