import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import UserInformation.Course;
import UserInformation.LessonPlan;
import UserInformation.Professor;
import UserInformation.Student;
import UserInformation.TeachingAssistant;
import UserInformation.Tutorial;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ConnectionHandler implements Runnable
{
	private Socket soc;
	ServerSocket serverSocket = null;

	public ConnectionHandler(Socket soc)
	{
		this.soc = soc;
		Thread t = new Thread(this);
		t.start();
	}
	private TeachingAssistant createTA(String username, String password) throws IOException
	{
		TeachingAssistant t = new TeachingAssistant(username, password);
		createCourses(t);
		return t;
	}

	private Professor createProf(String username, String password) throws IOException
	{
		Professor prof = new Professor(username, password);
		createCourses(prof);
		return prof;
	}

	private void createCourses(TeachingAssistant t) throws IOException
	{
		String username = t.getUsername();
                BufferedReader br = null;
                File path = new File("Database/" + t.getUserDir() + "/courses.txt");
                try{
                    br = new BufferedReader(new FileReader(path));
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }

		String currentLine;
		String[] splitArray;
		Course newCourse;
		while ((currentLine = br.readLine()) != null)
		{
			splitArray = currentLine.split("\\s+");
			if (splitArray.length == 1)
			{
				t.addEmptyCourse(splitArray[0]);
			}
			else
			{
				newCourse = new Course(splitArray[0]);
				for (int i = 1; i < splitArray.length; i++)
				{
					Tutorial newTutorial = new Tutorial(splitArray[i]);
					populateTutorial(splitArray[0], newTutorial);
					newCourse.addTutorial(newTutorial);
				}
				t.addCourse(newCourse);
			}
		}


	}
	private void populateTutorial(String courseCode, Tutorial tutorial)
	{
		//Read lesson plan student list and such from the tutorial directory and populate them into the tutorial object
		

	}
	public int checkAccount(String user, String pass)
	{
		BufferedReader br = null;
		try {

			String currentLine;
			br = new BufferedReader(new FileReader("Database/LoginDatabase/login.txt"));
			String[] splitArray;
			while ((currentLine = br.readLine()) != null)
			{
				splitArray = currentLine.split("\\s+");
				if (user.equals(splitArray[0]))
				{
					if (pass.equals(splitArray[1]))
					{
						System.out.println(Integer.parseInt(splitArray[2]));
						return Integer.parseInt(splitArray[2]);
					}
					br.close();
					return 0;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
		return 0;
	}

	public void run()
	{
		System.out.println("Someone is trying to login...");
		try {
				ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(soc.getInputStream());

                String cmd = (String)in.readObject();
                if (cmd.equals("login"))
                {
			        String username = (String)in.readObject();
			        String password = (String)in.readObject();

			        System.out.println("Username: " + username + "\nPassword: " + password);
                                int accountType = checkAccount(username, password);
			        if (accountType == 1)
			        {
	                            System.out.println("The user has logged in!!");
	                            Professor prof = createProf(username, password);
	                            prof.printProfile();
	                            out.writeObject("1");
	                    	    out.writeObject(prof);
			        }
			        else if (accountType == 2)
			        {
	                            System.out.println("The user has logged in!!");
	                            TeachingAssistant t = createTA(username, password);
	                            t.printProfile();
	                            out.writeObject("2");
	                    	    out.writeObject(t);
			        }
			        else
			        {
	                            System.out.println("Invalid login credentials...");
			        }
			        soc.close();
			        in.close();
			        out.close();
                }
                else if (cmd.equals("register"))
                {
                        /**String username = in.readLine();
                        String password = in.readLine();
                        String toAdd = username + " " + password;
                        System.out.println(toAdd);
                        System.out.println("Registering...");
                        registerUser(toAdd);
                        out.println("1");*/
                }
                else if (cmd.equals("getAllCourses"))
                {
                    List<String> lines = Files.readAllLines(Paths.get("Database/Courses/allCourses.txt"), Charset.defaultCharset());
                    String[] allCourses = lines.toArray(new String[lines.size()]);

                    Professor prof = (Professor)in.readObject();
                    List<String> enrolled = Files.readAllLines(Paths.get("Database/" + prof.getUserDir() + "/courses.txt"), Charset.defaultCharset());
                    String[] y = enrolled.toArray(new String[lines.size()]);
                    //remove the courses the user is already registered in
                    List<String> list = new ArrayList<String>();
                    list.addAll(Arrays.asList(allCourses));
                    list.removeAll(Arrays.asList(y));
                    String[] x = list.toArray(new String[list.size()]);

                    out.writeObject(x);
                }
                else if (cmd.equals("addProfCourses"))
                {
                    int i;
                    ArrayList<String> courses = (ArrayList<String>)in.readObject();
                    String username = (String)in.readObject();

                    File theDir = new File("Database/Prof/" + username);
                    if (!theDir.exists()){  // Checks that Directory/Folder Doesn't Exists!
                        theDir.mkdir();
                    }
                    File coursesTxt = new File("Database/Prof/" + username + "/courses.txt");
                    if (!coursesTxt.exists()){  // Checks that Directory/Folder Doesn't Exists!
                        coursesTxt.createNewFile();
                    }
                    List<String> lines = Files.readAllLines(Paths.get("Database/Prof/" + username + "/courses.txt"), Charset.defaultCharset());
                    String[] y = lines.toArray(new String[lines.size()]);

                    List<String> list = new ArrayList<String>();
                    list.addAll(Arrays.asList(courses.toArray(new String[list.size()])));
                    list.removeAll(Arrays.asList(y));
                    list.addAll(Arrays.asList(y));
                    String[] x = list.toArray(new String[list.size()]);

                    PrintWriter pw = new PrintWriter(coursesTxt);
                    for(String s : x){
                        pw.println(s);
                    }
                    pw.close();
                }
                else if (cmd.equals("getStudentList"))
                {
                	String courseCode = (String)in.readObject();
                	String tutSection = (String)in.readObject();
                	createStudentListFile(courseCode, tutSection);
                	ArrayList<Student> studentList = createStudentList(courseCode, tutSection);
                	out.writeObject(studentList);
                }
                else if (cmd.equals("createLessonPlan"))
                {
                	//Create a lesson plan in the database
                	//Respond to client whether the lesson plan was saved or not (0 or 1)
                	System.out.println("Create Lesson plan");
                	String courseCode = (String)in.readObject();
                	System.out.println("Create Lesson plan");
                	String tutSection = (String)in.readObject();
                	System.out.println("Create Lesson plan");
                	String header = (String)in.readObject();
                	System.out.println("Create Lesson plan");
                	String content = (String)in.readObject();
                	System.out.println("Checking if file exists...");
                	if (!lessonPlanExists(courseCode, tutSection, header))
                	{
                		String path = "Database/Courses/" + courseCode + "/" + tutSection + "/LessonPlans/" + header;
                		System.out.println(content);
                		createLessonPlan(path, content);
                		out.writeObject("1");
                	}
                	else
                	{
                		System.out.println("Exists");
                		out.writeObject("0");
                	}
                	
                }
                else if (cmd.equals("getLessonPlans"))
                {
                	String courseCode = (String)in.readObject();
                	String tutSection = (String)in.readObject();
                	File f = new File("Database/Courses/" + courseCode + "/" + tutSection + "/LessonPlans");
                	File[] listOfFiles = f.listFiles();
                	ArrayList<LessonPlan> lp = new ArrayList<LessonPlan>();
                	String header;
                	String content;
                	for (int i = 0; i < listOfFiles.length; i++)
                	{
                		header = listOfFiles[i].getName();
                		content = getLessonPlanContent(courseCode, tutSection, header);
                		lp.add(new LessonPlan(header, content));
                	}
                	out.writeObject(lp);
                }
                else
                {
                	System.out.println("invalid command");
                        //out.println("invalid");
                }
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private String getLessonPlanContent(String courseCode, String tutSection, String header)
	{
    	String path = "Database/Courses/" + courseCode + "/" + tutSection + "/LessonPlans/" + header;
        try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String currentLine;
			String content = "";
			while ((currentLine = br.readLine()) != null)
			{
				content += currentLine;
				content += "\n";
			}
			content = content.trim();
			br.close();
			return content;
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
        return "";
	}
	private void createLessonPlan(String path, String content)
	{
        try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, true)));
			String[] splitArray;
			splitArray = content.split("\\n");
			for (int i = 0; i < splitArray.length; i++)
			{
				out.println(splitArray[i]);
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	private boolean lessonPlanExists(String courseCode, String tutSection, String lessonHeader)
	{
		File f = new File("Database/Courses/" + courseCode + "/" + tutSection + "/LessonPlans/" + lessonHeader);
		if (f.exists())
		{
			return true;
		}
		return false;
	}
    private ArrayList<Student> createStudentList(String courseCode,
			String tutSection) 
	{
    	String path = "Database/Courses/" + courseCode + "/" + tutSection + "/studentList.txt";
    	ArrayList<Student> s = new ArrayList<Student>();
        try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String currentLine;
			String[] splitArray;
			while ((currentLine = br.readLine()) != null)
			{
				splitArray = currentLine.split(",");
				Student student = new Student(splitArray[0]);
				//TODO add grades to student as well
				s.add(student);
			}
			br.close();
			return s;
		} catch (IOException e) 
		{
			e.printStackTrace();
		}

		return null;
	}
	private void createStudentListFile(String courseCode, String tutSection) 
    {
    	String path = "Database/Courses/" + courseCode + "/" + tutSection + "/";
		File f = new File(path + "studentList.txt");
		if (!f.exists())
		{
	        try {
	            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path + "studentList.txt", true)));
	            BufferedReader br = new BufferedReader(new FileReader(path + "groups.txt"));
				String[] splitArray;
				String currentLine;
				while ((currentLine = br.readLine()) != null)
				{
					splitArray = currentLine.split(",");
					for (int i = 0; i < splitArray.length; i++)
					{
						out.println(splitArray[i]);
					}
				}
				br.close();
	            out.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		else
		{
			//TODO: add new utorids to the list
		}
		
	}
	private void registerUser(String toAdd) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Database/LoginDatabase/login.txt", true)));
            out.println(toAdd);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}