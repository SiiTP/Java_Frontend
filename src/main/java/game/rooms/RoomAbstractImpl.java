package game.rooms;

import service.UserProfile;

import java.util.Date;

/**
 * Created by ivan on 14.10.15.
 */
public abstract class RoomAbstractImpl implements Room  {
    private String password;
    private String roomName;
    private UserProfile creatorUser;//1 -1 userprofile
    private Date createdDate;
    private Date finishedDate;
    private Integer playersLimit;

    public RoomAbstractImpl(String roomName, UserProfile creatorUser) {
        this.roomName = roomName;
        this.creatorUser = creatorUser;
        createdDate = new Date();
        playersLimit = 10;
    }
    public RoomAbstractImpl(String roomName, String roomPassword, UserProfile creator) {
        this.roomName = roomName;
        this.password = roomPassword;
        this.creatorUser = creator;
        createdDate = new Date();
        playersLimit = 10;
    }

    @Override
    public int getPlayersLimit() {
        return playersLimit;
    }

    @Override
    public void setPlayersLimit(Integer playersLimit) {
        this.playersLimit = playersLimit;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isRoomHasPass() {
        return password != null;
    }

    @Override
    public Date getCreateDate() {
        return createdDate;
    }

    @Override
    public UserProfile getCreator() {
        return creatorUser;
    }

    @Override
    public Date getFinishedDate() {
        return finishedDate;
    }
    @Override
    public String getRoomName() {
        return roomName;
    }
    @Override
    public void setRoomName(String roomName){
        this.roomName = roomName;
    }
}
