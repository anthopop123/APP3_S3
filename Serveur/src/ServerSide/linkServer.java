package ServerSide;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class linkServer {
    public static long verify(byte[] bytes){

        Checksum checksum = new CRC32();

        checksum.update(bytes, 0, bytes.length);

        long checksumValue = checksum.getValue();

        System.out.println("Verifier les valeur avec le sum de : " + checksumValue);

        return checksumValue;
    }

    public DatagramPacket receiveSocket(byte[] args){

        return null;
    }

    public static void createStat(String message){
        Logger logger = Logger.getLogger("MyLog");
        FileHandler fh;

        try {

            fh = new FileHandler("C:\\Users\\antho\\AppData\\Local\\GitHubDesktop\\app-2.8.0\\APP3_S3\\Serveur\\liasonDeDonnes.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            logger.info(message);

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

    }

}
