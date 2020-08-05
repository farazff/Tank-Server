package GameData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class represents a MultiGame in game
 */
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
    private int port;
    private boolean expired;

    /**
     * creates a new Game
     * @param name name of game
     * @param gameFinishType gameFinishType of game
     * @param gameMemberShipType gameMemberShipType if game
     * @param numberOfPlayers numberOfPlayers of game
     * @param tankStamina tankStamina
     * @param wallStamina wallStamina
     * @param canonPower canonPower
     */
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
        this.expired = false;
    }

    /**
     * set expired
     * @param expired expired
     */
    public void setExpired (boolean expired) {
        this.expired = expired;
    }

    /**
     *
     * @return isExpired
     */
    public boolean isExpired () {
        return expired;
    }

    /**
     * sets port for game
     * @param port port
     */
    public void setPort (int port) {
        this.port = port;
    }

    /**
     *
     * @return port of game
     */
    public int getPort () {
        return port;
    }

    /**
     *
     * @return name of game
     */
    public String getName () {
        return name;
    }

    /**
     * add User
     */
    public void addUser ()
    {
        onlineUsersNumber++;
    }

    /**
     *
     * @return number of online Users
     */
    public int getOnlineUsersNumber () {
        return onlineUsersNumber;
    }

    /**
     *
     * @return game finishType
     */
    public GameFinishType getGameFinishType () {
        return gameFinishType;
    }

    /**
     *
     * @return game memberShip type
     */
    public GameMemberShipType getGameMemberShipType () {
        return gameMemberShipType;
    }

    /**
     *
     * @return get canon power
     */
    public int getCanonPower () {
        return canonPower;
    }

    /**
     *
     * @return get number of players
     */
    public int getNumberOfPlayers () {
        return numberOfPlayers;
    }

    /**
     *
     * @return get tanks stamina
     */
    public int getTankStamina () {
        return tankStamina;
    }

    /**
     *
     * @return get Wall stamina
     */
    public int getWallStamina () {
        return wallStamina;
    }
}
