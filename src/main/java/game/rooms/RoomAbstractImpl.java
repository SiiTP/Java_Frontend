package game.rooms;

import service.UserProfile;

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
        this.roomName = roomName;
        this.creatorUser = creatorUser;
        playersLimit = 10;
        scoreLimit = 25;//todo add to prop
    }
    public RoomAbstractImpl(String roomName, String roomPassword, UserProfile creator) {
        this(roomName,creator);
        this.password = roomPassword;
    }


    @Override
    public int getPlayersLimit() {
        return playersLimit;
    }


    public void setPlayersLimit(Integer playersLimit) {
        this.playersLimit = playersLimit;
    }

    public Instant getStartTime() {
        return startTime;
    }
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
    public Instant getCreateDate() {
        return startTime;
    }


    public UserProfile getCreator() {
        return creatorUser;
    }
    public Instant getFinishTime() {
        return finishTime;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName){
        this.roomName = roomName;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Integer getScoreLimit() {
        return scoreLimit;
    }

    public void setScoreLimit(Integer scoreLimit) {
        this.scoreLimit = scoreLimit;
    }

    public void setFinishTime(Instant finishTime) {
        this.finishTime = finishTime;
    }
}
