package GameData;

import java.io.Serializable;
import java.util.ArrayList;

public class MultiGame implements Serializable
{
    private final String name;
    private final GameFinishType gameFinishType;
    private final GameMemberShipType gameMemberShipType;
    private final int numberOfPlayers;
    private final int tankStamina;
    private final int wallStamina;
    private final int canonPower;
    private int onlineUsersNumber;
    private ArrayList<User> onlineUsers;

    public MultiGame (String name, GameFinishType gameFinishType,
                      GameMemberShipType gameMemberShipType, int numberOfPlayers,
                      int tankStamina, int wallStamina, int canonPower)
    {
        this.name = name;
        this.canonPower = canonPower;
        this.gameFinishType = gameFinishType;
        this.gameMemberShipType = gameMemberShipType;
        this.tankStamina = tankStamina;
        this.wallStamina = wallStamina;
        this.numberOfPlayers = numberOfPlayers;
        this.onlineUsersNumber = 0;
        onlineUsers = new ArrayList<> ();
    }

    public String getName () {
        return name;
    }

    public void addUser (User user)
    {
        onlineUsers.add (user);
        onlineUsersNumber++;
    }

    public ArrayList<User> getOnlineUsers () {
        return onlineUsers;
    }

    public int getOnlineUsersNumber () {
        return onlineUsersNumber;
    }

    public GameFinishType getGameFinishType () {
        return gameFinishType;
    }

    public GameMemberShipType getGameMemberShipType () {
        return gameMemberShipType;
    }

    public int getCanonPower () {
        return canonPower;
    }

    public int getNumberOfPlayers () {
        return numberOfPlayers;
    }

    public int getTankStamina () {
        return tankStamina;
    }

    public int getWallStamina () {
        return wallStamina;
    }
}
