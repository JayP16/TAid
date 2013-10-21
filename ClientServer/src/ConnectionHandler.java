import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


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

	public boolean checkAccount(String user, String pass)
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
						return true;
					}
					br.close();
					return false;
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
		return false;
	}

	public void run()
	{
		System.out.println("Someone is trying to login...");
		BufferedReader in;
		try {
                    in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
                    PrintWriter out = new PrintWriter(soc.getOutputStream(), true);
                    String cmd = in.readLine();
                    if (cmd.equals("login"))
                    {
		        String username = in.readLine();
		        String password = in.readLine();
		        System.out.println("Username: " + username + "\nPassword: " + password);
		        if (checkAccount(username, password))
		        {
                            System.out.println("The user has logged in!!");
                            out.println("1");
		        }
		        else
		        {
                            System.out.println("Invalid login credentials...");
                            out.println("0");
		        }
		        soc.close();
		        in.close();
		        out.close();
                    }
                    else if (cmd.equals("register"))
                    {
                        String username = in.readLine();
                        String password = in.readLine();
                        String toAdd = username + " " + password;
                        System.out.println(toAdd);
                        System.out.println("Registering...");
                        registerUser(toAdd);
                        out.println("1");
                    }
                    else{
                        out.println("invalid");
                    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    private void registerUser(String toAdd) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Database/LoginDatabase/login.txt", true)));
            out.println(toAdd);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}