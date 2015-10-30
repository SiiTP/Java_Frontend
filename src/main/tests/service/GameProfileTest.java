package service;

import game.user.GameProfile;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ivan on 26.10.15.
 */
public class GameProfileTest {
    GameProfile profile ;
    @Before
    public void setup(){
        profile =new GameProfile();
        profile.setX(10);
        profile.setY(5);
        profile.setScore(3);
    }
    @Test
    public void testGetJSON() throws JSONException {
        JSONObject object = profile.getJSON();

        assertTrue(object.has("x"));
        assertTrue(object.has("y"));
        assertTrue(object.has("score"));
        assertTrue(object.getInt("x")==10);

        profile.setX(2);
        object = profile.getJSON();
        assertTrue(object.getInt("x")==2);



    }
}