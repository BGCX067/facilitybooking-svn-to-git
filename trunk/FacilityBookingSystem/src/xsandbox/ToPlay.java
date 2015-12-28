package xsandbox;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import client.userinterface.UI;

import communication.fieldwrappers.FieldWrapper;
import communication.messages.ErrorMessage;
import communication.messages.Message;
import communication.messages.client.BookMessage;
import communication.messages.client.ClientMessage;
import communication.utils.ByteConversions;
import communication.utils.DateTime;
import communication.utils.DaysOfWeek;

public class ToPlay {

	/**
	 * @param args
	 */

	public static void main2(String[] args) {
		DaysOfWeek day = DaysOfWeek.Tuesday;
		DaysOfWeek a = DaysOfWeek.values()[2];
		System.out.println(a);

	}

	public static void main3(String[] args) {
		ToPlay pl = new ToPlay();
		String messageClassName = ToPlay.class.getName();
		try {
			Class<?> classa = Class.forName(messageClassName);
			System.out.println(classa.getCanonicalName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public static void main(String[] args) throws Exception {
		Hashtable<String, Integer> hash = new Hashtable<String, Integer>();
		String a = "what up";
		hash.put(a, 1);
		hash.put(a, 2);
		hash.put(a, 1);
		hash.put(a, 2);
		System.out.println(hash.size());
		
		
		
	}

}
