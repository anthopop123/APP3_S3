package ServerSide;

import Test.TransmissionErrorException;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class applicationServer {
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
    public void appReceive(byte[] sortie,String filename){
        System.out.println(new String(sortie, StandardCharsets.UTF_8));
        System.out.println("--------------------------------------------------------");
        write(sortie,filename);

    }
}
