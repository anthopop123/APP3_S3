package ServerSide;

import Test.TransmissionErrorException;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class applicationServer {
    /**
     * Creer un file qui contiendra l'ensemble des packets et lui donnera un nom de filename
     * @param info array de byte contenant l'information a ecrire dans le fichier
     * @param filename string pour representer le nom du fichier
     */
    public void write(byte[] info, String filename) {
        String infoFile = null;
        File fichier;
        FileWriter transcript;
        try {
            fichier = new File(filename);
            transcript = new FileWriter(filename);
            infoFile = new String(info, StandardCharsets.UTF_8);
            if (fichier.createNewFile()) {
                System.out.println("Creation d''un nouveau fichier du nom de " + fichier.getName());
            }
            transcript.write(infoFile);
            transcript.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * function de la couche application pour recevoir l'array de byte et lancer le write.
     * @param sortie array de byte qui formera le fichier.
     * @param filename string representant le nom du fichier.
     */
    public void appReceive(byte[] sortie,String filename) {
        System.out.println(new String(sortie, StandardCharsets.UTF_8));
        System.out.println("--------------------------------------------------------");
        write(sortie, filename);
    }
}
