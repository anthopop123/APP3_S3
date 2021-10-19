package ClientSide;

import java.io.File;
import java.util.Scanner;


/**
 *public class observerThread
 * Attend que le client lui envoie une commande
 * Si la commande d'arret est envoye l'application est stopper
 * Si la commande de transfert est envoyer
 * Le thread demande le nom du fichier et l'addresse de destination a l'utilisateur
 * @see "Thread"
 */
public class observerThread extends Thread{
    /**
     * S'occupe de la gestion
     */
    public String nomFichier;
    public String nomAdr;
    public void run(){

        Scanner sc= new Scanner(System.in);    //System.in is a standard input stream
        while(true){                           //God, I hate this while true
            int inputCmd = sc.nextInt();
            if(inputCmd == 9){
                String dump = sc.nextLine();
                Boolean zeFlag = false;
                Boolean isExisting = false;
                String nameOfTheFile = "default";
                while(!zeFlag){
                    System.out.print("Enter the name of the file you would like to transfer :  ");
                    nameOfTheFile= sc.nextLine();
                    File tempFile = new File(nameOfTheFile);
                    isExisting = tempFile.exists();
                    if(isExisting){
                        zeFlag = Boolean.TRUE;
                    }
                }
                System.out.print("Enter the path of the file you would like to transfer :  ");
                nameOfTheFile= sc.nextLine();
                System.out.print("Enter the IP address of the destination :  ");
                String ipAddress= sc.nextLine();
                System.out.println("File to be transferred :" +nameOfTheFile);
                System.out.println("Destination address :" +ipAddress);
                setName2(nameOfTheFile);
                setAdr2(ipAddress);

            }
            if(inputCmd == 8) {
                System.exit(0);
            }
        }

    }

    /**
     * void setName2
     * @param name
     * Doit retenir le nom du fichier a partir du Thread
     */
    public void setName2(String name){
        nomFichier = name;
    }

    /**
     * String getName2
     * @return nomFichier
     * Doit envoyer le nom du fichier au QuoteClient
     */
    public String getName2(){
        return nomFichier;
    }

    /**
     * setAdr2
     * @param Addr
     * Doit retenir l'ip du destinataire a partir du Thread
     */
    public void setAdr2(String Addr){
        nomAdr = Addr;
    }

    /**
     * String getAdr2
     * @return nomAdr
     * Doit envoyer l'ip du destinataire au QuoteClient
     */
    public String getAdr2(){
        return nomAdr;
    }
}