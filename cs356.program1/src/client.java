import java.io.*;
import java.net.*;


public class client {
	private static int userNum = (int) (Math.random() * 9000);
	/**
	 * @param args Should be a single input either 0 or 1 depending on which excersize should be run.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		if(args[0].equals("0")){
			ex0();
		}else if(args[0].equals("1")){
			ex1();
		}else{
			System.out.println("Error, invalid arguments. Please enter either 0 or 1.");
			System.exit(1);
		}
		
	}
	
	//This is the method to run exercise one, where the client acts like a server
	private static void ex1() throws IOException {
		Socket sockLeft = null;
		PrintWriter out = null; // output stream which will be attached to the socket sockLeft
		BufferedReader in = null; // input stream which will be attached to the socket sockLeft
		
		ServerSocket sockRight = null;
		
		//Initialize the client socket (sockLeft)
		try{
			sockLeft = new Socket("venice.cs.utexas.edu", 35600);
			out = new PrintWriter(sockLeft.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sockLeft.getInputStream()));
		}catch (UnknownHostException e){
			System.err.println("Can't find host: venice.cs.utexas.edu");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Can't get an I/O connection to host: venice.cs.utexas.edu");
			System.exit(1);
		}
		//Initialize the server socket (sockRight)
		try{
			sockRight = new ServerSocket(0);
		}catch(IOException e){
			System.out.println("Could not find a free port. Aborting....");
		    System.exit(-1);
		}
		
		//**Get my ip address and the port number for the server socket**
		String my_ip = InetAddress.getLocalHost().getHostAddress();
		int my_port = sockRight.getLocalPort(); // Now the port we send is for the SERVER socket, so that the venice server will connect to our serverSocket
		
		out.println("ex1 128.83.120.202-35600 "+ my_ip + "-" + my_port + " " + userNum + " J.A.Shield\n"); //Send first request
		//**Read responses**
		String firstResponse = in.readLine(); // receive first awk (Note that nothing is done with this since it's just empty string.)
		String secondResponse = in.readLine(); // receive second awk
		//**Print responses**
		System.out.println(secondResponse);
		
		String[] pieces = secondResponse.split("\\s+");
		if(pieces.length != 4 || !pieces[0].equals("OK")){ // If we don't get OK or there is some other problem, exit.
			System.err.println("Cannot continue due to improper server response.");
			out.close();
			in.close();
			sockLeft.close();		//Close everything
			sockRight.close();
			System.exit(1);
		}
		//Now we're sure that the server's response was "OK" so we can go ahead and grab the server number.
		int firstServNum = Integer.parseInt(pieces[3]);
		
		//**Wait to accept a connection over the ServerSocket sockRight**
		Socket sockDirty = null; // This socket is connected to the server venice.cs.utexas.edu
		try {
		    sockDirty = sockRight.accept();
		} catch (IOException e) {
		    System.out.println("Accept failed: " + sockRight.getLocalPort());
		    System.exit(-1);
		}
		//**Connection has now been established on the second line. Set up readers for output and input**
		PrintWriter outDirty = new PrintWriter(sockDirty.getOutputStream(), true);
		BufferedReader inDirty = new BufferedReader( new InputStreamReader( sockDirty.getInputStream()) );
		
		String servResponse = inDirty.readLine();//Read in what the server sends, saving it so we can parse the random int it sent.
		//System.out.println(servResponse);
		
		String[] piecesZwei = servResponse.split("\\s+");
		if(piecesZwei.length != 5){ // Make sure the server sent the correct response: "CS 356 server calling SOME_NUM"
			System.err.println("Cannot continue due to improper server response.");
			outDirty.close();
			inDirty.close();
			out.close();
			in.close();
			sockLeft.close();		//Close everything
			sockRight.close();
			System.exit(1);
		}
		int secondServNum = Integer.parseInt(piecesZwei[4]);
		System.out.println("CS 356 server sent " + secondServNum);
		
		//Now send the two server numbers back to the server venice.
		String response = (firstServNum + 1) + " " + (secondServNum + 1) + "\n";
		outDirty.println(response); // Send the response
		
		// Now the venice server should terminate this connection, so we can close all these things
		outDirty.close();
		inDirty.close();
		sockDirty.close();
		sockRight.close();
		
		//**The server should send us confirmation on our original socket, so let's listen for that**
		System.out.println(in.readLine());
		
		//*Done, close connections**
		out.close();
		in.close();
		sockLeft.close();
		System.out.println("Done.");
	}

	//This is the method to run exercise zero, the 4 way handshake.
	public static void ex0() throws IOException{
		Socket sock = null;
		PrintWriter out = null; // output stream which will be attached to the socket
		BufferedReader in = null; // input stream which will be attached to the socket
		
		try{
			sock = new Socket("venice.cs.utexas.edu", 35600);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
		}catch (UnknownHostException e){
			System.err.println("Can't find host: venice.cs.utexas.edu");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Can't get an I/O connection to host: venice.cs.utexas.edu");
			System.exit(1);
		}
		String my_ip = InetAddress.getLocalHost().getHostAddress();
		int my_port = sock.getLocalPort();
		
		out.println("ex0 128.83.120.202-35600 "+ my_ip + "-" + my_port + " " + userNum + " J.A.Shield\n"); //Send first request
		
		//**Read responses**
		String firstResponse = in.readLine(); // receive first awk (Note that nothing is done with this since it's just empty string.)
		String secondResponse = in.readLine(); // receive second awk

		
		System.out.println(secondResponse);
		
		// Split the server's second response so we can use the userID and serverID
		String[] pieces = secondResponse.split("\\s+");
		if(pieces.length != 4 || !pieces[0].equals("OK")){ // If we don't get OK or there is some other problem, exit.
			System.err.println("Cannot continue due to improper server response.");
			out.close();
			in.close();
			sock.close();
			System.exit(1);
		}
		
		int incServID = Integer.parseInt(pieces[3]) + 1; // Get the server ID, convert to an int, then increment 
		
		out.println("ex0 " + pieces[1] + " " + incServID + "\n"); // Send awk
		
		String thirdResponse = in.readLine(); // receive third awk
		System.out.println(thirdResponse);
		
		if(!thirdResponse.split("\\s+")[9].equals("OK")){
			System.err.println("Server response not 'OK'. Error! Aborting");
			out.close();
			in.close();
			sock.close();
			System.exit(1);
		}
		
		
		out.close();
		in.close();
		sock.close();
		System.out.println("Done.");
	}

}
