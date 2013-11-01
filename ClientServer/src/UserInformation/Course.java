package UserInformation;
import java.io.Serializable;
import java.util.ArrayList;


public class Course implements Serializable
{
	private String courseCode;
	private ArrayList<Tutorial> tutorials = new ArrayList<Tutorial>();
	public Course(String courseCode)
	{
		this.courseCode = courseCode;
	}
	
	public String getCourseCode()
	{
		return courseCode;
	}

	public void addTutorial(Tutorial tutorial)
	{
		tutorials.add(tutorial);
		
	}

	public void printData() 
	{
		System.out.println(courseCode);
		for (int i = 0; i < tutorials.size(); i++)
		{
			System.out.println(tutorials.get(i).getTutCode());
		}
		
	}
}
