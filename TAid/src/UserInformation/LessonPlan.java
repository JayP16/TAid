package UserInformation;

import java.io.Serializable;

public class LessonPlan implements Serializable
{
	private String header;
	private String content;
	
	public LessonPlan(String header, String content)
	{
		this.header = header;
		this.content = content;
	}
	
	public String getHeader()
	{
		return header;
	}
	
	public String getContent()
	{
		return content;
	}
}
