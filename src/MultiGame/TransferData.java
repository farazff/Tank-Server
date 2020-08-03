package MultiGame;

import GameData.User;

import java.io.Serializable;

public class TransferData implements Serializable
{
    private String command;
    private User user;

    public TransferData (String command, User user)
    {
        this.command = command;
        this.user = user;
    }

    public User getUser () {
        return user;
    }

    public String getCommand () {
        return command;
    }
}
