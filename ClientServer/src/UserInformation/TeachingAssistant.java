package UserInformation;
import java.io.Serializable;
import java.util.ArrayList;


public class TeachingAssistant extends User implements Serializable
{
	private ArrayList<Course> courses = new ArrayList<Course>();
<<<<<<< HEAD
	public TeachingAssistant(String username, String password) 
	{
		super(username, password);
	}

	public void addEmptyCourse(String courseCode) 
	{
		courses.add(new Course(courseCode));
	}
	
=======
	public TeachingAssistant(String username, String password)
	{
		super(username, password, 2);
	}
        public TeachingAssistant(String username, String password, int userType)
	{
		super(username, password, userType);
	}
        public TeachingAssistant(){}

	public void addEmptyCourse(String courseCode)
	{
		courses.add(new Course(courseCode));
	}

>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
	public void addCourse(Course c)
	{
		courses.add(c);
	}
<<<<<<< HEAD
	
=======

>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
	public ArrayList<Course> getCourses()
	{
		return courses;
	}
	public int getCourseLoad()
	{
		return courses.size();
	}
<<<<<<< HEAD
	
	public void printProfile()
	{
		
=======

	public void printProfile()
	{

>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
		System.out.println("---------------------------------");
		System.out.println("Login Information");
		System.out.println("Username: " + username);
		System.out.println("Password: " + password);
		System.out.println("---------------------------------\n");
<<<<<<< HEAD
		
		
=======


>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
		for (int i = 0; i < courses.size(); i++)
		{
			System.out.println("---------------------------------");
			courses.get(i).printData();
			System.out.println("---------------------------------\n");
		}
<<<<<<< HEAD
		
=======

>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
	}

}
