package tests;

import tests.client.MainTest;
import tests.communication.PacketTest;
import tests.communication.SocketTest;
import tests.communication.fieldwrappers.AvailabilityArrayFieldWrapperTest;
import tests.communication.fieldwrappers.DateTimeFieldWrapperTest;
import tests.communication.fieldwrappers.IdFieldWrapperTest;
import tests.communication.fieldwrappers.IntegerFieldWrapperTest;
import tests.communication.fieldwrappers.StringArrayFieldWrapperTest;
import tests.communication.fieldwrappers.StringFieldWrapperTest;
import tests.communication.messages.AllFacilitiesMessageTest;
import tests.communication.messages.BookMessageTest;
import tests.communication.messages.BookedMessageTest;
import tests.communication.messages.BookingExtendedMessageTest;
import tests.communication.messages.BookingShiftedMessageTest;
import tests.communication.messages.ExtendBookingMessageTest;
import tests.communication.messages.FacilityChangedMessageTest;
import tests.communication.messages.GetAllFacilitiesMessageTest;
import tests.communication.messages.MonitorFacilityMessageTest;
import tests.communication.messages.QueryAvailibilityMessageTest;
import tests.communication.messages.ShiftBookingMessageTest;
import tests.communication.utils.ByteConversionsTest;
import tests.communication.utils.DateTimeTest;
import tests.communication.utils.IdTest;
import tests.communication.utils.UtilsTest;
import tests.server.facilitybooking.BookingTest;
import tests.server.facilitybooking.FacilityTest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for communication.tests");
		//$JUnit-BEGIN$
		
		//---------------------- field wrappers -------------------------------
		suite.addTestSuite(StringFieldWrapperTest.class);
		suite.addTestSuite(IntegerFieldWrapperTest.class);
		suite.addTestSuite(DateTimeFieldWrapperTest.class);
		suite.addTestSuite(StringArrayFieldWrapperTest.class);
		suite.addTestSuite(IdFieldWrapperTest.class);
		suite.addTestSuite(AvailabilityArrayFieldWrapperTest.class);

		//---------------------- utils ----------------------------------------
		suite.addTestSuite(DateTimeTest.class);
		suite.addTestSuite(UtilsTest.class);
		suite.addTestSuite(ByteConversionsTest.class);
		
		//---------------------- communication --------------------------------
		suite.addTestSuite(IdTest.class);
		suite.addTestSuite(PacketTest.class);
		suite.addTestSuite(SocketTest.class);
		
		//---------------------- messages -------------------------------------
		suite.addTestSuite(QueryAvailibilityMessageTest.class);
		suite.addTestSuite(BookMessageTest.class);
		suite.addTestSuite(BookedMessageTest.class);
		suite.addTestSuite(ShiftBookingMessageTest.class);
		suite.addTestSuite(BookingShiftedMessageTest.class);
		suite.addTestSuite(ExtendBookingMessageTest.class);
		suite.addTestSuite(BookingExtendedMessageTest.class);
		suite.addTestSuite(GetAllFacilitiesMessageTest.class);
		suite.addTestSuite(AllFacilitiesMessageTest.class);	
		suite.addTestSuite(MonitorFacilityMessageTest.class);
		suite.addTestSuite(FacilityChangedMessageTest.class);
		//suite.addTestSuite(IntervalExpiredMessageTest.class);
		
		//---------------------- client ---------------------------------------
		suite.addTestSuite(MainTest.class);
		
		//---------------------- server ---------------------------------------
		suite.addTestSuite(BookingTest.class);
		suite.addTestSuite(FacilityTest.class);
		//$JUnit-END$
		return suite;
	}

}
