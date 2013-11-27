package UserInformation;

import junit.framework.TestCase;

public class CourseTest extends TestCase {
	
	
	public CourseTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetCourseCode() {
		Course course  = new Course("CSCC01");
		assertEquals(course.getCourseCode(), "CSCC01");
	}

	public void testAddTutorial() {
		Course course  = new Course("CSCC01");
		course.addTutorial(new Tutorial("TUT1"));
		assertEquals(course.getTutorials().get(0).getTutCode(), "TUT1");
	}
	
	public void testAddTutorial2()
	{
		Course course  = new Course("CSCC01");
		course.addTutorial(new Tutorial("TUT1"));
		course.addTutorial(new Tutorial("TUT2"));
		course.addTutorial(new Tutorial("TUT3"));
		assertEquals(course.getTutorials().get(0).getTutCode(), "TUT1");		
	}

	public void testGetTutorialLoadEmpty() {
		Course course  = new Course("CSCC01");
		assertTrue(course.getTutorialLoad() == 0);
	}
	
	public void testGetTutorialLoadMultiple() {
		Course course  = new Course("CSCC01");
		course.addTutorial(new Tutorial("TUT1"));
		course.addTutorial(new Tutorial("TUT2"));
		course.addTutorial(new Tutorial("TUT3"));
		assertTrue(course.getTutorialLoad() == 3);
	}

}
