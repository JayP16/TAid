package UserInformation;

import java.io.Serializable;

public class Student implements Serializable
{
	private String utorid;
	public Student(String utorid)
	{
		this.utorid = utorid;
	}
	
	public String getUtorid()
	{
		return utorid;
	}
}
