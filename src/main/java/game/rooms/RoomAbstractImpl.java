package game.rooms;

import game.user.UserProfile;
import resource.GameResources;
import resource.ResourceFactory;

import java.time.Instant;

/**
 * Created by ivan on 14.10.15.
 */
public abstract class RoomAbstractImpl implements Room  {
    private String password;
    private String roomName;
    private UserProfile creatorUser;
    private Instant startTime;
    private Instant finishTime;
    private Integer playersLimit;
    private Integer scoreLimit;
    public RoomAbstractImpl(String roomName, UserProfile creatorUser) {
        GameResources gameResources =(GameResources) ResourceFactory.getResource("resources/data/game.json");
        this.roomName = roomName;
        this.creatorUser = creatorUser;
        playersLimit = gameResources.getMaxPlayers();
        scoreLimit = gameResources.getDefaultWinScore();
    }
    public RoomAbstractImpl(String roomName, String roomPassword, UserProfile creator) {
        this(roomName,creator);
        this.password = roomPassword;
    }


    @Override
    public int getPlayersLimit() {
        return playersLimit;
    }


    public Instant getStartTime() {
        return startTime;
    }
    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isRoomHasPass() {
        return password != null;
    }
    public void setPlayersLimit(int playersLimit){
        this.playersLimit = playersLimit;
    }

    public Instant getFinishTime() {
        return finishTime;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Integer getScoreLimit() {
        return scoreLimit;
    }

    public void setFinishTime(Instant finishTime) {
        this.finishTime = finishTime;
    }

    public UserProfile getCreatorUser() {
        return creatorUser;
    }
}
