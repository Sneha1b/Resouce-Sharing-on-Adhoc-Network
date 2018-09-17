import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class Server{
	private static final int maxClientsCount = 10;
	private static final clientThread[] threads = new clientThread[maxClientsCount];

	public static void main(String[] argv){
	    try{
	    	// The default port.
		    int portNumber = 10000;
		    // The default host.
		    String host = "127.0.0.1";

		    if (argv.length < 1) {
		    	System.out.println("Usage: java Server <host ip> \n");
		    }
		    else{
		    	host = argv[0];
		    }
		    System.out.println("Now connecting to MainServer=" + host + ", portNumber=" + portNumber);
			Socket sock = new Socket(host, portNumber);

			Scanner in = new Scanner(System.in);
			DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
			
			System.out.println("Press 0: To add a service");
			int choice = in.nextInt();  
			System.out.println("Enter the name of the service:");
			String service = in.next();
			dos.writeInt(choice);
			dos.flush();
			dos.writeUTF(service);
			dos.flush();

			in.close();
			dos.close();
			sock.close();	
			//End connection with mainserver

			//Start hosting the service
	    	ServerSocket servsock = new ServerSocket(9999);
	    	while(true){
	    		Socket clientSocket = servsock.accept();
		        int i = 0;
		        for (i = 0; i < maxClientsCount; i++) {
		        	if (threads[i] == null) {
		        		(threads[i] = new clientThread(clientSocket, threads)).start();
		            	break;
		          	}
		        }
		        if (i == maxClientsCount) {
		        	PrintStream os = new PrintStream(clientSocket.getOutputStream());
			        os.println("Server too busy. Try later.");
			        os.close();
			        clientSocket.close();
		        }
	    	}
	    } catch(IOException e){
	    		System.out.println(e);
	    }
	}
}
