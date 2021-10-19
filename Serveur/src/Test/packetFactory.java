package Test;

public class packetFactory {

    Packet[] createPacketList(byte [] i_fichierComplet,String i_ip){
        int totalPacket=i_fichierComplet.length/200;
        if(i_fichierComplet.length % 200 != 0){
            totalPacket++;
        }
        Packet[] transmission = new Packet [totalPacket];
        transmission[0]=new Packet(i_ip,0,totalPacket);
        int packetnumber=1;
        int totalArrayPosition=0;
        byte[] buffer = new byte[200];
        byte[] smallBuffer= new byte[i_fichierComplet.length % 200];
        while(packetnumber!=totalPacket){
            for (int i=0; i<200;i++,totalArrayPosition++){
                buffer[i]=i_fichierComplet[totalArrayPosition];
            }
            transmission[packetnumber]= new Packet(buffer,i_ip,packetnumber,totalPacket+1);
            packetnumber++;
        }
        if(i_fichierComplet.length % 200 != 0){
            for (int i=0; i<smallBuffer.length;i++,totalArrayPosition++){
                smallBuffer[i]=i_fichierComplet[totalArrayPosition];
            }
            transmission[packetnumber]= new Packet(smallBuffer,i_ip,packetnumber,totalPacket+1);
        }
        return transmission;
    }

}
