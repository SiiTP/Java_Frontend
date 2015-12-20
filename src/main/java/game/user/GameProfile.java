package game.user;

import org.json.JSONObject;
import resource.GameResources;
import resource.ResourceFactory;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by ivan on 25.10.15.
 */
public class GameProfile {
    private int score;
    private double x;
    private double y;
    private double direction;
    private boolean isKilled;
    private AtomicBoolean isMoving;
    private Instant respawnTime;
    private long collisionTimeStamp;
    private Instant dt;
    public GameProfile() {
        isMoving = new AtomicBoolean(true);
        resetSetting();
    }

    public JSONObject getJSON(){
        JSONObject object = new JSONObject();
        object.put("posX",x);
        object.put("posY",y);
        object.put("score",score);
        object.put("isKilled",isKilled);
        object.put("isMoving", isMoving.get());
        object.put("direction",direction);
        return object;
    }

    public boolean isMoving() {
        return isMoving.get();
    }

    public void setIsMoving(boolean isStopped) {
        this.isMoving.set(isStopped);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public synchronized double getX() {
        return x;
    }

    public synchronized void setX(double x) {
        this.x = x;
    }

    public synchronized double getY() {
        return y;
    }

    public synchronized void setY(double y) {
        this.y = y;
    }

    public synchronized double getDirection() {
        return direction;
    }

    public synchronized void setDirection(double direction) {
        this.direction = direction;
    }

    public double getDeltaTime() {
        final int toSeconds = ((GameResources)ResourceFactory.getResource("data/game.json")).getToSeconds();
        if(dt==null){
            dt = Instant.now();
        }
        Instant temp = Instant.now();
        Instant diff = temp.minusMillis(dt.toEpochMilli());
        dt = temp;
        return (diff.toEpochMilli()+0.0)/toSeconds;
    }

    public synchronized boolean isKilled() {
        if(respawnTime != null){
            if(Instant.now().isAfter(respawnTime)){
                isKilled = false;
                respawnTime = null;
                resetSetting();
            }
        }
        return isKilled;
    }

    public synchronized void setIsKilled(boolean isKilled) {
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
    public void resetScore(){
        score = 0;
    }

    public long getCollisionTimeStamp() {
        return collisionTimeStamp;
    }

    public void setCollisionTimeStamp(long collisionTimeStamp) {
        this.collisionTimeStamp = collisionTimeStamp;
    }
}
