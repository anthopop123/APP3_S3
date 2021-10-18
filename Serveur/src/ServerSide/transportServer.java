package ServerSide;

import Test.TransmissionErrorException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class transportServer {
    DatagramSocket socket = null;
    int baseball = 0;
    int[] listPosition = new int[131072];
    byte[] pos = new byte[5];
    int position;
    public transportServer(){};
    boolean readReceipt(byte[] packet) throws TransmissionErrorException, IOException {
        System.arraycopy(packet, 4,pos,0,2);
        position = pos[1];
        position |= pos[0]<<8;
        listPosition[position] = 1;

        for(int i :listPosition){
            if(listPosition[i] == 1 && listPosition[i+1] == 0 && listPosition[i+2] == 1){
                baseball++;
                sendReceipt(false);
            }
            if(baseball == 3){
                throw new TransmissionErrorException("Il y a perte de connexion (3 paquets perdu)");
            }
        }

        sendReceipt(true);
    }
    DatagramPacket sendReceipt(boolean isReceipt) throws IOException {
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);

        if(isReceipt){

        }
        else{

        }
        return null;
    }
    public static void main(){

    }

}
