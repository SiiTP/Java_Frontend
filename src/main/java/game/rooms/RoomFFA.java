package game.rooms;

import game.user.GameProfile;
import game.user.UserProfile;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import resource.GameResources;
import resource.ResourceFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ivan on 02.10.15.
 */
public class RoomFFA extends RoomAbstractImpl {

    private List<UserProfile> users = new ArrayList<>();
    @Override
    public boolean isFull(){
        return users.size() == getPlayersLimit();
    }
    public RoomFFA(String roomName, UserProfile creatorUser) {
        super(roomName,creatorUser);
    }

    public RoomFFA(String roomName, String roomPassword, UserProfile creator) {
        super(roomName, roomPassword, creator);
    }

    @Override
    public void kickPlayer(UserProfile profile){
        if(users.contains(profile)){
            profile.setCurrentroom(null);
            users.remove(profile);

        }
    }

    @Override
    public boolean isRoomReady() {
        boolean isReady =  users.size()>1;
        if(isReady && getStartTime() == null) {
            Instant startTime = Instant.now();
            GameResources gameResources =(GameResources) ResourceFactory.getResource("resources/data/game.json");
            final int maxRoomTime = gameResources.getMaxRoomPlayingTimeInSec();
            Instant finishTime = startTime.plusSeconds(maxRoomTime);
            setStartTime(startTime);
            setFinishTime(finishTime);
        }
        return isReady;
    }
    public int maxScore(){
        int max = -1;
        for(UserProfile user:users){
            GameProfile gameProfile = user.getGameProfile();
            if(gameProfile.getScore()>max){
                max = gameProfile.getScore();
            }
        }
        return max;
    }
    @Override
    public boolean isFinished() {
        return Instant.now().isAfter(getFinishTime()) || maxScore()==getScoreLimit();
    }

    public List<GameProfile> getGameProfiles(){
        List<GameProfile> gameProfiles = new ArrayList<>();
        for(UserProfile profile :users){
            gameProfiles.add(profile.getGameProfile());
        }
        return gameProfiles;
    }

    @Override
    @Nullable
    public String getWinner(){
        int max = maxScore();
        UserProfile winner = null;
            Iterator<UserProfile> iterator = users.iterator();
            while (winner == null) {
                if (iterator.hasNext()) {
                    UserProfile user = iterator.next();
                    if (user.getGameProfile().getScore() == max) {
                        winner = user;
                    }
                }
            }
        return winner.getUsername();
    }
    @Override
    public boolean checkUser(UserProfile user){
        return users.contains(user);
    }
    @Override
    public void addUser(UserProfile profile) {
        if(!isFull()){
            users.add(profile);
        }
    }
    @Override
    public JSONObject getJsonRoom(){
        JSONObject object = new JSONObject();
        //object.put("roomID",1);//TODO еще нет БД для ID
        object.put("name",getRoomName());
        object.put("players",getPlayersCount());
        object.put("maxPlayers",getPlayersLimit());
        return object;
    }

    @Override
    public JSONArray getJsonRoomPlayers() {

        JSONArray array = new JSONArray();
        for(UserProfile profile : users){
            if(!profile.getGameProfile().isKilled()) {
                array.put(profile.getJson());
            }
        }
        return array;
    }

    @Override
    public int getPlayersCount() {return users.size();}

}
