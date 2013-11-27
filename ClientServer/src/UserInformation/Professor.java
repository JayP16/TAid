package UserInformation;
<<<<<<< HEAD
import java.io.Serializable;
import java.util.ArrayList;


public class Professor extends User implements Serializable
{
	private ArrayList<Course> courses = new ArrayList<Course>();
	public Professor(String username, String password) 
	{
		super(username, password);
	}

	public void addEmptyCourse(String courseCode) 
	{
		courses.add(new Course(courseCode));
	}
	
=======
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class Professor extends TeachingAssistant implements Serializable
{
	private ArrayList<Course> courses = new ArrayList<Course>();
	
	public Professor(String username, String password)
	{
		super(username, password, 1);
	}


	public Professor() {
		// TODO Auto-generated constructor stub
	}

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

	}

	public void registerCourses(Professor prof, ArrayList<String> courses)
	{
		Socket clientSocket;
		try
		{
			clientSocket = new Socket(Globals.ipAddress, Globals.port);
			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream printwriter = new ObjectOutputStream(clientSocket.getOutputStream());
			//write the registration information to the server, to add to DB
			printwriter.writeObject("addProfCourses");
			printwriter.writeObject(courses);
			printwriter.writeObject(prof.getUsername());
			clientSocket.close();
			in.close();
			printwriter.close();
		}
		catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
	}

}
