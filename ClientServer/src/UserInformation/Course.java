package UserInformation;
<<<<<<< HEAD
import java.io.Serializable;
import java.util.ArrayList;


=======
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
public class Course implements Serializable
{
	private String courseCode;
	private ArrayList<Tutorial> tutorials = new ArrayList<Tutorial>();
<<<<<<< HEAD
=======

>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
	public Course(String courseCode)
	{
		this.courseCode = courseCode;
	}
<<<<<<< HEAD
	
=======

	public Course() {
		// TODO Auto-generated constructor stub
	}

>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
	public String getCourseCode()
	{
		return courseCode;
	}

	public void addTutorial(Tutorial tutorial)
	{
		tutorials.add(tutorial);
<<<<<<< HEAD
		
	}
	
=======

	}

>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
	public int getTutorialLoad()
	{
		return tutorials.size();
	}
<<<<<<< HEAD
	
=======

>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
	public ArrayList<Tutorial> getTutorials()
	{
		return tutorials;
	}

<<<<<<< HEAD
	public void printData() 
=======
	public void printData()
>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
	{
		System.out.println(courseCode);
		for (int i = 0; i < tutorials.size(); i++)
		{
			System.out.println(tutorials.get(i).getTutCode());
		}
<<<<<<< HEAD
		
=======

	}
	public String[] getAllCourses(Professor t)
	{
		String [] result = null;
		Socket clientSocket;
		try
		{
			clientSocket = new Socket(Globals.ipAddress, Globals.port);
			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream printwriter = new ObjectOutputStream(clientSocket.getOutputStream());
			//write the registration information to the server, to add to DB
			printwriter.writeObject("getAllCourses");
			printwriter.writeObject(t);
			result = (String[])in.readObject();
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
	}
}
