package Test;

/**
 * factory de packet qui crer des packet selon le nombre qu'on a de besoin dans le fichier
 */
public class packetFactory {
    /**
     * creer la liste de packet de moins de 200 octet selon la taille du fichier
     * @param i_fichierComplet
     * @param i_ip string donnant addresse ip du serveur actuel
     * @param filename string donnant le nom du fichier a convertire en liste de packet
     * @return array de packet de moins de 200 octet representant le fichier a envoyer
     */
    public Packet[] createPacketList(byte [] i_fichierComplet,String i_ip,String filename){
        int totalPacket=i_fichierComplet.length/200;
        if(i_fichierComplet.length % 200 != 0){
            totalPacket++;
        }
        Packet[] transmission = new Packet [totalPacket+1];
        transmission[0]=new Packet(filename.getBytes(),i_ip,0, totalPacket+1);
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
