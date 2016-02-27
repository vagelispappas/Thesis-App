package com.example.zoteroapp.model;

import java.io.Serializable;


public class Item implements Serializable {
	/**
	 * 
	 */
	//this is semi-auto-generated serial version number
	private static final long serialVersionUID = 6256705452261647692L;
	
	
	public  String title;
	public  String published;
	public  String zapi_key;
	public  String itemType;
	public  String author;
	public  String abstractNote;
	public  String url;
	
	//default constructor
	public Item(){
		
	}
	
	public Item(String title , String published, String key){
		this.title = title;
		this.published = published;
		this.zapi_key = key;
		
	}
	public Item(String title , String published , String key , String itemType, String author , 
			String abstractNote,String url) {
		this.title = title;
		this.published = published;
		this.zapi_key=key;
		this.itemType=itemType;
		this.author = author;
		this.abstractNote = abstractNote;
		this.url = url;
		
	}
	
	

}
