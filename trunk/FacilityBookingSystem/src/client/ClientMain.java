package client;

import java.net.InetAddress;

import communication.Socket;
import communication.messages.Message;
import communication.messages.client.EndClientMessage;
import communication.messages.client.GetAllBookingMessage;
import communication.messages.client.GetAllFacilitiesMessage;
import communication.messages.client.QueryAvailibilityMessage;
import communication.messages.server.AllFacilitiesMessage;
import client.dispatchers.ClientDispatcher;
import client.userinterface.UI;


public class ClientMain {
	public static InetAddress serverAddress;
	public static int serverPort;
	public static int clientPort;
	
	public static void main(String[] args) {
		boolean parsedSuccessfully = parseArgumentsAndInit(args);
		if(!parsedSuccessfully)
			return;
			
		while(true){
			try{
			UI.printMenu();
			//GetAllBookingMessage allBookings = new GetAllBookingMessage();
			//allBookings.facilityName = "F3";
			Message choice = UI.getUsersChoice();
			
			boolean endClient = (choice.getClass()== EndClientMessage.class);
			if(endClient){
				return;
			}
			
			ClientDispatcher dispatcher = ClientDispatcher.getDispatcherForMessage(choice);
			Message answer = dispatcher.dispatch();
			UI.printMessage(answer);
			
			System.out.print("Press any key to continue to menu...");
			System.console().readLine();
			}catch(Exception e){
				UI.printDelimiter();
				
				e.printStackTrace();
				
				UI.printDelimiter();
				
				String errorAnnouncement = "An unexpected error occurred. Please try again.";
				UI.printString(errorAnnouncement);
				
				UI.printDelimiter();
			}
		}
	}
	
	public static boolean parseArgumentsAndInit(String[] args) {
		boolean parsedSuccessfully = false;
		try{
			int requiredNumberOfArguments = 6;
			boolean allArgsEntered = (args.length >= requiredNumberOfArguments);
			if(!allArgsEntered){
				throw new IllegalArgumentException("Number of argument was smaller than expected.");
			}
			
			String clientPortString = args[0].replaceFirst("-", "");
			clientPort= Integer.parseInt(clientPortString);
			
			String serverAddressString = args[1].replaceFirst("-", "");
			serverAddress = InetAddress.getByName(serverAddressString);
			
			String serverPortString = args[2].replaceFirst("-", "");
			serverPort = Integer.parseInt(serverPortString);
			
			String timeOut = args[3].replaceFirst("-", "");
			Socket.TimeOut = Integer.parseInt(timeOut);
			
			String retryCount = args[4].replaceFirst("-", "");
			Socket.RetryCount = Integer.parseInt(retryCount);

			String networkFailureProbability = args[5].replaceFirst("-", "");
			Socket.NetworkFailureProbability = Double.parseDouble(networkFailureProbability);
			
			
			parsedSuccessfully = true;
		
		}catch(Exception e){
			UI.printDelimiter();
			
			e.printStackTrace();
			
			UI.printDelimiter();
			
			System.out.println("Wrong arguments number, format, or order.");
			System.out.println("help: -clientPort -serverIPAddress -serverPort -timeOut -retryCount -networkFailureProbability");
			
			UI.printDelimiter();
			
			parsedSuccessfully = false;
		}
		
		return parsedSuccessfully;
	}

}
