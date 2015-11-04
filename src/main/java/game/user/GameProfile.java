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
    private int x;
    private int y;
    private double direction;
    private Instant dt;
    private boolean isKilled;
    private Instant killTime;
    public GameProfile() {
        Random random = new Random(6);
        GameResources gameResources =(GameResources) ResourceFactory.getResource("resources/data/game.json");
        x = random.nextInt(gameResources.getGameFieldWidth());
        y = random.nextInt(gameResources.getGameFieldHeight());
        isKilled = false;
    }

    public JSONObject getJSON(){
        JSONObject object = new JSONObject();
        object.put("posX",x);
        object.put("posY",x);
        object.put("score",x);
        object.put("direction",direction);
        return object;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public double getDeltaTime() {
        final int toSeconds = ((GameResources)ResourceFactory.getResource("resources/data/game.json")).getToSeconds();
        if(dt==null){
            dt = Instant.now();
        }
        Instant temp = Instant.now();
        Instant diff = temp.minusMillis(dt.toEpochMilli());
        dt = Instant.now();

        return (diff.toEpochMilli()+0.0)/toSeconds;
    }

    public boolean isKilled() {
        return isKilled;
    }

    public void setIsKilled(boolean isKilled) {
        this.isKilled = isKilled;
    }

    @Override
    public boolean equals(Object obj) {
        GameProfile profile = (GameProfile)obj;
        return x == profile.x && y == profile.y && score == profile.score;
    }
}
