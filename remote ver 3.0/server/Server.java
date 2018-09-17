import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class Server{
	public static void main(String[] args) throws IOException{
	    ServerSocket servsock = new ServerSocket(9999);
	    while (true) {
	    	Socket sock = servsock.accept();
	    	
	    	DataInputStream in = new DataInputStream(sock.getInputStream());
	    	DataOutputStream out = new DataOutputStream(sock.getOutputStream());
	    	// PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())));
	    	String folder = in.readUTF();
	    	String filename = in.readUTF();
	    	int fileSize = in.readInt();	    	

			//transfer file	    	
	    	Path path = FileSystems.getDefault().getPath(folder, filename);
	    	byte[] mybytearray = new byte[1024];
	    	InputStream is = sock.getInputStream();
			BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(path));
			int bytesRead = is.read(mybytearray, 0, mybytearray.length);
			bos.write(mybytearray, 0, bytesRead);
			bos.close();
			//transfer complete

			String command = "temp";
			while(true){
				command = in.readUTF();
				if(command.equals("q")){
					break;
				}
				else{
			    	try{
						Process p=Runtime.getRuntime().exec(command);
						//System.out.println("1");
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
	}
}
