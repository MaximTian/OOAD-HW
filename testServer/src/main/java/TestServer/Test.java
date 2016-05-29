package src.main.java.TestServer;

import src.main.java.TestServer.datamodel.*;
import java.util.ArrayList;
import java.util.Iterator;
import com.google.gson.Gson;

public class Test {
    public void testLogin() {
        DataOperationPost dataOp = new DataOperationPost();
        dataOp.setUrl(dataOp.getBaseUrl() + "/user/login");
        String json1 = "{\"username\": \"1234\", \"cocopassword\": \"12345\"}";
        String json2 = "{\"username\": \"1234\", \"password\": \"12345\"}";
        String json3 = "{\"username\": \"tiankk\", \"password\": \"tiankk\"}";
        try {
        	dataOp.setJson(json1);
        	System.out.println(dataOp.Do());

        	dataOp.setJson(json2);
        	// System.out.println(dataOp.Do());

        	dataOp.setJson(json3);
        	System.out.println(dataOp.Do());
    	} catch (Exception e) {
    		System.out.println("Exception");
    	}
    }
    
    public void testRegister() {
        DataOperationPost dataOp = new DataOperationPost();
        dataOp.setUrl(dataOp.getBaseUrl() + "/user/register");
        String json3 = "{\"username\": \"tiankk\", \"password\": \"tiankk\"," +  
                " \"gender\": 1, \"phone\": \"18819473274\", \"tags\": \"action\"}";
        try {
        	dataOp.setJson(json3);
        	System.out.println(dataOp.Do());
    	} catch (Exception e) {
    		System.out.println("Exception");
    	}
    }
    
    public void testMovie() {
        DataOperationGet dataOp = new DataOperationGet();
	    dataOp.setUrl(dataOp.getBaseUrl() + "/movie/onView");
    	String response = "";
	    try {
            response = dataOp.Do();
	    } catch (Exception e) {
	        System.out.println("Exception");
	    }
	    // System.out.println(response);
	    MovieList list = new MovieList();
	    try {
	        list = new Gson().fromJson(response, MovieList.class);
	    } catch (Exception e) {
	        System.out.println("exception deserialize");
	    }
	    for (int i = 0; i < list.size(); i++)
	        System.out.println(list.get(i).getName()); // + "," + list.get(i).getIntro());
    }

}
