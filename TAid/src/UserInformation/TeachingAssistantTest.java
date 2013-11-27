package UserInformation;

import junit.framework.TestCase;

public class TeachingAssistantTest extends TestCase {

	public TeachingAssistantTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddEmptyCourse() 
	{
		TeachingAssistant p = new TeachingAssistant("test","123");
		p.addEmptyCourse("CSCC01");
		int size = p.getCourses().size();
		assertTrue(p.getCourses().get(size - 1).getTutorialLoad() == 0);
	}

	public void testAddCourse() 
	{
		TeachingAssistant p = new TeachingAssistant("test","123");
		p.addCourse(new Course("CSCC01"));
		assertTrue(p.getCourses().get(0).getCourseCode() == "CSCC01");
	}
	public void testAddCourseNull() 
	{
		TeachingAssistant p = new TeachingAssistant("test","123");
		assertTrue(p.getCourses().size() == 0);
	}

	public void testGetCourseLoad() 
	{
		TeachingAssistant p = new TeachingAssistant("test","123");
		p.addCourse(new Course("CSCC01"));
		p.addCourse(new Course("CSCC02"));
		assertTrue(p.getCourses().size() == 2);
	}
	public void testGetCourseLoadEmpty() 
	{
		TeachingAssistant p = new TeachingAssistant("test","123");
		assertTrue(p.getCourses().size() == 0);
	}
	public void testGetCourseLoadOne() 
	{
		TeachingAssistant p = new TeachingAssistant("test","123");
		p.addCourse(new Course("CSCC01"));
		assertTrue(p.getCourses().size() == 1);
	}

}
