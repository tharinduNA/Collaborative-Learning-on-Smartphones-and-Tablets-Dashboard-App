package com.android.database;

public class Feedback {
    
	   //private variables
	   int id;
	   int client_id;
	   String lesson_name;
	   String type;
	    
	   // Empty constructor
	   public Feedback(){
	        
	   }
	   // constructor
	   public Feedback(int client_id, String lesson_name, String type){
	       this.client_id = client_id;
	       this.lesson_name = lesson_name;
	       this.type = type;
	   }
	    
	   // getting ID
	   public int getID(){
	       return this.id;
	   }
	    
	   // setting id
	   public void setID(int id){
	       this.id = id;
	   }
	    
	   // getting name
	   public int getClientId(){
	       return this.client_id;
	   }
	    
	   // setting name
	   public void setClientId(int client_id){
	       this.client_id = client_id;
	   }
	   
	   public String getLessonName(){
	       return this.lesson_name;
	   }
	    
	   // setting name
	   public void setLessonName(String lesson_name){
	       this.lesson_name = lesson_name;
	   }
	   
	   public String getType(){
	       return this.type;
	   }
	    
	   // setting name
	   public void setType(String type){
	       this.type = type;
	   }
	   
	   
	}
