package tests.communication.messages;



import communication.fieldwrappers.FieldWrapper;
import communication.messages.Message;
import communication.messages.server.AllFacilitiesMessage;

import junit.framework.TestCase;

public class AllFacilitiesMessageTest extends TestCase {

	AllFacilitiesMessage m_message;
	public AllFacilitiesMessageTest(String name) {
		super(name);
		m_message = new AllFacilitiesMessage();
		m_message.allFacilities = new String[]{"one","טרלטרטרטלרטלרטררלטרט‎bbg3U983U938U292Uשששש;;,';34l,535938y5309485yh3045y34;,r[,fdf,[sd[fsdf,[sdfp,sfsdf[sdf,sfsdfa","three","","this","is","test","one","two","three","","this","is","test","one","two","three","","this","is","test","one","two","three","","this","is","test","one","two","three","","this","is","test","one","טרלטרטרטלרטלרטררלטרט‎bbg3U983U938U292Uשששש;;,';34l,535938y5309485yh3045y34;,r[,fdf,[sd[fsdf,[sdfp,sfsdf[sdf,sfsdfa","three","","this","is","test","one","two","three","","this","is","test","one","two","three","","this","is","test","one","two","three","","this","is","test","one","two","three","","this","is","test"};
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testSerarializeDeserialize() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InstantiationException {
		byte[] serializedMessage = m_message.serialize();
		AllFacilitiesMessage deserializedMessage = (AllFacilitiesMessage) Message.deserialize(serializedMessage);
		String[] expected = m_message.allFacilities;
		String[] actual = deserializedMessage.allFacilities;
		
		assertEquals(expected.length, actual.length);
		for(int i = 0; i < expected.length; i++){
			assertEquals(expected[i], actual[i]);
		}		
		
		FieldWrapper[] fields = m_message.getFieldWrappers();
		for(FieldWrapper field : fields){
			System.out.println(field.getFieldValueAsString());
		}
	}
}
