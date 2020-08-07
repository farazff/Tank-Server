package MultiGame;

import GameData.User;

import java.io.Serializable;

/**
 * class which transfers data to server every frame per second
 */
public class TransferData implements Serializable
{
    private String command;
    private User user;

    /**
     * creates new Transfer data
     * @param command command
     * @param user user
     */
    public TransferData (String command, User user)
    {
        this.command = command;
        this.user = user;
    }

    /**
     *
     * @return user
     */
    public User getUser () {
        return user;
    }

    /**
     *
     * @return Command
     */
    public String getCommand () {
        return command;
    }
}
