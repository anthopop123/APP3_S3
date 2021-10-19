package ServerSide;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class applicationClient {
    File fichierAEnvoyer;

    void init(String nom, String ip){
        File fichier = new File(nom);
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(fichier);
        } catch (FileNotFoundException e) {
            System.out.println(e);
            return;
        }
        byte buffer[]=new byte[(int)fichier.length()];
        try {
            stream.read(buffer);
        } catch (IOException e) {
            System.out.println(e.toString());
            return;
        }

    }

    void close() {

    }
}