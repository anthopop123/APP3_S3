package ServerSide;

import Test.TransmissionErrorException;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class applicationServer {

    byte[] buf = new byte[1024];
    BufferedReader in = null;


    void stopCommunication(byte[] packet){

        try {
            transportServer ts = new transportServer();
            ts.readReceipt(packet);
        }
        catch (TransmissionErrorException | IOException te){
            System.out.println("Erreur dans le test : " + te.getMessage());
            System.exit(0);
        }
    }
    void listeners(){

    }

    File write(byte[] args, String filename) throws FileNotFoundException {
        return null;
    }
}
