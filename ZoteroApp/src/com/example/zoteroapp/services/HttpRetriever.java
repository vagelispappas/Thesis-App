package com.example.zoteroapp.services;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/*This class is responsible for performing all Http requests,
 * also returns the responses */
public class HttpRetriever {
	public static int statusCode;
		String s ;
	//Default implementation of Http client interface
	DefaultHttpClient client = new DefaultHttpClient();
	
	//parameter is the target URL
	//execute the http get request
	//response in textual format
	public String retrieve(String url){
		Log.d(getClass().getName(),"execute the http get");
		//Represent a get request
		HttpGet getRequest  = new HttpGet(url);

		try{
			//execute the request and provide the server response
			HttpResponse getResponse = client.execute(getRequest);
			 statusCode = getResponse.getStatusLine().getStatusCode();

			if(statusCode!=HttpStatus.SC_OK){
				Log.w(getClass().getCanonicalName(),"error me to get"+statusCode+"for URL"+url);
				
				return null;
			}
            //HttpEntity, we have access to the data
			HttpEntity getResponseEntity = getResponse.getEntity();
			if(getResponseEntity!=null){
				//Textual response, we should convert entity (data) to a string 
				//return EntityUtils.toString(getResponseEntity);
				s =  EntityUtils.toString(getResponseEntity);
				Log.d(getClass().getCanonicalName(),"status code   "+statusCode);
				Log.d(getClass().getSimpleName(),s);
				return s;
			}else{
				Log.d(getClass().getSimpleName(), "eimai adeios");
			}


		}catch(IOException e){
			getRequest.abort();
			Log.w(getClass().getCanonicalName(), "error");
		}
		return null;
		
		//return null;
	}

	
}


