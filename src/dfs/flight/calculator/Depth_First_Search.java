/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dfs.flight.calculator;

/**
 *
 * @author Radu Ciprian Rotaru
 */

import java.util.*;
import java.io.*;

//Flight information
class FlightInfo{
    String from;
    String to;
    int distance;
    
    boolean skip; //used in backtracking
    FlightInfo(String f, String t, int d){
        from = f;
        to = t;
        distance =d;
        skip = false;
    }
}

public class Depth_First_Search{
    
    final int MAX =100; //maximum number of connections
    
    //This array holds the flight information.
    FlightInfo flights[]=new FlightInfo[MAX];
    
    int numFlights = 0; //number of entries in flight array
    
    Stack btStack = new Stack(); //baktrack stack
    
    public static void main(String args[]){
    String to, from;
    Depth_First_Search ob = new Depth_First_Search();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    ob.setup();
    
    try{
        System.out.print("From? ");
        from =br.readLine();
        System.out.print("To?");
        to = br.readLine();
        
        ob.isflight(from, to);
        
        if(ob.btStack.size()!=0)
            ob.route(to);
    } catch(IOException exc){
        System.out.println("Error on input.");
    }
   
}
    void setup(){
        addFlight("Washington", "London", 3716);
        addFlight("London","Paris",235);
        addFlight("Washington","Houston",1223);
        addFlight("Washington", "Paris",3892);
        addFlight("Houston","Tokio",6744);
        addFlight("Houston", "Sydney",8551);
        addFlight("Paris","Frankfurt",318);
        addFlight("Paris","Rome",694);
        addFlight("Rome","Sydney",10115);
        addFlight("Sydney","Paris",10519);
    }
    
    //put flights into the database
    void addFlight(String from, String to, int dist){
        if(numFlights<MAX){
            flights[numFlights]=new FlightInfo(from, to, dist);
                numFlights++;
        }
        else
            System.out.println("Flight databse full.\n");
    }
    
    //Show the route and total distance
    void route(String to){
        Stack rev = new Stack();
        int dist = 0;
        FlightInfo f;
        int num = btStack.size();
        
        //Reverse the stack to display route.
        for(int i =0; i < num;i++)
            rev.push(btStack.pop());
        
        for (int i = 0; i < num;i++){
            f = (FlightInfo) rev.pop();
            System.out.print(f.from + " to ");
            dist += f.distance;
        }
        System.out.println(to);
        System.out.println("Distance is " + dist);
        
    }
    
    /*
    *if there is a flight between from and to, return the distance of flight;
    *otherwise, return 0.
    */
    
    int match(String from, String to){
        for(int i = numFlights - 1; i >-1;i--){
         if(flights[i].from.equals(from)&&flights[i].to.equals(to)&&!flights[i].skip){
             flights[i].skip=true;//prevent reuse
             return flights[i].distance;
         }   
        }
    return 0;//not found
    }
    
    //Given from, find any connection
    FlightInfo find(String from){
        for(int i=0;i<numFlights;i++){
            if(flights[i].from.equals(from)&&!flights[i].skip){
                FlightInfo f=new FlightInfo(flights[i].from, flights[i].to, flights[i].distance);
                flights[i].skip=true;//prevent reuse
            return f;
            }
        }
        return null;
    }
    
    //Determine if there is a route between from and to.
    void isflight(String from, String to){
        int dist;
        FlightInfo f;
        
        //See if at destination
        dist=match(from,to);
        if(dist!=0){
            btStack.push(new FlightInfo(from,to,dist));
            return;
        }
        
        //Try another connection.
        f=find(from);
        if(f!=null){
            btStack.push(new FlightInfo(from,to,f.distance));
            isflight(f.to,to);
        } 
        else if (btStack.size()>0){
            //backtrack and try another connection.
            f = (FlightInfo) btStack.pop();
            isflight(f.from,f.to);
        }
    }
   
}

