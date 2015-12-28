package servr;

import servr.dispatchers.ServerDispatcher;
import communication.Packet;
import communication.Socket;
import client.userinterface.UI;

public class ServerMain {

	public static int serverPort;
	public static InvocationSemantics invocationSemantics;
	static Socket socket;
	
	public static void main(String[] args) {
		ServerMain.printOnConsole("Starting");
		
		boolean parsedSuccessfully = parseArgumentsAndInit(args);
		if(!parsedSuccessfully){
			return;
		}
		
		ServerMain.printOnConsole("Args parsed");
		
		try {
			socket = new Socket(serverPort);
			while(true){	
				int oldTimeOut = socket.getTimeOut();
				
				ServerMain.printOnConsole("Receiving");
				
				socket.setTimeOut(0);
				Packet receivedPacket = socket.receive();
				socket.setTimeOut(oldTimeOut);
				
				ServerMain.printOnConsole("Received");
				
				ServerDispatcher dispatcher = ServerDispatcher.getServerDispatcherForPacket(receivedPacket);
				dispatcher.dispatch();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			socket.close();
		}
		
		shutDown();
	}

	public static Socket getFreeSocket(){
		return socket;
	}
	
	public static boolean parseArgumentsAndInit(String[] args) {
		boolean parsedSuccessfully = false;
		try {
			int requiredNumberOfArguments = 5;
			boolean allArgsEntered = (args.length >= requiredNumberOfArguments);
			if (!allArgsEntered) {
				throw new IllegalArgumentException(
						"Number of argument was smaller than expected.");
			}

			String serverPortString = args[0].replaceFirst("-", "");
			serverPort = Integer.parseInt(serverPortString);
			
			String invocationSemanticString = args[1].replaceFirst("-", "");
			invocationSemantics = InvocationSemantics.valueOf(invocationSemanticString);
			
			String timeOut = args[2].replaceFirst("-", "");
			Socket.TimeOut = Integer.parseInt(timeOut);
			
			String retryCount = args[3].replaceFirst("-", "");
			Socket.RetryCount = Integer.parseInt(retryCount);

			String networkFailureProbability = args[4].replaceFirst("-", "");
			Socket.NetworkFailureProbability = Double.parseDouble(networkFailureProbability);
			
			parsedSuccessfully = true;

		} catch (Exception e) {
			UI.printDelimiter();

			e.printStackTrace();

			UI.printDelimiter();

			System.out.println("Wrong arguments number, format, or order.");
			System.out.println("help: -serverPort -invocationSemantics<{ AtMostOnce| AtLeastOnce}> -timeOut -retryCount -networkFailureProbability");

			UI.printDelimiter();

			parsedSuccessfully = false;
		}

		return parsedSuccessfully;
	}

	public static void printOnConsole(String textToPring){
		UI.printDelimiter();
		System.out.println(textToPring);
		UI.printDelimiter();
	}
	
	public static void shutDown(){
		socket.close();
		UI.printDelimiter();
		System.out.println("Server is about to shutdown, press any key to continue...");
		System.console().readLine();
		
		System.exit(0);
	}
}
