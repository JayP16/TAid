package UserInformation;

import java.io.Serializable;


public class Tutorial implements Serializable
{
	private String tutorialCode;
<<<<<<< HEAD
	
	public Tutorial(String tutorialCode) 
=======
	private String StartTime;
	private String Endtime;
	private String day;
	private String roomNumber;

	public Tutorial(String tutorialCode)
>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
	{
		this.tutorialCode = tutorialCode;
	}

<<<<<<< HEAD
	public String getTutCode() 
=======
	public String getTutCode()
>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
	{
		// TODO Auto-generated method stub
		return tutorialCode;
	}
}
