/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpdatetimeclient;

/**
 *
 * @author hartigan
 */

import java.io.*;
import java.net.*;

public class UDPDateTimeClient {
    
    private static InetAddress host;
    private static final int PORT = 4040;
    private static DatagramSocket dgramSocket;
    private static DatagramPacket inPacket, outPacket;
    private static byte[] buffer;
    
    public static void main(String[] args) {
        try{
            host = InetAddress.getLocalHost();
        }
        catch(UnknownHostException e){
            System.out.println("Host Not Found!");
            System.exit(1);
        }
        run();
    }
    
    private static void run(){
        try{
            dgramSocket = new DatagramSocket();
            BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));
            String dot = null;
            String response = null;
            
            System.out.println("Please enter DATE to return today's date or"
                    + " Time to return the current time");
            dot = userEntry.readLine();

            outPacket = new DatagramPacket(dot.getBytes(),dot.length(), host, PORT);
            dgramSocket.send(outPacket);

            buffer = new byte[256];
            inPacket = new DatagramPacket(buffer, buffer.length);
            dgramSocket.receive(inPacket);
            response = new String(inPacket.getData(), 0, inPacket.getLength());
            System.out.println(response);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            System.out.println("Closing Connection");
            dgramSocket.close();
        }
    }
    
}
