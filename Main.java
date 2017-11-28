public static void main(String[] args) {


}

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

public class Team {
  private Actor[] members;
  private int teamHappiness = 0;

  public Team(int size){
    Actor = new Actor[size];
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
      actiorIds.add(this.members[i].getId());
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
