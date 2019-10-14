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
            String equation = null;
            String response = null;
            System.out.println("Hello welcome to my TCP Calulator!");
            System.out.println("Type 'stop' at anytime to end the programme and connection!");
            System.out.println("Spaces do not matter!");
            System.out.println("Formating is (num/operator/num) opertators(+, -, *, /)");
            do{
                System.out.println("Enter the equation you would like to use: ");
                equation = userEntry.readLine();
                out.println(equation);
                
                response = in.readLine();
                if (!response.equals("stop")){
                    if (response.contains("Wrong Operator")){
                        System.out.println(response);
                    }
                    else{
                        System.out.println("\n<SERVER> Your answer is " + response);
                    }
                }
            }
            while(!equation.toLowerCase().equals("stop"));
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
