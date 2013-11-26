package UserInformation;

import java.io.Serializable;

public class Student implements Serializable
{
	private String utorid;
	private String grade;
	
	public Student(String utorid)
	{
		this.utorid = utorid;
	}
	
	public String getUtorid()
	{
		return utorid;
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