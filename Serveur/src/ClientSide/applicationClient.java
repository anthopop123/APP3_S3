package ClientSide;
import java.io.File;
import Test.TransmissionErrorException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class applicationClient {
    File fichierAEnvoyer;

    void close() {

    }
    void stopCommunication(byte[] packet){

    }

    public byte[] creationMessage(String i_nom) {
        File fichier = new File(i_nom);
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(fichier);
        } catch (FileNotFoundException e) {
            System.out.println(e);
            return null;
        }
        byte buffer[]=new byte[(int)fichier.length()];
        try {
            stream.read(buffer);
        } catch (IOException e) {
            System.out.println(e.toString());
            return null;
        }
        return buffer;
    }
}
