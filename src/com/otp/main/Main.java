package com.otp.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.otp.actor.Actor;
import com.otp.data.DataParser;

public class Main {
	public static void main(String[] args) {
		readTest();
	}
	static void writeTest(){
		List<Actor> actors = new ArrayList<Actor>();
		Actor actor = new Actor();
		actor.setId(10);
		actor.setHappy(3);
		actor.setTeam(5);
		actor.setPref(new int[]{5,4,2,1,3});
		actors.add(actor);
		actor = new Actor();
		actor.setId(3);
		actor.setHappy(4);
		actor.setTeam(2);
		actor.setPref(new int[]{4,2,5,1,3});
		actors.add(actor);
		DataParser.write(".", actors);
	}
	static void readTest(){
		List<Actor> actors = DataParser.read(".");
		for(Actor actor:actors){
			System.out.println(actor.getId());
			System.out.println(actor.getHappy());
			System.out.println(actor.getTeam());
			String comma = "";
			System.out.print("[");
			for(int i:actor.getPref()){
				System.out.print(comma);
				System.out.print(i);
				comma = ",";
			}
			System.out.println("]");
		}
	}
}


