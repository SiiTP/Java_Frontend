package game.rooms;

import exceptions.NoSuchPlayerException;
import exceptions.RoomFullException;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import service.GameProfile;
import service.UserProfile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ivan on 02.10.15.
 */
public class RoomFFA extends RoomAbstractImpl {

    private List<UserProfile> users = new ArrayList<>();//TODO лучше сделать связь от профиля к new профилю игровому
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
    public void kickPlayer(UserProfile profile) throws NoSuchPlayerException{
        if(!users.contains(profile)){
            throw new NoSuchPlayerException(getRoomName());
        }
        users.remove(profile);
    }

    @Override
    public boolean isRoomReady() {
        boolean isReady =  users.size()>1;
        if(isReady && getStartTime() == null) {
            Instant startTime = Instant.now();
            final int minutes = 1;
            final int seconds = 60;
            final int maxRoomTime = minutes * seconds; //TODO add to prop
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
        boolean fin =  Instant.now().isAfter(getFinishTime()) || maxScore()==getScoreLimit();
        if(fin){
            System.out.println("here we go");
        }
        return fin;
    }


    @Nullable
    public String getWinner(){
        int max = maxScore();
        String winnerName = null;
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
            winnerName = winner.getUsername();
        return winnerName;
    }
    @Override
    public boolean checkUser(UserProfile user){
        return users.contains(user);
    }
    @Override
    public void addUser(UserProfile profile) throws RoomFullException {
        if(isFull()){
            throw new RoomFullException(getPlayersLimit());
        }
        users.add(profile);
    }
    @Override
    public JSONObject getJsonRoom(){
        JSONObject object = new JSONObject();
        object.put("roomID",1);//TODO еще нет БД для ID
        object.put("roomname",getRoomName());
        object.put("players",getPlayersCount());
        object.put("maxPlayers",getPlayersLimit());
        return object;
    }

    @Override
    public JSONArray getJsonRoomPlayers() {

        JSONArray array = new JSONArray();
        for(UserProfile profile : users){
            array.put(profile.getJson());
        }
        return array;
    }

    @Override
    public int getPlayersCount() {return users.size();}

    @Nullable
    public List<UserProfile> getPlayers() {
        if(!isFinished()) {
            return users;
        }else {
            return null;
        }
    }

}
