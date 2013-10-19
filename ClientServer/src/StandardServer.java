import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class StandardServer 
{
	
	public static void main(String[] args) throws IOException
	{
	 System.out.println("Booted server.");
	 ServerSocket serverSocket = null;
     try {
         serverSocket = new ServerSocket(6789);
     } catch (IOException e) {
         System.err.println("Could not listen on port: 6789.");
         System.exit(1);
     }

     Socket clientSocket = null;
     try {
	    	 while (true) 
	    	 {
				 clientSocket = serverSocket.accept();
				 ConnectionHandler c = new ConnectionHandler(clientSocket);
	    	 }
		 } 
     catch (IOException e1) {
		         System.err.println("Accept failed.");
		         System.exit(1);
		    }
    
	}
}