import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class Server{
	private static final int maxClientsCount = 10;
	private static final clientThread[] threads = new clientThread[maxClientsCount];
	private static String prompt = "Press";

	public static void main(String[] argv){
	    try{
	    	// The default port.
		    int portNumber = 10000;
		    // The default host.
		    String host = "127.0.0.1";

		    if (argv.length < 1) {
		    	System.out.println("Usage: java Server <host ip>");
		    }
		    else{
		    	host = argv[0];
		    }
		    System.out.println("Now connecting to MainServer at " + host + ":" + portNumber);
			Socket sock = new Socket(host, portNumber);

			int numServices = 0;

			Scanner in = new Scanner(System.in);
			DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
			int choice;
			String service;

			dos.writeInt(0);
			dos.flush();

			while(true){
				System.out.println("Enter the name of the service (\"done\" to complete registering):");
				service = in.next();
				// System.out.println(service);
				dos.writeUTF(service);
				dos.flush();
				if(service.equals("done")) break;
				prompt = (prompt + " " + numServices + ": For " + service + " |");
				numServices++;	
				





				// System.out.println("Press 0: To register a service | -1: To complete registering");
				// choice = in.nextInt();
				// dos.writeInt(choice);
				// dos.flush();
				// if(choice == 0){
				// 	System.out.println("Enter the name of the service:");
				// 	service = in.next();
				// 	dos.writeUTF(service);
				// 	dos.flush();					
				// 	prompt.add( " " + numServices + ": For" + service + " |");
				// 	numServices++;
				// }
				// else	break;
			}

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
