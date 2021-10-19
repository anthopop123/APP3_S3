package ClientSide;
import java.io.File;
import Test.TransmissionErrorException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * public class applicationClient
 * demmare
 */
public class applicationClient {
    String filename;

    public byte[] creationMessage(String i_nom) {
        File fichier = new File(i_nom);
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(fichier);
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
        byte[] buffer =new byte[(int)fichier.length()];
        try {
            assert stream != null;
            stream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        filename = fichier.getName();
        return buffer;
    }
    public String getFilename(){
        return filename;
    }
}
