import ServerSide.linkServer;
import ServerSide.transportServer;
import Test.TransmissionErrorException;

import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class main {
    public static void main(String[] args) throws IOException {
        int portServeur = 25500;
        linkServer ls = new linkServer(portServeur);
        String header = "Erreur/lors de l'envoie du paquet no ! :(";
        byte[] buf = new byte[256];
        Charset charset = StandardCharsets.UTF_8;
        buf = charset.encode(header).array();
        long test = linkServer.verify(buf);
        System.out.println(test);
        try {
            ls.receiveSocket();
        } catch (TransmissionErrorException e) {
            e.printStackTrace();
        }
    }
}
