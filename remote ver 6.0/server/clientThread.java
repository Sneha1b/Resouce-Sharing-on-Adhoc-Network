import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

class clientThread extends Thread{

	private Socket sock = null;
	private final clientThread[] threads;
	private int maxClientsCount;

	public clientThread(Socket sock, clientThread[] threads) {
		this.sock = sock;
		this.threads = threads;
		maxClientsCount = threads.length;
	}

	public void run() {
		int maxClientsCount = this.maxClientsCount;
		clientThread[] threads = this.threads;

		try {
			DataInputStream in = new DataInputStream(sock.getInputStream());
	    	DataOutputStream out = new DataOutputStream(sock.getOutputStream());
		    while(true){
		    	String filename = in.readUTF();
		    	if(filename.equals("exit")) break; 
		    	int fileSize = in.readInt(); 	

				//transfer file
				synchronized(threads){	    	
			    	Path path = FileSystems.getDefault().getPath(filename);
			    	byte[] mybytearray = new byte[1024];
			    	InputStream is = sock.getInputStream();
					BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(path));
					int bytesRead = is.read(mybytearray, 0, mybytearray.length);
					bos.write(mybytearray, 0, bytesRead);
					bos.close();
				}
				//transfer complete
			}

			String command = "temp";
			while(true){
				command = in.readUTF();				
				if(command.equals("q")){
					break;
				}
				else{
			    	try{
			    		Process p;
			    		synchronized(threads){
							p=Runtime.getRuntime().exec(command);
						}
						Scanner a=new Scanner(p.getInputStream());
						while(a.hasNextLine()){
							String line=a.nextLine();
							out.writeUTF(line);
							// out.write(line);
						}
						a.close();

						Scanner c=new Scanner(p.getErrorStream());
						while(c.hasNextLine()){
							String line=c.nextLine();
							out.writeUTF(line);
							// out.println(line);
						}
						out.writeUTF("");
						c.close();
					}
					catch(Exception e){
						out.writeUTF("error");
						out.writeUTF("");
					}
				}
			}


			sock.close();
		}
		catch (IOException e) {
		}
	}
}
