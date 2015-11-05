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
import java.util.stream.Collectors;

/**
 * Created by ivan on 02.10.15.
 */
public class RoomFFA extends RoomAbstractImpl {

    private final List<UserProfile> users = new ArrayList<>();
    public boolean isFull(){
        return users.size() == getPlayersLimit();
    }
    public RoomFFA(String roomName) {
        super(roomName);
    }

    public RoomFFA(String roomName, String roomPassword) {
        super(roomName, roomPassword);
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
        return users.stream().map(UserProfile::getGameProfile).collect(Collectors.toList());
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
        object.put("name",getRoomName());
        object.put("players",getPlayersCount());
        object.put("maxPlayers",getPlayersLimit());
        return object;
    }

    @Override
    public JSONArray getJsonRoomPlayers() {

        JSONArray array = new JSONArray();
        users.stream().filter(profile -> !profile.getGameProfile().isKilled()).forEach(profile -> array.put(profile.getJson()));
        return array;
    }

    @Override
    public int getPlayersCount() {return users.size();}

}
