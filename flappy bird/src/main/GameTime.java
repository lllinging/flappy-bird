package main;

public class GameTime {
  //attributes
  private long beginTime;
  private long endTime;
  private long differ;

  //get the current time
  public void begin() {
    this.beginTime = System.currentTimeMillis();
  }

  //calculate the time difference to get the game time of the player
  public long differ() {
    endTime = System.currentTimeMillis(); 
    differ = (endTime - beginTime)/1000;
    return differ;
}
}

