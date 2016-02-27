package com.example.zoteroapp;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zoteroapp.model.Item;
import com.example.zoteroapp.model.ItemAdapter;
import com.example.zoteroapp.services.HttpRetriever;
import com.example.zoteroapp.services.ItemSeeker;
import com.example.zoteroapp.services.ServerCred;
import com.example.zoteroapp.tabs.FragmentTab;


public class m_screen extends ListActivity {
	Item item;
	String zapi_key;
	String url_zapi_key;

	String url_itemMember_zapi_key;
	ArrayList<String> member_zapi = new ArrayList<String>();

	public static String on_click_URL;
	public static String on_Single_item_click;

	ItemTask2 task2 = new ItemTask2();
	String s;
	HttpRetriever httpRetriever = new HttpRetriever();

	ArrayList<String> zapi=new ArrayList<String>();

	ArrayList<String> single_zapi=new ArrayList<String>();

	ServerCred<Item> itemseeker = new ItemSeeker();

	ArrayList<String> zapi_key_list = new ArrayList<String>();
	ArrayList<String> zapi_key_list2  =new ArrayList<String>();

	ItemAdapter item_adapter;
	ItemAdapter item_adapter2;

	String url = null;

	ListView l;

	TextView tv_title ,tv_url,tv_author,tv_abstract;

	FragmentTab fragmentTab = null; 
	FragmentTransaction ft = null; 



	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.m_screen);

		//tv_title = (TextView) findViewById(R.id.tv_title);
		//tv_url = (TextView)findViewById(R.id.tv_url);

		ArrayList<Item> item_list = new ArrayList<Item>();


		item_adapter = new ItemAdapter(this, android.R.layout.simple_list_item_2, item_list);

		ListView list1;
		list1 = (ListView)findViewById(R.id.list1);

		item_list = (ArrayList<Item>) getIntent().getSerializableExtra("items");

		zapi_key_list=(ArrayList<String>)getIntent().getStringArrayListExtra("zapi_keys");


		if(zapi_key_list!=null){
			for(int i=0;i<zapi_key_list.size();i++){
				continue;
			}
			try{
				Log.d(getClass().getSimpleName(),"stoixia tis zapi_key_list"+zapi_key_list);
			}catch(NullPointerException e){
				e.printStackTrace();
			}
		}else{
			try{
				Log.d("zapi_key_list einai adeia ","teleiws");
			}catch (NullPointerException n){
				n.printStackTrace();
			}
		}

		list1.setAdapter(item_adapter);

		list1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				MainZoteroActivity.flag=3;
				
				//disappear the fragment
				if(fragmentTab!=null && ft!=null){
					fragmentTab.getActivity().getFragmentManager().beginTransaction().remove(fragmentTab).commit();
				}

				Log.d(getClass().getSimpleName(),"The posotion "+position);

				url_zapi_key =zapi_key_list.get(position);

				Log.d(getClass().getSimpleName(),"url_ zapi key "+url_zapi_key);

				String coll_url = "https://api.zotero.org/users/1737513/collections/";
				String key_url = "/items?key=hIpXYNj8Ct1e7JQznLTKlxLL";	
				//on_click_URL = "https://api.zotero.org/users/1737513/collections/G6SQ8JFT/items?key=hIpXYNj8Ct1e7JQznLTKlxLL";
				on_click_URL= coll_url+url_zapi_key+key_url;

				Log.d(getClass().getSimpleName(), "the url for member items: "+on_click_URL+" and the flag"+MainZoteroActivity.flag);

				new ItemTask2().execute();
				//task2.execute();
			}
		});


		if(item_list!=null && !item_list.isEmpty()){
			item_adapter.notifyDataSetChanged();
			item_adapter.clear();
			for (int i = 0; i < item_list.size(); i++) {
				item_adapter.add(item_list.get(i));
			}

		}
		item_adapter.notifyDataSetChanged();

	}// onCreate()


	public void onResume(){
		super.onResume();

	}//end onResume

	public void activeFragment(final ArrayList<Item> list ,final ArrayList<String> keys){

		ItemAdapter adapter = new ItemAdapter(this,android.R.layout.simple_list_item_2 , list);
		l = (ListView)findViewById(android.R.id.list);
		setListAdapter(adapter);
		l.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				MainZoteroActivity.flag=5;
				
				//set up the fragment
				fragmentTab = new FragmentTab();
				ft = getFragmentManager().beginTransaction();

				ft.replace(R.id.framelayout1, fragmentTab);
				ft.commit();

				String url_member= "https://api.zotero.org/users/1737513/items/";
				String key_member="?key=hIpXYNj8Ct1e7JQznLTKlxLL";

				url_itemMember_zapi_key = keys.get(position);


				on_Single_item_click = url_member+url_itemMember_zapi_key+key_member;
				Log.d(getClass().getCanonicalName(),on_Single_item_click);
				new ItemTask3().execute();

			}
		});
	}//end activeFregment

	class ItemTask2 extends AsyncTask<String,Void, ArrayList<Item>>{

		@Override
		protected ArrayList<Item> doInBackground(String... params) {
			Log.d(getClass().getName(), "doInbackground()");
			// TODO Auto-generated method stub
			String query = params.toString();

			ArrayList<Item> it_seeker = (ArrayList<Item>) itemseeker.find(query);
			return it_seeker;


		}//end doInbackground
		protected void  onPostExecute(final ArrayList<Item> result) {
			Log.d(getClass().getCanonicalName(),"on post execute");

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(MainZoteroActivity.flag==3){
						zapi.clear();
						for(Item i:result){
							s= i.zapi_key;
							Log.d(getClass().getSimpleName(),"Member item zapi key  "+s);
							zapi.add(s);

						}
						Log.d(getClass().getSimpleName(),"List member item zapi key  "+zapi);
						activeFragment(result, zapi);

					}
				}

			});
		}//end onPostexecute()
	}//end ItemTask2


	private class ItemTask3 extends AsyncTask<String , Void, ArrayList<Item>> {

		@Override
		protected ArrayList<Item> doInBackground(String... params) {
			// TODO Auto-generated method stub
			String query = params.toString();
			ArrayList<Item> seeker = (ArrayList<Item>) itemseeker.find2(query);
			return seeker;
		}
		protected void  onPostExecute(final ArrayList<Item> result) {
			Log.d(getClass().getCanonicalName(),"on post execute");

			runOnUiThread(new Runnable() {

				@SuppressLint("DefaultLocale")
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(MainZoteroActivity.flag==5){	
						try{
							for(Item item:result){
								tv_title = (TextView)findViewById(R.id.tv_title);
								tv_url = (TextView)findViewById(R.id.tv_url);
								tv_author = (TextView)findViewById(R.id.tv_author);
								tv_abstract = (TextView)findViewById(R.id.tv_abstract);

								//Log.d(getClass().getSimpleName(),"the zapi key einai: "+item.zapi_key.toString());

								tv_title.setText(String.valueOf(item.itemType.toString()));

								tv_url.setText(String.valueOf(item.url.toString()));
								tv_url.setTypeface(null, Typeface.ITALIC);
								tv_url.setTextColor(Color.RED);

								tv_author.setText(String.valueOf(item.author.toString()));
								tv_abstract.setText(String.valueOf(item.abstractNote.toString()));
								tv_abstract.setMovementMethod(new ScrollingMovementMethod());

								url = item.url.toString();
								tv_url.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
									}
								});
							}
						}
						catch(NullPointerException n){
							n.printStackTrace();

						}

					}
				}

			});
		}

	}//end ItemTask3
}//end m_screen





