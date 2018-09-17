
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class Servermain{
	public static Map <String,String> database = new HashMap<String,String>();
	
	public static void main(String[] args){
	    try{
	    	ServerSocket servsock = new ServerSocket(10000);
			while(true){
		    	Socket clientSocket = servsock.accept();
				
				// PrintStream os = new PrintStream(clientSocket.getOutputStream());			
				DataInputStream din = new DataInputStream(clientSocket.getInputStream());
				DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
		
				int choice = din.readInt();
				String service = din.readUTF();
				switch(choice){
					case 0: {
						String ip = clientSocket.getInetAddress().getHostAddress();
						// int p = clientSocket.getPort();
						// String addr = ip + ":" + p;
						database.put(service,ip);
						System.out.println(service +" " + ip);
						break;
					}
					case 1: {
						String ip;
						if(database.containsKey(service)){
							ip = database.get(service);
						}
						else{
							ip = "Service not found";
						}
						dos.writeUTF(ip);
						dos.flush();
						break;

					}
				}
		    }
	    } catch(IOException e){
		    		System.out.println(e);
	    }
	}
}
