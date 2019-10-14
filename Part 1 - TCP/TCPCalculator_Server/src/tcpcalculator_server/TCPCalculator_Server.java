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
            String equation_string = null;
            String equation_string_raw = null;
            do{
                BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
                PrintWriter out = new PrintWriter(link.getOutputStream(),true);
                equation_string_raw = in.readLine(); 
                equation_string = equation_string_raw.replaceAll("\\s","");
                
                int answer = 0;
                int number_1 = 0;
                int number_2 = 0;
                
                if (equation_string.contains("+")){
                    String[] numbers = equation_string.split("\\+");
                    number_1 = Integer.parseInt(numbers[0]);
                    number_2 = Integer.parseInt(numbers[1]);
                    answer = number_1 + number_2;
                }
                else if (equation_string.contains("-")){
                    String[] numbers = equation_string.split("\\-");
                    number_1 = Integer.parseInt(numbers[0]);
                    number_2 = Integer.parseInt(numbers[1]);
                    answer = number_1 - number_2;
                }
                else if (equation_string.contains("*")){
                    String[] numbers = equation_string.split("\\*");
                    number_1 = Integer.parseInt(numbers[0]);
                    number_2 = Integer.parseInt(numbers[1]);
                    answer = number_1 * number_2;
                }
                else if (equation_string.contains("/")){
                    String[] numbers = equation_string.split("\\/");
                    number_1 = Integer.parseInt(numbers[0]);
                    number_2 = Integer.parseInt(numbers[1]);
                    answer = number_1/number_2;
                }
                else{
                    if (!equation_string.toLowerCase().contains("stop")){
                        out.println("Wrong Operator please try again");
                    }    
                }
                String answer_string = Integer.toString(answer);
                answer = 0;
                number_1 = 0;
                number_2 = 0;
                if (!equation_string.toLowerCase().contains("stop")){
                    out.println(answer_string);
                }
                else{
                    out.println("stop");
                }
            }while(!equation_string.toLowerCase().equals("stop"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            try{
                System.out.println("\n *Closing Connection..*");
                link.close();
                System.exit(0);
            }
            catch(IOException e){
                System.out.println("Unable to disconnect!");
                System.exit(1);
            }
        }
    }  
}
