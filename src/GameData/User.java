package GameData;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class User implements Serializable
{
    private char[] password;
    private String userName;
    private long signedUpTime;
    private int score;
    private int rank;
    private int defaultTankStamina;
    private int defaultWallStamina;
    private int defaultCanonPower;
    private int numOfMultiGames;
    private int numOfSingleGames;
    private int numOfWinMultiGames;
    private int numOfWinSingleGames;
    private ServerInformationStorage serverInformationStorage;

    public User (String userName, char[] password, ServerInformationStorage serverInformationStorage)
    {
        this.password = password;
        this.userName = userName;
        signedUpTime = System.currentTimeMillis ();
        this.score = 0;
        rank = -1;
        defaultCanonPower = 10;
        defaultTankStamina = 10;
        defaultWallStamina = 10;
        numOfMultiGames = 0;
        numOfSingleGames = 0;
        numOfWinMultiGames = 0;
        numOfWinSingleGames = 0;
        this.serverInformationStorage = serverInformationStorage;
    }

    public String getUserName () {
        return userName;
    }

    public void setDefaultWallStamina (int defaultWallStamina) {
        this.defaultWallStamina = defaultWallStamina;
    }

    public void setDefaultCanonPower (int defaultCanonPower) {
        this.defaultCanonPower = defaultCanonPower;
    }

    public void setDefaultTankStamina (int defaultTankStamina) {
        this.defaultTankStamina = defaultTankStamina;
    }

    public int getDefaultCanonPower () {
        return defaultCanonPower;
    }

    public int getDefaultTankStamina () {
        return defaultTankStamina;
    }

    public int getDefaultWallStamina () {
        return defaultWallStamina;
    }

    public void setRank (int rank) {
        this.rank = rank;
    }

    public void setScore (int score) {
        this.score = score;
    }

    public long getSignedUpTime () {
        return signedUpTime;
    }

    public int getScore () {
        return score;
    }

    public int getRank () {
        return rank;
    }

    public void setNumOfMultiGames (int numOfMultiGames) {
        this.numOfMultiGames = numOfMultiGames;
    }

    public void setNumOfSingleGames (int numOfSingleGames) {
        this.numOfSingleGames = numOfSingleGames;
    }

    public void setNumOfWinMultiGames (int numOfWinMultiGames) {
        this.numOfWinMultiGames = numOfWinMultiGames;
    }

    public void setNumOfWinSingleGames (int numOfWinSingleGames) {
        this.numOfWinSingleGames = numOfWinSingleGames;
    }

    public int getNumOfMultiGames () {
        return numOfMultiGames;
    }

    public int getNumOfSingleGames () {
        return numOfSingleGames;
    }

    public int getNumOfWinMultiGames () {
        return numOfWinMultiGames;
    }

    public int getNumOfWinSingleGames () {
        return numOfWinSingleGames;
    }

    public ServerInformationStorage getServerInformationStorage () {
        return serverInformationStorage;
    }


    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Arrays.equals (password, user.password) &&
                Objects.equals (getUserName (), user.getUserName ());
    }

    @Override
    public int hashCode () {
        int result = Objects.hash (getUserName ());
        result = 31 * result + Arrays.hashCode (password);
        return result;
    }
}
