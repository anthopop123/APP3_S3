package ClientSide;
import java.io.File;
import Test.TransmissionErrorException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * public class applicationClient
 * demmare le processus d'envoie en lisant le fichier a envoye et le place dans un buffer
 */
public class applicationClient {
    String filename;

    /**
     * Permet la creation d'un buffer avec un fichier entree
     * @param i_nom string representant le path du file
     * @return array de byte du buffer
     */
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

    /**
     * donne le nom du ficher actuel
     * @return string du nom du fichier
     */
    public String getFilename(){
        return filename;
    }
}
