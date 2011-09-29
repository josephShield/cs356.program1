import java.io.*;
import java.net.*;


public class client {
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		Socket sock = null;
		PrintWriter out = null; // output stream which will be attached to the socket
		BufferedReader in = null; // input stream which will be attached to the socket
		
		try{
			sock = new Socket("venice.cs.utexas.edu", 35600);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
		}catch (UnknownHostException e){
			System.out.println("Can't find host: venice.cs.utexas.edu");
			System.err.println("Can't find host: venice.cs.utexas.edu");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Can't get an I/O connection to host: venice.cs.utexas.edu");
			System.err.println("Can't get an I/O connection to host: venice.cs.utexas.edu");
			System.exit(1);
		}
		String my_ip = InetAddress.getLocalHost().getHostAddress();
		int my_port = sock.getLocalPort();
		
		out.println("ex0 128.83.120.202-35600 "+ my_ip + "-" + my_port + " 5555 J.A.Shield\n");

		//BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		//System.out.println(in.readLine() + "*");
		//System.out.println(in.readLine()+ "*");
		String response = "";
		while((response = in.readLine()) != ""){
			System.out.println(response + "*");
		}
		
		
		
		out.close();
		in.close();
		sock.close();
	}

}
