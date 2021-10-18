package ClientSide;

import java.util.Scanner;
import java.util.*;
public class observerThread extends Thread{
    public void run(){

        Scanner sc= new Scanner(System.in);    //System.in is a standard input stream
        while(true){                           //God, I hate this while true
            int inputCmd = sc.nextInt();
            if(inputCmd == 9){
                String dump = sc.nextLine();
                System.out.print("Enter the name of the file you would like to transfer :  ");
                String nameOfTheFile= sc.nextLine();
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
