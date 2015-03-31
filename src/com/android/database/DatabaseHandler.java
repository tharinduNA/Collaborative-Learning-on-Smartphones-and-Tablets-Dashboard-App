package com.android.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;
 
    // Database Name
    private static final String DATABASE_NAME = "testDatabase";
 
    // ClientDetails table name
    private static final String TABLE_CLIENTDETAILS = "clientdetails";
    private static final String TABLE_FEEDBACK = "feedback";
    private static final String TABLE_MESSAGES = "messages";
 
    // ClientDetails Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_MAC_ADDRESS = "mac_address";
    
    // Feedback Table Columns names
    private static final String KEY_CLIENT_ID = "clientdetails_id";
    private static final String KEY_LESSON_NAME = "lesson_name";
    private static final String KEY_TYPE = "type";
    
  //messages table column names
    private static final String KEY_MASSAGE = "message";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CLIENTDETAILS_TABLE = "CREATE TABLE " + TABLE_CLIENTDETAILS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MAC_ADDRESS + " TEXT" + ")";
        db.execSQL(CREATE_CLIENTDETAILS_TABLE);
        
        String CREATE_FEEDBACK_TABLE = "CREATE TABLE " + TABLE_FEEDBACK + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CLIENT_ID + " INTEGER," + KEY_LESSON_NAME + " TEXT,"
                + KEY_TYPE + " TEXT" + ")";
        db.execSQL(CREATE_FEEDBACK_TABLE);
        
        String CREATE_MESSAGES_TABLE = "CREATE TABLE " + TABLE_MESSAGES  + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MASSAGE + " TEXT," + KEY_MAC_ADDRESS + " TEXT" + ")";
        db.execSQL(CREATE_MESSAGES_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTDETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDBACK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations for CLIENTDETAILS table
     */
 
    // Adding new contact
    public void addClientDetails(ClientDetails clientDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_MAC_ADDRESS, clientDetails.getMacAddress()); // Contact Name
 
        // Inserting Row
        db.insert(TABLE_CLIENTDETAILS, null, values);
        db.close(); // Closing database connection
    }
 
    // Getting single contact for given id
    public ClientDetails getClientDetails(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_CLIENTDETAILS, new String[] { KEY_ID,
        		KEY_MAC_ADDRESS}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        ClientDetails clientDetails = new ClientDetails(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        // return contact
        return clientDetails;
    }
     
 // Getting single contact for given client's mac address
    public ClientDetails getClientDetailsForMacAddress(String macAddress) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_CLIENTDETAILS, new String[] { KEY_ID,
        		KEY_MAC_ADDRESS}, KEY_MAC_ADDRESS + "=?",
                new String[] { String.valueOf(macAddress) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        ClientDetails clientDetails = new ClientDetails(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        // return contact
        return clientDetails;
    }
    
    // Getting All Contacts
    public List<ClientDetails> getAllClientDetails() {
        List<ClientDetails> clientDetailsList = new ArrayList<ClientDetails>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CLIENTDETAILS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ClientDetails clientDetails = new ClientDetails();
                clientDetails.setID(Integer.parseInt(cursor.getString(0)));
                clientDetails.setMacAddress(cursor.getString(1));
                // Adding contact to list
                clientDetailsList.add(clientDetails);
            } while (cursor.moveToNext());
        }
 
        // return client detail list
        return clientDetailsList;
    }
 
    // Updating single client detail
    public int updateClientDetails(ClientDetails clientDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_MAC_ADDRESS, clientDetails.getMacAddress());
 
        // updating row
        return db.update(TABLE_CLIENTDETAILS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(clientDetails.getID()) });
    }
 
    // Deleting single contact
    public void deleteClientDetail(ClientDetails clientDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLIENTDETAILS, KEY_ID + " = ?",
                new String[] { String.valueOf(clientDetails.getID()) });
        db.close();
    }
 
 
//    // Getting contacts Count
//    public int getContactsCount() {
//        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
// 
//        // return count
//        return cursor.getCount();
//    }
    
    /**
     * All CRUD(Create, Read, Update, Delete) Operations for FEEDBACK table
     */
 
    /**
     * adding feedback
     */
    public void addFeedback(int client_id, String lesson_name, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_CLIENT_ID, client_id);
        values.put(KEY_LESSON_NAME, lesson_name);
        values.put(KEY_TYPE, type);
 
        db.insert(TABLE_FEEDBACK, null, values);
        db.close();
    }
    
 // Getting All feedbacks for a given mac_id
    public List<Feedback> getAllFeedbacksForMacId(int client_id) {
        List<Feedback> feedbackList = new ArrayList<Feedback>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FEEDBACK + " WHERE " + KEY_CLIENT_ID + "=" + client_id;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Feedback feedback = new Feedback();
                feedback.setID(Integer.parseInt(cursor.getString(0)));
                feedback.setClientId(Integer.parseInt(cursor.getString(1)));
                feedback.setLessonName(cursor.getString(2));
                feedback.setType(cursor.getString(3));
                // Adding contact to list
                feedbackList.add(feedback);
            } while (cursor.moveToNext());
        }
 
        // return client detail list
        return feedbackList;
    }
    
 // Getting All feedbacks for a given mac_id and given leeson
    public List<Feedback> getAllFeedbacksForMacIdAndLesson(int client_id , String lessonName) {
        List<Feedback> feedbackList = new ArrayList<Feedback>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FEEDBACK + " WHERE " + KEY_CLIENT_ID + "=" + client_id + " AND " + KEY_LESSON_NAME + "='" + lessonName + "'" ;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Feedback feedback = new Feedback();
                feedback.setID(Integer.parseInt(cursor.getString(0)));
                feedback.setClientId(Integer.parseInt(cursor.getString(1)));
                feedback.setLessonName(cursor.getString(2));
                feedback.setType(cursor.getString(3));
                // Adding contact to list
                feedbackList.add(feedback);
            } while (cursor.moveToNext());
        }
 
        // return client detail list
        return feedbackList;
    }
    
 // Getting All feedbacks
    public List<Feedback> getAllFeedbacks() {
        List<Feedback> feedbackList = new ArrayList<Feedback>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FEEDBACK;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Feedback feedback = new Feedback();
                feedback.setID(Integer.parseInt(cursor.getString(0)));
                feedback.setClientId(Integer.parseInt(cursor.getString(1)));
                feedback.setLessonName(cursor.getString(2));
                feedback.setType(cursor.getString(3));
                // Adding contact to list
                feedbackList.add(feedback);
            } while (cursor.moveToNext());
        }
 
        // return client detail list
        return feedbackList;
    }
    
    //add a message
    public void addMessage(Messages messages) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_MAC_ADDRESS, messages.getMacAddress()); // Contact Name
    	values.put(KEY_MASSAGE, messages.getMessage());
    	
//    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//    	Date date = new Date();
//    	values.put(KEY_DATE, dateFormat.format(date));
    	//Inserting Row
    	db.insert(TABLE_MESSAGES, null, values);
    	db.close(); // Closing database connection
    }
    
    // Getting All messages
    public List<Messages> getAllMessages() {
    	List<Messages> messageList = new ArrayList<Messages>();
    	// Select All Query
    	String selectQuery = "SELECT  * FROM " + TABLE_MESSAGES;
	
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);

    	// looping through all rows and adding to list
    	if (cursor.moveToFirst()) {
    		do {
    			Messages messages = new Messages();
    			messages.setID(Integer.parseInt(cursor.getString(0)));
    			messages.setMessage(cursor.getString(1));
    			messages.setMacAddress(cursor.getString(2));

    			// Adding contact to list
    			messageList.add(messages);
    		} while (cursor.moveToNext());
    	}
	
  		// return client detail list
    	return messageList;
    }
}
