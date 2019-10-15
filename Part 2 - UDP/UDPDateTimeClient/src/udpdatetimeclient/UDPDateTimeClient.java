package udpdatetimeclient;

/**
 * 
 * @author Wayne Hartigan (x16348136)
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
            Boolean correct = false;
            
            while(correct==false){
                
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
                if (!response.equals("Sorry wrong input please try again!")){
                    correct = true;
                }
                else{
                    correct = false;
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            System.out.println("Thank you for using my UDP Date/Time Display!");
            System.out.println("Creator: Wayne Hartigan (x16348136)!");
            dgramSocket.close();
        }
    }
    
}
