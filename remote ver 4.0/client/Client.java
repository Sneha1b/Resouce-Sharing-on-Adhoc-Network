import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class Client{
	public static void main(String[] argv) throws Exception{
		
		Socket sock = new Socket("127.0.0.1", 9999);

		Scanner in = new Scanner(System.in);
    	DataInputStream din = new DataInputStream(sock.getInputStream());		
		DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
		
		// System.out.print("folder path: ");
		// String folder = in.nextLine();
		// dos.writeUTF(folder);
		// dos.flush();
		String folder = "";

		System.out.print("filename: ");
		String filename = in.nextLine();
		dos.writeUTF(filename);
		dos.flush();



		//file transfer initialise
		Path path = FileSystems.getDefault().getPath(filename);
		int fileSize = (int) Files.size(path);
		dos.writeInt(fileSize);
		byte[] mybytearray = new byte[fileSize];
		BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(path));
		bis.read(mybytearray, 0, mybytearray.length);
		OutputStream os = sock.getOutputStream();
		os.write(mybytearray, 0, mybytearray.length);
		os.flush();
		bis.close();
		//file transfer complete

		while(true){
			System.out.print("command (q to quit): ");
			String command=in.nextLine();
			dos.writeUTF(command);
			dos.flush();
			if(command.equals("q")){
				break;
			}
			else{
				// Scanner a=new Scanner(sock.getInputStream());
				// while(a.hasNextLine()){
				// 	String line=a.nextLine();
				// 	System.out.println(line);
				// }
				// a.close();

				while(true){
					String line=din.readUTF();
					if(line.equals("")) break;
					System.out.println(line);
				}
			}
		}

		in.close();
		dos.close();
		sock.close();
	}
  
}
