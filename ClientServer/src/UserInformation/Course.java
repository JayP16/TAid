package UserInformation;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Course implements Serializable
{
	private String courseCode;
	private ArrayList<Tutorial> tutorials = new ArrayList<Tutorial>();

	public Course(String courseCode)
	{
		this.courseCode = courseCode;
	}

	public Course() {
		// TODO Auto-generated constructor stub
	}

	public String getCourseCode()
	{
		return courseCode;
	}

	public void addTutorial(Tutorial tutorial)
	{
		tutorials.add(tutorial);

	}

	public int getTutorialLoad()
	{
		return tutorials.size();
	}

	public ArrayList<Tutorial> getTutorials()
	{
		return tutorials;
	}

	public void printData()
	{
		System.out.println(courseCode);
		for (int i = 0; i < tutorials.size(); i++)
		{
			System.out.println(tutorials.get(i).getTutCode());
		}

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
	}
}
