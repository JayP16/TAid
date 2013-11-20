package UserInformation;

import java.io.Serializable;

public abstract class User implements Serializable
{
	protected String username;
	protected String password;
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

	protected void changePassword(String newPassword)
	{
		password = newPassword;
	}
}
