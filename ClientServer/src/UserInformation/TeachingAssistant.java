package UserInformation;
import java.io.Serializable;
import java.util.ArrayList;


public class TeachingAssistant extends User implements Serializable
{
	private ArrayList<Course> courses = new ArrayList<Course>();
	public TeachingAssistant(String username, String password) 
	{
		super(username, password);
	}

	public void addEmptyCourse(String courseCode) 
	{
		courses.add(new Course(courseCode));
	}
	
	public void addCourse(Course c)
	{
		courses.add(c);
	}
	
	public void printProfile()
	{
		
		System.out.println("---------------------------------");
		System.out.println("Login Information");
		System.out.println("Username: " + username);
		System.out.println("Password: " + password);
		System.out.println("---------------------------------\n");
		
		
		for (int i = 0; i < courses.size(); i++)
		{
			System.out.println("---------------------------------");
			courses.get(i).printData();
			System.out.println("---------------------------------\n");
		}
		
	}

}
