package tests.client;

import client.ClientMain;
import junit.framework.TestCase;

public class MainTest extends TestCase {

	public MainTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testParseArgumentsAndInit() {
		String clientPortString = "2222";
		String serverAddressString = "172.20.206.7";
		String serverPortString = "2222";
		String[] args = new String[]{	clientPortString,
										serverAddressString,
										serverPortString};
		boolean parsedSuccessfully = ClientMain.parseArgumentsAndInit(args);
		assertTrue(parsedSuccessfully);
		assertEquals(ClientMain.clientPort, 2222);
		assertEquals(ClientMain.serverAddress.getHostAddress(), serverAddressString);
		assertEquals(ClientMain.serverPort, 2222);
	}

}
