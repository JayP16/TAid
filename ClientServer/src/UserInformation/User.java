package UserInformation;

import java.io.Serializable;

public abstract class User implements Serializable
{
	protected String username;
	protected String password;
<<<<<<< HEAD
	
	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
=======
        protected int userType;
        protected String userDir;

	public User(String username, String password, int userType)
	{
		this.username = username;
		this.password = password;
		this.userType = userType;
                this.setUserDir();
	}

	public User(){}

>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
	public String getUsername()
	{
		return username;
	}
<<<<<<< HEAD
	
=======

>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
	public String getPassword()
	{
		return password;
	}
<<<<<<< HEAD
	
=======

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

>>>>>>> 9e90f157f78f0164db988294b51106f7ef04f5c7
	protected void changePassword(String newPassword)
	{
		password = newPassword;
	}
}
