package com.otp.actor;


public class Actor {
  private int id;
  private int happiness = 0;
  private int[] pref;
  private int team;

  public Actor(){
  }

  public int getHappy(){
    return this.happiness;
  }
  public void setHappy(int x){
    this.happiness = x;
  }
  public int[] getPref(){
    return this.pref;
  }
  public void setPref(int[] x){
    this.pref = x;
  }
  public int getTeam(){
    return this.team;
  }
  public void setTeam(int x){
    this.team = x;
  }
  public int getId(){
    return this.id;
  }
  public void setId(int x){
    this.id = x;
  }
}