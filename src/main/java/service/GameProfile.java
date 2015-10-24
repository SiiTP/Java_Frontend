package service;

import org.json.JSONObject;

/**
 * Created by ivan on 25.10.15.
 */
public class GameProfile {
    private int score = 1;
    private int x = 100;
    private int y = 200;//TODO delete defaults

    public JSONObject getJSON(){
        JSONObject object = new JSONObject();
        object.put("x",x);
        object.put("y",x);
        object.put("score",x);
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

}
