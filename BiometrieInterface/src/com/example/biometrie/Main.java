package com.example.biometrie;
public class Main {

	public static void main(String[] args) {
		Database db = new Database();
		db.Clear();
		db.AddUser("toto", "durand", "toto.durand@mail.com", "none", "none");
		db.AddUser("titi", "martin", "titi.martin@mail.com", "none", "none");
		db.GetUsers();
	}

}
