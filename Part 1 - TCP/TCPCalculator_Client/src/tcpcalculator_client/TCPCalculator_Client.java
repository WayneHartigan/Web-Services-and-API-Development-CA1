package tcpcalculator_client;

/**
 * 
 * @author Wayne Hartigan (x16348136)
*/

import java.net.*;
import java.io.*;

public class TCPCalculator_Client {
    private static InetAddress host;
    private static final int PORT = 4040;
    
    public static void main(String[] args) {
        try{
            host = InetAddress.getLocalHost();
        }
        catch(UnknownHostException e){
            System.out.println("Host ID not found");
            System.exit(1);
        }
        run();
    }
    
    private static void run(){
        Socket link = null;
        try{
            link = new Socket(host, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));

            PrintWriter out = new PrintWriter(link.getOutputStream(),true);
            
            BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));
            String num1 = null;
            String operator = null;
            String num2 = null;
            String response = null;
            String again = null;
            do{
                System.out.println("Enter first number: ");
                num1 = userEntry.readLine();
                out.println(num1);
                
                System.out.println("Enter operator (+, -, *, /) : ");
                operator = userEntry.readLine();
                out.println(operator);
                
                System.out.println("Enter second number: ");
                num2 = userEntry.readLine();
                out.println(num1);
                
                response = in.readLine();
                System.out.println("\n<SERVER> Your answer is " + response);
                
                System.out.println("Would you like to try again? (Y/n)");
                again = userEntry.readLine();
                out.println(again);
                
            }
            while((!again.equals("N")) || (!again.equals("n")));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            try{
                System.out.println("\n Thank you for using my TCP calculator!");
                System.out.println("\n Creator: Wayne Hartigan (x16348136)!");
                link.close();
            }
            catch(IOException e){
                System.out.println("Connection unable to be disconnected");
                System.exit(1);
            }
        }
    }
    
}
