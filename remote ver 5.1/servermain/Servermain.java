
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class Servermain{
	public static HashMap<String, ArrayList<String> > database = new HashMap<String, ArrayList<String>>();
	public static ArrayList<String>  inner;
	public static HashMap<String, Integer > indices = new HashMap<String, Integer>();
	private static ArrayList<String> services = new ArrayList<String>();
	private static String prompt = "Press";
	public static void main(String[] args){
	    try{
	    	ServerSocket servsock = new ServerSocket(10000);
			while(true){
		    	Socket clientSocket = servsock.accept();
				
				// PrintStream os = new PrintStream(clientSocket.getOutputStream());			
				DataInputStream din = new DataInputStream(clientSocket.getInputStream());
				DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
			
				int choice = din.readInt();
				String service;
				switch(choice){
					case 0: {
						while(true){
							service = din.readUTF();					
							if(service.equals("done")) break;
							String ip = clientSocket.getInetAddress().getHostAddress();
							if(database.containsKey(service)){
								inner = database.get(service);
								inner.add(ip);
							}
							else{
								inner = new ArrayList<String>();
								inner.add(ip);
								database.put(service,inner);
								indices.put(service,0);
								prompt = (prompt + " " + services.size() + ": For " + service + " |");
								// System.out.println(prompt);
								services.add(service);
							}
							System.out.println(service + ": " + ip);
						}
						break;
					}
					case 1: {
						String ip;
						dos.writeUTF(prompt);
						dos.flush();
						choice = din.readInt();
						service = services.get(choice);
						ip = database.get(service).get( indices.get(service) );
						indices.put(service, ( (indices.get(service)+1)%( database.get(service).size() ) ) );
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
