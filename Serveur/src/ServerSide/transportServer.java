package ServerSide;

import Test.TransmissionErrorException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class transportServer {
    DatagramSocket socket = new DatagramSocket();
    int position;
    int erreur;
    int noPort;
    InetAddress noAddresse;

    public transportServer() throws IOException {
    }

    public void getPort(int port){
        noPort  = port;
    }
    public void getAddress(InetAddress address){
        noAddresse = address;
    }
    public void readReceipt(byte[] packet) throws TransmissionErrorException, IOException {
        int baseball = 0;
        int[] listPosition = new int[131072];
        byte[] pos = new byte[5];
        System.arraycopy(packet, 4,pos,0,2);
        position = pos[1];
        position |= pos[0]<<8;
        listPosition[position] = 1;


        for(int i = 0; i < listPosition.length; i++){
            if(listPosition[i] == 1 && listPosition[i+1] == 0 && listPosition[i+2] == 1){
                baseball++;
                erreur = i+1;
                sendReceipt(false);
            }
            if(baseball == 3){
                throw new TransmissionErrorException("Il y a perte de connexion (3 paquets perdu)");
            }
        }
        sendReceipt(true);
    }
    public void sendReceipt(boolean isReceipt) throws IOException {
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        InetAddress address = packet.getAddress();
        int port = packet.getPort();

        if(isReceipt){
            linkServer.createStat("Paquet recu");
            String header = "ceci est un success";
            Charset charset = StandardCharsets.US_ASCII;
            buf = charset.encode(header).array();
            packet = new DatagramPacket(buf, buf.length, noAddresse, noPort);
            socket.send(packet);
        }
        else{
            linkServer.createStat("Paquet perdu");
            String header = "Il y a eu un erreur lors de l'envoie du paquet no "+erreur+" ! :(";
            Charset charset = StandardCharsets.US_ASCII;
            buf = charset.encode(header).array();
            packet = new DatagramPacket(buf, buf.length, noAddresse, noPort);
            socket.send(packet);
        }
        socket.close();
    }
}
