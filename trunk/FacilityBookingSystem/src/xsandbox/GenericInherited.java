package xsandbox;

public class GenericInherited extends GenericBase<String> {

	public static void main(String[] args) {
	 String value = "ahoj honziku";
	 
	 GenericInherited gi = new GenericInherited();
	 gi.setField(value);
	 
	 System.out.println(gi.getField());
			
	}
}
