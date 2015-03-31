package com.android.database;

public class ClientDetails {
    
   //private variables
   int id;
   String mac_address;
    
   // Empty constructor
   public ClientDetails(){
        
   }
   // constructor
   public ClientDetails(int id, String macAddress){
       this.id = id;
       this.mac_address = macAddress;
   }
    
   // constructor
   public ClientDetails(String macAddress){
       this.mac_address = macAddress;
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
   public String getMacAddress(){
       return this.mac_address;
   }
    
   // setting name
   public void setMacAddress(String macAddress){
       this.mac_address = macAddress;
   }
}
