import ServerSide.transportServer;
import Test.TransmissionErrorException;

import java.io.IOException;

public class main {
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
