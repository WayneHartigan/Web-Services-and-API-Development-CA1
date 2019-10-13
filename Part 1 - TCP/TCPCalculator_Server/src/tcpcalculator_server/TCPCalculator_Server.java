package tcpcalculator_server;

/**
 * 
 * @author Wayne Hartigan (x16348136)
 */

import java.io.*;
import java.net.*;

public class TCPCalculator_Server {
    
    private static ServerSocket servSock;
    private static final int PORT = 4040;
    
    public static void main(String[] args) {
        System.out.println("Opening port " + PORT + "....");
        try{
            servSock = new ServerSocket(PORT);
        }
        catch(IOException e){
            System.out.println("Unable to attach to port!");
            System.exit(1);
        }
        
        do{
            run();
        }
        while (true);
    }
    
    private static void run(){
        Socket link = null;
        
        try{
            link = servSock.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            PrintWriter out = new PrintWriter(link.getOutputStream(),true);
            String num1_string = in.readLine();
            String operator = in.readLine();            
            String num2_string = in.readLine();
            String again = in.readLine();
            
            System.out.println(num1_string);
            System.out.println(num2_string);

            
            int num1 = Integer.parseInt(num1_string);
            int num2 = Integer.parseInt(num2_string);
            int answer = 0;
            
            while((!again.equals("N")) || (!again.equals("n"))){
                if (operator.equals("+")){
                    answer = num1 + num2;
                }
                else if (operator.equals("-")){
                    answer = num1 - num2;
                }
                else if (operator.equals("*")){
                    answer = num1 * num2;
                }
                else if (operator.equals("/")){
                    answer = num1/num2;
                }
                else{
                    out.println("Wrong Operator please try again");
                }
                String answer_string = Integer.toString(answer);
                out.println(answer_string);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            try{
                System.out.println("\n *Closing COnnection..*");
                link.close();
            }
            catch(IOException e){
                System.out.println("Unable to disconnect!");
                System.exit(1);
            }
        }
    }  
}
