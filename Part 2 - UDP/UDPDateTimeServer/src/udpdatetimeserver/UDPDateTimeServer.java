/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpdatetimeserver;

/**
 *
 * @author hartigan
 */

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;  


public class UDPDateTimeServer {

    private static final int PORT = 4040;
    private static DatagramSocket dgramSocket;
    private static DatagramPacket inPacket, outPacket;
    private static byte[] buffer;
    
    public static void main(String[] args) {
        System.out.println("Opening Port");
        try{
            dgramSocket = new DatagramSocket(PORT);
        }
        catch(SocketException e){
            System.out.println("Unable to connect to port!");
            System.exit(1);
        }
        run();
    }
    
    private static void run(){
        try{
            String messageIn, messageOut;
            
            Boolean correct = false;
            
            while(correct==false){
                
                buffer = new byte[256];
                inPacket = new DatagramPacket(buffer, buffer.length);
                dgramSocket.receive(inPacket);
                messageIn = new String(inPacket.getData(), 0, inPacket.getLength());
                
                InetAddress clientAddress = inPacket.getAddress();
                int clientPort = inPacket.getPort();
            
            
                String message = messageIn.replaceAll("\\s+","");
                
                if(message.toLowerCase().equals("date")){
                    correct = true;
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDateTime now = LocalDateTime.now();
                    String time = dtf.format(now);
                    messageOut = "Todays date is " + time;
                }
                else if(message.toLowerCase().equals("time")){
                    correct = true;
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String date = dtf.format(now);
                    messageOut = "Current time is " + date;
                }
                else{
                    messageOut = "Sorry wrong input please try again!";
                    correct = false;
                }
                outPacket = new DatagramPacket(messageOut.getBytes(), messageOut.length(), clientAddress, clientPort);
                dgramSocket.send(outPacket);
            }
            System.out.println("Input Received");
            
            
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            System.out.println("\n *Closing Connection..*");
            System.out.println("\n Thank you for using my UDP Date/Time Display!");
            System.out.println("\n Creator: Wayne Hartigan (x16348136)!");
            dgramSocket.close();
        }
    }
}
