package UserInformation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;


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

	public Professor processFile(String filePath, Professor prof) {
		BufferedReader br = null;
		String line = "";
		String splitBy = ",";
//		try {
//			List<String> lines = Files.readAllLines(Paths.get(filePath), Charset.defaultCharset());
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
	 
		try {
			//read the course schedule file
			br = new BufferedReader(new FileReader(filePath));
			ArrayList<String> courses = new ArrayList<String>();
			HashMap<String, ArrayList<String>> name = new HashMap<String, ArrayList<String>>();
			while ((line = br.readLine()) != null) {
	 
			    // use comma as separator
				String[] data = line.split(splitBy);
				String courseCode = data[0];
				String section    = data[1];
				String day 		  = data[2];
				String startTime  = data[3];
				String endTime    = data[4];
				String room 	  = data[5];
				
				
				if (name.get(courseCode) != null){
					ArrayList<String> x = name.get(courseCode);
					x.add(section);
					name.put(courseCode, x);
				}else{
					ArrayList<String> x = new ArrayList<String>();
					x.add(section);
					name.put(courseCode, x);
				}
				
			}
			for (String s : name.keySet()){
				String y = name.get(s).toString().replace("[", "  ");
				y= y.replace("]", "  ");
				y = y.replace(",", "  ");
				prof.addEmptyCourse(s);
				courses.add(s + y);
			}

			//register the courses to the prof
			prof.registerCourses(prof, courses);
			prof = prof.reset();
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prof;
		
	}
}
