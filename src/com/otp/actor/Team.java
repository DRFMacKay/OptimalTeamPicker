package com.otp.actor;

import java.util.HashSet;
import java.util.Set;

public class Team {
	private Actor[] members;
	private int teamHappiness = 0;

	public Team(int size){
		members = new Actor[size];
	}

	public Actor[] getMembers(){
		return this.members;
	}
	public void setMembers(int[] x){
		this.members = x;
	}
	public int getTeamHappiness(){
		return this.teamHappiness;
	}
	public void setTeamHappiness(int x){
		this.teamHappiness = x;
	}
	public void addMember(Actor x){
		this.members.add(x);
		this.updateHappiness();
	}
	public void removeMember(Actor x){
		this.members.remove(x);
		this.updateHappiness();
	}

	public void updateHappiness(){
		int totalHappiness = 0;
		int actorHappiness = 0;
		int[] actorPref;
		Set<Integer> actorIds = new HashSet<Integer>();
		for(int i = 0; i < members.length; i++){
			actorIds.add(this.members[i].getId());
		}
		for(int i = 0; i < members.length; i++){
			actorPref = this.members[i].getPref();
			for (int j = 0; j < actorPref.length ; j++) {
				if(actorIds.contains(actorPref[j])){
					actorHappiness = actorHappiness + (actorPref.length - j);
				}
			}
			this.members[i].setHappy(actorHappiness);
			totalHappiness = totalHappiness + actorHappiness;
			actorHappiness = 0;
		}
		this.setTeamHappiness(totalHappiness);
	}


	public Team[] createTeams(int size, int howMany){
		Team[] newTeams = new Team[howMany];

		for(int i = 0; i < howMany; i++){
			newTeams[i] = new Team(size);
		}
		return newTeams;
	}

}