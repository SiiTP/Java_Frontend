package game.user;

import org.json.JSONObject;
import resource.GameResources;
import resource.ResourceFactory;

import java.time.Instant;
import java.util.Random;

/**
 * Created by ivan on 25.10.15.
 */
public class GameProfile {
    private int score;
    private double x;
    private double y;
    private double direction;
    private boolean isKilled;
    private Instant respawnTime;
    public GameProfile() {
            resetSetting();
    }

    public JSONObject getJSON(){
        JSONObject object = new JSONObject();
        object.put("posX",x);
        object.put("posY",y);
        object.put("score",score);
        object.put("direction",direction);
        return object;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    /*public double getDeltaTime() {
        final int toSeconds = ((GameResources)ResourceFactory.getResource("data/game.json")).getToSeconds();
        if(dt==null){
            dt = Instant.now();
        }
        Instant temp = Instant.now();
        Instant diff = temp.minusMillis(dt.toEpochMilli());
        dt = Instant.now();

        return (diff.toEpochMilli()+0.0)/toSeconds;
    }*/

    public boolean isKilled() {
        if(respawnTime != null){
            if(Instant.now().isAfter(respawnTime)){
                isKilled = false;
                respawnTime = null;
                resetSetting();
            }
        }
        return isKilled;
    }

    public void setIsKilled(boolean isKilled) {
        this.isKilled = isKilled;
        this.respawnTime = Instant.now().plusSeconds(5);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof GameProfile) {
            GameProfile profile = (GameProfile) obj;
            return x == profile.x && y == profile.y && score == profile.score;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return Double.hashCode(x)+Double.hashCode(y);
    }
    public void resetSetting(){
        Random random = new Random(System.currentTimeMillis());
        GameResources gameResources =(GameResources) ResourceFactory.getResource("data/game.json");
        x = random.nextInt(gameResources.getGameFieldWidth());
        y = random.nextInt(gameResources.getGameFieldHeight());
        isKilled = false;
    }

}
