package com.example.zoteroapp.model;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.zoteroapp.R;
import com.example.zoteroapp.services.HttpRetriever;

public class ItemAdapter extends ArrayAdapter<Item>{
	HttpRetriever httpRetriever = new HttpRetriever();
	List<Item> itemList = new ArrayList<Item>();
	 Activity context;
	

	public ItemAdapter(Activity context, int resource, List<Item> items) {
		super(context, resource, items);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.itemList = items;
	}

	

	public View getView(int position , View convert , ViewGroup group){
		View view = convert;
		if(view==null) {
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = li.inflate(R.layout.lista_item, null);
		}
		Item item = itemList.get(position);

		if(item!=null){
			TextView tv_name = (TextView)view.findViewById(R.id.tv_name);
			tv_name.setText(item.title);

			TextView tv_date = (TextView)view.findViewById(R.id.tv_date);
			tv_date.setText(String.valueOf(item.published));
			tv_date.setBackgroundColor(Color.GRAY);

		}
		
		return view;
	}

}
