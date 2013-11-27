package UserInformation;

import java.io.Serializable;

public class Student implements Serializable
{
	private String utorid;
	private String studentNumber;
	private String grade;
	
	public Student(String utorid, String studentNumber)
	{
		this.utorid = utorid;
		this.studentNumber = studentNumber;
	}
	
	public Student(String utorid)
	{
		this.utorid = utorid;
	}
	
	public String getUtorid()
	{
		return utorid;
	}
	
	public String getStudentNumber()
	{
		return studentNumber;
	}
	
	public void setGrade(String grade)
	{
		this.grade = grade;
	}
	
	public String getGrade()
	{
		return grade;
	}
}