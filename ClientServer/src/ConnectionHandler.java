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
<<<<<<< HEAD
import UserInformation.Professor;
import UserInformation.TeachingAssistant;
import UserInformation.Tutorial;

=======
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

>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7

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
<<<<<<< HEAD
	
	private Professor createProf(String username, String password) throws IOException
	{
		Professor prof = new Professor(username, password);
		//createCourses(prof);
		return prof;
	}
	
	private void createCourses(TeachingAssistant t) throws IOException
	{
		String username = t.getUsername();
		BufferedReader br = new BufferedReader(new FileReader("Database/TA/" + username + "/courses.txt"));
=======

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

>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
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
<<<<<<< HEAD
					populateTutorial(splitArray[0], newTutorial);
					newCourse.addTutorial(newTutorial);		
=======
					newCourse.addTutorial(newTutorial);
>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
				}
				t.addCourse(newCourse);
			}
		}
<<<<<<< HEAD
		
		
	}
	private void populateTutorial(String courseCode, Tutorial tutorial) 
	{
		//Read lesson plan student list and such from the tutorial directory and populate them into the tutorial object
		
	}
=======


	}

>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
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
<<<<<<< HEAD
                
=======

>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
                String cmd = (String)in.readObject();
                if (cmd.equals("login"))
                {
			        String username = (String)in.readObject();
			        String password = (String)in.readObject();
<<<<<<< HEAD
			        
			        System.out.println("Username: " + username + "\nPassword: " + password);
			        
			        if (checkAccount(username, password) == 1)
=======

			        System.out.println("Username: " + username + "\nPassword: " + password);
                                int accountType = checkAccount(username, password);
			        if (accountType == 1)
>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
			        {
	                            System.out.println("The user has logged in!!");
	                            Professor prof = createProf(username, password);
	                            prof.printProfile();
<<<<<<< HEAD
	                            
	                            out.writeObject("1");
	                    	    out.writeObject(prof);
			        }
			        else if (checkAccount(username, password) == 2)
=======
	                            out.writeObject("1");
	                    	    out.writeObject(prof);
			        }
			        else if (accountType == 2)
>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
			        {
	                            System.out.println("The user has logged in!!");
	                            TeachingAssistant t = createTA(username, password);
	                            t.printProfile();
<<<<<<< HEAD
	                            out.writeObject("2");                         
=======
	                            out.writeObject("2");
>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
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
<<<<<<< HEAD
                else
                {
                        //out.println("invalid");
                }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    private void registerUser(String toAdd) {
=======
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
                	ArrayList<Student> studentList = createStudentList(courseCode, tutSection);
                	out.writeObject(studentList);
                }
                else if (cmd.equals("getGroups"))
                {
                	String courseCode = (String)in.readObject();
                	String tutSection = (String)in.readObject();
                	ArrayList<ArrayList<Student>> groups = createGroups(courseCode, tutSection);
                	out.writeObject(groups);
                }
                else if (cmd.equals("createLessonPlan"))
                {
                	//Create a lesson plan in the database
                	//Respond to client whether the lesson plan was saved or not (0 or 1)
                	int newOrEdit = (Integer)in.readObject();
                	String courseCode = (String)in.readObject();
                	String tutSection = (String)in.readObject();
                	String header = (String)in.readObject();
                	String content = (String)in.readObject();
                	System.out.println("Checking if file exists...");
                	if (!lessonPlanExists(courseCode, tutSection, header) || newOrEdit == 1)
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
                else if (cmd.equals("getAssignments"))
                {
                	String courseCode = (String)in.readObject();
                	String tutSection = (String)in.readObject();
                	File f = new File("Database/Courses/" + courseCode + "/" + tutSection + "/Grades");
                	File[] listOfFiles = f.listFiles();
                	ArrayList<String> assignmentNames = new ArrayList<String>();
                	String header;
                	String content;
                	for (int i = 0; i < listOfFiles.length; i++)
                	{
                		assignmentNames.add(listOfFiles[i].getName());
                	}
                	out.writeObject(assignmentNames);
                	
                }
                else if (cmd.equals("getAssignmentTotal"))
                {
                	String courseCode = (String)in.readObject();
                	String tutSection = (String)in.readObject();
                	String assignmentName = (String)in.readObject();
                	String path = "Database/Courses/" + courseCode + "/" + tutSection + "/Grades/" + assignmentName;
                	String assignmentTotal = getAssignmentTotal(path);
                	out.writeObject(assignmentTotal);
                }
                else if (cmd.equals("checkAssignmentName"))
                {
                	String courseCode = (String)in.readObject();
                	String tutSection = (String)in.readObject();
                	String assignmentName = (String)in.readObject();
                	File f = new File("Database/Courses/" + courseCode + "/" + tutSection + "/Grades/" + assignmentName);
                	if (f.exists())
                		out.writeObject("1");
                	else
                		out.writeObject("0");
                }
                else if(cmd.equals("addGrades"))
                {
                	String courseCode = (String)in.readObject();
                	String tutSection = (String)in.readObject();
                	String assignmentName = (String)in.readObject();
                	String assignmentTotal = (String)in.readObject();
                	@SuppressWarnings("unchecked")
					ArrayList<Student> students = (ArrayList<Student>)in.readObject();
                	String path = "Database/Courses/" + courseCode + "/" + tutSection + "/Grades/" + assignmentName;
                    try {
            			PrintWriter printwriter = new PrintWriter(new BufferedWriter(new FileWriter(path, true)));
            			printwriter.println(assignmentTotal);
            			for (int i = 0; i < students.size(); i++)
            			{
            				printwriter.println(students.get(i).getUtorid() + "," + students.get(i).getGrade());
            			}
            			printwriter.close();
            		} catch (IOException e) {
            			e.printStackTrace();
            		}          	
                }
                else if(cmd.equals("getStudentGrade"))
                {
                	String courseCode = (String)in.readObject();
                	String tutSection = (String)in.readObject();
                	String assignmentName = (String)in.readObject();
                	String studentName = (String)in.readObject();
                	String path = "Database/Courses/" + courseCode + "/" + tutSection + "/Grades/" + assignmentName;
                	String grade = getStudentGrade(path, studentName);
                	out.writeObject(grade);
                }
                else if(cmd.equals("changeGrade"))
                {
                	String courseCode = (String)in.readObject();
                	String tutSection = (String)in.readObject();
                	String assignmentName = (String)in.readObject();
                	String studentName = (String)in.readObject();
                	String newGrade = (String)in.readObject();
                	String path = "Database/Courses/" + courseCode + "/" + tutSection + "/Grades/" + assignmentName;
                	changeGrade(path, studentName, newGrade);
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
	private void retrunGradeEmail(String courseCode, String tutSection)
	{	
		String email = "";
		String path = "Database/Courses/" + courseCode + "/" + tutSection + "/Grades";
    	File f = new File(path);
    	File[] listOfFiles = f.listFiles();	
    	for (int i = 0; i < listOfFiles.length; i++)
    	{
            try {
    			BufferedReader br = new BufferedReader(new FileReader(path + "/" + listOfFiles[i].getName()));
    			String assignmentTotal = br.readLine();
    			email += listOfFiles[i].getName() + " / " + assignmentTotal + "\n";
    			br.close();
    		} catch (IOException e) 
    		{
    			e.printStackTrace();
    		}
    	}
    	email += "\n"; //end the header portion
    	
        try {
			BufferedReader studentReader = new BufferedReader(new FileReader
					( "Database/Courses/" + courseCode + "/" + tutSection + "/studentList.txt"));
			String currentLine;
			String[] splitArray;
			while ((currentLine = studentReader.readLine()) != null)
			{
				splitArray = currentLine.split(",");
				email += splitArray[0] + " " + splitArray[1];
				email += getAllGrades(splitArray[1], path) + "\n";
			}
			studentReader.close();
		} catch (IOException e) 
		{
			e.printStackTrace();
		} 	
 
	}
	
	private String getAllGrades(String studentId, String path)
	{
		File f = new File(path);
		File[] listOfFiles = f.listFiles();		
    	for (int i = 0; i < listOfFiles.length; i++)
    	{
            try {
    			BufferedReader br = new BufferedReader(new FileReader(path + "/" + listOfFiles[i].getName()));
    			br.readLine();//skip assignment total
    			    			br.close();
    		} catch (IOException e) 
    		{
    			e.printStackTrace();
    		}
    	}
		return "";
	}
	private void changeGrade(String path, String studentId, String newGrade)
	{
		String content = "";
		try {
			
			String currentLine;
			BufferedReader br = new BufferedReader(new FileReader(path));
			content = content + br.readLine() + "\n";
			String splitArray[];
			while ((currentLine = br.readLine()) != null)
			{
				splitArray = currentLine.split(",");
				if (splitArray[0].equals(studentId))
				{
					content = content + splitArray[0] + "," + newGrade + "\n";
				}
				else
				{
					content = content + currentLine + "\n";
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 	
		
        try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, false)));
			out.write(content);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private String getStudentGrade(String path, String studentId)
	{
		try {

			String currentLine;
			BufferedReader br = new BufferedReader(new FileReader(path));
			br.readLine(); // skip past assignment total
			String splitArray[];
			while ((currentLine = br.readLine()) != null)
			{
				splitArray = currentLine.split(",");
				if (splitArray[0].equals(studentId))
				{
					br.close();
					return splitArray[1];
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 		
		return "";		
	}
	private String getAssignmentTotal(String path)
	{
		try {

			String currentLine;
			BufferedReader br = new BufferedReader(new FileReader(path));
			if ((currentLine = br.readLine()) != null)
			{
				br.close();
				return currentLine;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} 		
		return "0";
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
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, false)));
			out.write(content);
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
				Student student = new Student(splitArray[1], splitArray[0]);
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
    
    public ArrayList<ArrayList<Student>> createGroups(String courseCode, String tutSection)
    {
    	String path = "Database/Courses/" + courseCode + "/" + tutSection + "/groups.txt";
    	ArrayList<ArrayList<Student>> groups = new ArrayList<ArrayList<Student>>();
    	ArrayList<Student> group = new ArrayList<Student>();
        try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String currentLine;
			String[] splitArray;
			while ((currentLine = br.readLine()) != null) // each line is a group
			{
				splitArray = currentLine.split(","); // split by comma gives each group member
				for (int i = 0; i < splitArray.length; i++)
				{
					group.add(new Student(splitArray[i])); //add each member to the group
				}
				groups.add(group); //add the group to the list of groups
				group = new ArrayList<Student>(); // reset group to prepare for next group
			}
			br.close();
			return groups;
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
    	return null;
    }

	private void registerUser(String toAdd) {
>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Database/LoginDatabase/login.txt", true)));
            out.println(toAdd);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}