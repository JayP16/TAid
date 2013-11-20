package UserInformation;

import java.io.Serializable;


public class Tutorial implements Serializable
{
	private String tutorialCode;
	private String StartTime;
	private String Endtime;
	private String day;
	private String roomNumber;

	public Tutorial(String tutorialCode)
	{
		this.tutorialCode = tutorialCode;
	}

	public String getTutCode()
	{
		// TODO Auto-generated method stub
		return tutorialCode;
	}
}
