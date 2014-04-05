package com.example.biometrie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

public class Database {
	
	FileOutputStream fOut = null; 
    OutputStreamWriter osw = null;

	private static String databasePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Documents/database.txt";
	
	
	public void AddUser(String firstName, String surname, String mail, String picture, String voice)
	{
		System.out.println(databasePath);
		try {
			 
			String content = "firstname=" + firstName + "; surname=" + surname + "; mail=" + mail + "; picture=" + picture + "; voice=" + voice + ";\n";
			
			File file = new File(databasePath);
 
			// if file doesnt exists, then create it
			if (!file.exists())
				file.createNewFile();
 
			//file.createNewFile();
			if(file.exists()){
				BufferedWriter sortie = new BufferedWriter(new FileWriter(databasePath, true));
				sortie.write(content);
				sortie.close();
			     
			     System.out.println("file created: "+file);
			}
 
			System.out.println("User added.");
 
		} catch (IOException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	public List<User> GetUsers()
	{
		List<User> users = new ArrayList<User>();
		BufferedReader br = null;

		try
		{
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader(databasePath));
 
			while ((sCurrentLine = br.readLine()) != null)
			{
				String[] fields = sCurrentLine.split(";");
				System.out.println("field0 " + fields[0]);
				String firstname = fields[0].split("=")[1];
				String surname = fields[1].split("=")[1];
				String mail = fields[2].split("=")[1];
				String picture = fields[3].split("=")[1];
				String voice = fields[4].split("=")[1];
				
				User user = new User(firstname, surname, mail, picture, voice);
				users.add(user);
			}
		}
		catch (IOException e) { e.printStackTrace(); }
		finally
		{
			try
			{
				if (br != null)
					br.close();
			}
			catch (IOException ex) { ex.printStackTrace(); }
		}
		
		return users;
	}
	
	public void Clear()
	{
		PrintWriter writer;
		try
		{
			writer = new PrintWriter(databasePath);
			writer.print("");
			writer.close();
		}
		catch (FileNotFoundException e) { e.printStackTrace(); }
	}
}
