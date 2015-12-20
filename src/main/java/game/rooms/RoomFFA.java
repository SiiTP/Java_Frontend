package game.rooms;

import game.user.GameProfile;
import persistance.UserProfile;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import resource.GameResources;
import resource.ResourceFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ivan on 02.10.15.
 */
public class RoomFFA extends RoomAbstractImpl {
    private final List<UserProfile> users = new ArrayList<>();
    private UserProfile winner;
    private boolean isFinished = false;
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
        return getStartTime() != null || users.size() > 1;
    }
    @SuppressWarnings("ConstantConditions")
    public UserProfile currentWinner(){
        if(winner== null || winner.getGameProfile().getScore() != getScoreLimit()) {
            int max = -1;
            UserProfile resultProfile = null;
            for (UserProfile user : users) {
                GameProfile gameProfile = user.getGameProfile();
                if (gameProfile.getScore() > max) {
                    max = gameProfile.getScore();
                    resultProfile = user;
                }
            }
            winner = resultProfile;
        }
        return winner;
    }
    @Override
    public boolean isFinished() {
        if(!isFinished && getFinishTime() != null) {
            UserProfile currentWinner = currentWinner();
            isFinished = currentWinner.getGameProfile().getScore() == getScoreLimit() || Instant.now().isAfter(getFinishTime());
        }
        return isFinished;
    }

    public List<GameProfile> getGameProfiles(){
        return users.stream().map(UserProfile::getGameProfile).collect(Collectors.toList());
    }

    @Override
    @Nullable
    public UserProfile getWinner(){
        return winner;
    }
    @Override
    public boolean checkUser(UserProfile user){
        return users.contains(user);
    }
    @Override
    public void addUser(UserProfile profile) {
        if(!isFull()){
            users.add(profile);
            if(isRoomReady() && getStartTime() == null){
                setGamePlayInterval();
            }
        }
    }
    private void setGamePlayInterval(){
            Instant startTime = Instant.now();
            GameResources gameResources =(GameResources) ResourceFactory.getResource("data/game.json");
            final int maxRoomTime = gameResources.getMaxRoomPlayingTimeInSec();
            Instant finishTime = startTime.plusSeconds(maxRoomTime);
            setStartTime(startTime);
            setFinishTime(finishTime);
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
        users.stream().forEach(profile -> array.put(profile.getJson()));
        return array;
    }

    @Override
    public int getPlayersCount() {return users.size();}

    public void setWinner(UserProfile winner) {
        this.winner = winner;
    }
}
