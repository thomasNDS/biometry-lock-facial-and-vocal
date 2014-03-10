package com.example.biometrie;
public class User implements java.io.Serializable{
	
	public static String emptyField = "none";
	
	private String firstName;
	private String surname;
	private String mail;
	private String picture;
	private String voice;

	public String GetFirstName()
	{
		return firstName;
	}
	public void SetFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public String GetSurname()
	{
		return surname;
	}
	public void SetSurname(String surname)
	{
		this.surname = surname;
	}
	public String GetMail()
	{
		return mail;
	}
	public void SetMail(String mail)
	{
		this.mail = mail;
	}
	public String GetPicture()
	{
		return picture;
	}
	public void SetPicture(String picture)
	{
		this.picture = picture;
	}
	public String GetVoice()
	{
		return voice;
	}
	public void SetVoice(String voice)
	{
		this.voice = voice;
	}

	public User()
	{
		this.firstName = "Toto";
		this.surname = "Durand";
		this.mail = "toto.durand@mail.com";
		this.picture = User.emptyField;
		this.voice = User.emptyField;
	}

	public User(String firstName, String surname, String mail, String picture, String voice)
	{
		this.firstName = firstName;
		this.surname = surname;
		this.mail = mail;
		this.picture = picture;
		this.voice = voice;
	}

	public String ToString()
	{
		return GetFirstName() + " " + GetSurname() + ", " + GetMail() + ", " + GetPicture() + ", " + GetVoice();
	}
}
