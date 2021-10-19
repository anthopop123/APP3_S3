package ClientSide;

import java.io.File;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class linkClient {
    void init(String nom, String ip){

    }

    void close() {

    }


    public static long verify(byte[] bytes){

        Checksum checksum = new CRC32();

        checksum.update(bytes, 0, bytes.length);

        long checksumValue = checksum.getValue();

        System.out.println("Verifier les valeur avec le sum de : " + checksumValue);

        return checksumValue;

    }

    File createStat(){
        return null;
    }
}
