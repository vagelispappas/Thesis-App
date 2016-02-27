package com.example.zoteroapp.services;

import java.util.List;

public abstract class ServerCred<E> {
	
	
	    
	protected HttpRetriever httpRetriever = new HttpRetriever();
	protected XMLResponseParser xmlParser = new XMLResponseParser();
	protected XMLSingleItemParser itemParser = new XMLSingleItemParser();
	
	public abstract List<E> find(String query);
	public abstract List<E> find2(String query);
				
	

}
