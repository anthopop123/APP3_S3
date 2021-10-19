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
    DatagramSocket socket;
    int position = 0;
    int erreur = 0;
    int portClient = 0;
    int portServeur = 6969;
    int baseball = 0;
    InetAddress noAddresse;

    public transportServer() throws IOException {
        socket = new DatagramSocket(portServeur);
    }
    public transportServer(int pServeur, int pClient, InetAddress address) throws IOException {
        socket = new DatagramSocket(pServeur);
        portClient = pClient;
        portServeur = pServeur;
        noAddresse = address;
    }

    public void setPortServeur(int port) throws SocketException {
        portServeur  = port;
        socket = new DatagramSocket(port);
    }
    public void setPortClient(int port){
        portClient  = port;
    }
    public void setAddress(InetAddress address){
        noAddresse = address;
    }
    public void readReceipt(byte[] packet) throws TransmissionErrorException, IOException {
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

        byte[] buf;
        DatagramPacket packet;


        if(isReceipt){
            linkServer.createStat("Paquet recu !!! :)");
            String header = "Success lors de la reception du paquet no " +erreur+ "! :)";
            Charset charset = StandardCharsets.US_ASCII;
            buf = charset.encode(header).array();
        }
        else{
            linkServer.createStat("Paquet perdu !!! :(");
            String header = "Erreur lors de l'envoie du paquet no "+erreur+" ! :(";
            Charset charset = StandardCharsets.US_ASCII;
            buf = charset.encode(header).array();
        }
        packet = new DatagramPacket(buf, buf.length, noAddresse, portClient);
        socket.send(packet);
        socket.close();
    }
}
