package tests.server.facilitybooking;

import servr.facilitybooking.FacilityManager;
import junit.framework.TestCase;

public class FacilityManagerTest extends TestCase {

	public FacilityManagerTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public final void testGetAllFAcilities(){
		FacilityManager fm = new FacilityManager();
		String[] facils = fm.getAllFacilitiesNames();
		
	}

}
