package persistance;

import game.rooms.Room;
import game.user.GameProfile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Created by ivan on 21.09.15.
 */
@Entity
public class UserProfile {
    @NotNull
    @Column
    private final String username;
    @NotNull
    @Column
    private final String password;
    @Transient
    private final GameProfile gameProfile;
    @Transient
    private Room currentroom;
    @Transient
    private boolean isAuthorized;

    public UserProfile(@NotNull String name,@NotNull String pass) {
        this.username = name;
        this.password = pass;
        gameProfile = new GameProfile();
        currentroom = null;
    }
    public boolean checkPassword(@Nullable String pass){
        return pass != null && pass.equals(this.password);
    }

    @NotNull
    public String getUsername() {
        return username;
    }
    @Nullable
    public Room getCurrentroom() {
        return currentroom;
    }

    public void setCurrentroom(@Nullable Room currentroom) {
        this.currentroom = currentroom;
    }

    public JSONObject getJson(){
        JSONObject object = gameProfile.getJSON();
        object.put("name",username);
        return object;
    }
    @NotNull
    public GameProfile getGameProfile(){
        return gameProfile;
    }


    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setIsAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }
}
