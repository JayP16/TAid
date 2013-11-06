package UserInformation;

import junit.framework.TestCase;

public class TutorialTest extends TestCase {

	public TutorialTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testGetTutCode() {
		Tutorial t = new Tutorial("Tut01");
		assertEquals(t.getTutCode(), "Tut01");
	}
	public final void testGetTutCodeLong() {
		Tutorial t = new Tutorial("Tuuuuuuutttttttttttttttt01");
		assertEquals(t.getTutCode(), "Tuuuuuuutttttttttttttttt01");
	}

}
