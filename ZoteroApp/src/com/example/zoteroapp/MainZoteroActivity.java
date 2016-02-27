package com.example.zoteroapp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.zoteroapp.model.Item;
import com.example.zoteroapp.services.ItemSeeker;
import com.example.zoteroapp.services.ServerCred;

public class MainZoteroActivity extends Activity implements OnClickListener {

	protected static final String TAG = "MainZoteroActivity";

	Button bttn_login;
	Button bttn_next;
	String q = null;
	ProgressDialog proDialog;
	public String query = null;
	public static int flag =0;
	Item item;
	ArrayList<String> zapi=new ArrayList<String>();
	String s;

	Bundle b;
	ServerCred<Item> itemseeker = new ItemSeeker();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_zotero);


		bttn_login = (Button)findViewById(R.id.next);
		bttn_next = (Button)findViewById(R.id.login);

		bttn_next.setOnClickListener(this); 
		bttn_login.setOnClickListener(this);


	}// END onCreate()


	public void onClick(View v){
		if(v.getId()==R.id.login){
			Log.d(TAG, "performItem");
			flag=1;
			performItem();
		}
		else if(v.getId()==R.id.next){
			flag=2;
			performItem();

		}

	}
	public void longToast(CharSequence message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
	public void performItem(){
		//ItemTask item_task = new ItemTask();
		new ItemTask().execute();
	}


	private class ItemTask extends AsyncTask<String, Void , ArrayList<Item>>{
		
		
		@Override
		protected ArrayList<Item> doInBackground(String... params) {
			Log.d(getClass().getName(), "doInbackground()");
			// TODO Auto-generated method stub
			String query = params.toString();
			ArrayList<Item> it_seeker = (ArrayList<Item>) itemseeker.find(query);
			return it_seeker;
		}

		protected void  onPostExecute(final ArrayList<Item> result) {
			Log.d(getClass().getCanonicalName(),"on post execute");
		
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(MainZoteroActivity.flag==1){

						Intent intent = new Intent(MainZoteroActivity.this , m_screen.class);
						intent.putExtra("items", result);

						for(Item i:result){
							s= i.zapi_key;
							Log.d(getClass().getSimpleName(),"zapi key  "+s);
							zapi.add(s);
						}
						Log.d("H lista me ta zapi key"+zapi, s);
						intent.putStringArrayListExtra("zapi_keys", zapi);
						startActivity(intent);
					}


				}

			});

		}

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_zotero, menu);
		return true;
	}

}
