package service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by ivan on 21.09.15.
 */
public class UserProfile {
    @NotNull
    private String username;
    @NotNull
    private String password;

    public UserProfile(@NotNull String name,@NotNull String pass) {
        this.username = name;
        this.password = pass;
    }
    public boolean checkPassword(@Nullable String pass){
        return pass != null && pass.equals(this.password);
    }

    @NotNull
    public String getUsername() {
        return username;
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

}
