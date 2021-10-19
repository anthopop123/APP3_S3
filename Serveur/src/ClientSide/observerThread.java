package ClientSide;

import java.io.File;
import java.util.Scanner;

public class observerThread extends Thread{
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
                System.out.print("Enter the IP address of the destination :  ");
                String ipAddress= sc.nextLine();
                System.out.println("File to be transferred :" +nameOfTheFile);
                System.out.println("Destination address :" +ipAddress);
                //appeler la couche transport
            }
            if(inputCmd == 8) {
                System.exit(0);
            }
        }
    }
}
