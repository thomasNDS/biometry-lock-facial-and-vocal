package com.example.biometrie;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;











import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

public class LesUtilisateurs extends Activity {
	
	ListView listView;
    List<RowItem> rowItems;
    ImageView imageAfficher;
    Bitmap imgsrc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_les_utilisateurs);
		
		Database db = new Database();
		rowItems = new ArrayList<RowItem>();
		listView = (ListView)findViewById(R.id.listDesUtilsateurs);
		List<User> listUser = db.GetUsers();
		
		for (User u : listUser){
			System.out.println("premier utilisateur  " + u.ToString());
			if(u.GetPicture().equals(User.emptyField)){
				RowItem item = new RowItem(R.drawable.ic_launcher, u.GetFirstName() + " " + u.GetSurname(), u.GetMail());
				rowItems.add(item);
			}else{
				RowItem item = new RowItem(R.drawable.ic_launcher, u.GetFirstName() + " " + u.GetSurname(), u.GetMail());
				
				
				rowItems.add(item);
			}
		}
		CustomListViewAdapter adapter = new CustomListViewAdapter(this,R.layout.list_item, rowItems);
        listView.setAdapter(adapter);
        //listView.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.les_utilisateurs, menu);
		return true;
	}

}
