package game.user;

import game.rooms.Room;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

/**
 * Created by ivan on 21.09.15.
 */
public class UserProfile {
    @NotNull
    private final String username;
    @NotNull
    private final String password;
    private final GameProfile gameProfile;
    private Room currentroom;
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

}
