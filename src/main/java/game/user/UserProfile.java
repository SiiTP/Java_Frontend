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
    private String username;
    @NotNull
    private String password;
    private GameProfile gameProfile;
    private Room currentroom;
    public UserProfile(@NotNull String name,@NotNull String pass) {
        this.username = name;
        this.password = pass;
        gameProfile = new GameProfile();

    }
    public boolean checkPassword(@Nullable String pass){
        return pass != null && pass.equals(this.password);
    }

    @NotNull
    public String getUsername() {
        return username;
    }

    public Room getCurrentroom() {
        return currentroom;
    }

    public void setCurrentroom(Room currentroom) {
        this.currentroom = currentroom;
    }

    public void setUsername(@NotNull String name) {
        this.username = name;
    }

    @NotNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String pass) {
        this.password = pass;
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
