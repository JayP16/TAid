package UserInformation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public abstract class User implements Serializable
{
	protected String username;
	protected String password;
    protected int userType;
    protected String userDir;
	protected String address = Globals.ipAddress;
	protected int port = Globals.port;

	public User(String username, String password, int userType)
	{
		this.username = username;
		this.password = password;
		this.userType = userType;
        this.setUserDir();
	}

	public User(){}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

    public int getUserType()
    {
        return this.userType;
    }

    public String getUserDir()
    {
        return this.userDir;
    }

    public void setUserDir()
    {
        if (this.userType == 1)
        {
            this.userDir = "Prof/" + this.username;
        }
        else{
            this.userDir = "TA/" + this.username;
        }
    }
    
    public Professor reset()
    {
    	Socket clientSocket;
    	Professor prof = null;
		try 
		{
			clientSocket = new Socket(address, port);
			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream printwriter = new ObjectOutputStream(clientSocket.getOutputStream());
			//write the credentials to the server for verification
			printwriter.writeObject("login");
			printwriter.writeObject(this.getUsername());
			printwriter.writeObject(this.getPassword());
			
			String result = (String)in.readObject();
			
			prof = (Professor)in.readObject();
				
			clientSocket.close();
			in.close();
			printwriter.close();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return prof;
    }

	protected void changePassword(String newPassword)
	{
		password = newPassword;
	}
}
