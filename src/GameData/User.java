package GameData;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * this class represents User in game
 */
public class User implements Serializable
{
    private int tankCode;
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

    /**
     * creates a new User
     * @param userName userName
     * @param password password
     * @param serverInformationStorage serverInformationStorage
     */
    public User (String userName, char[] password, ServerInformationStorage serverInformationStorage)
    {
        tankCode = 4;
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

    /**
     *
     * @return username
     */
    public String getUserName () {
        return userName;
    }

    /**
     *
     * @return password
     */
    public char[] getPassword () {
        return password;
    }

    /**
     * set DefaultWallStamina
     * @param defaultWallStamina defaultWallStamina
     */
    public void setDefaultWallStamina (int defaultWallStamina) {
        this.defaultWallStamina = defaultWallStamina;
    }

    /**
     * set DefaultCanonPower
     * @param defaultCanonPower defaultCanonPower
     */
    public void setDefaultCanonPower (int defaultCanonPower) {
        this.defaultCanonPower = defaultCanonPower;
    }

    /**
     * set DefaultTankStamina
     * @param defaultTankStamina defaultTankStamina
     */
    public void setDefaultTankStamina (int defaultTankStamina) {
        this.defaultTankStamina = defaultTankStamina;
    }

    /**
     *
     * @return get DefaultCanonPower
     */
    public int getDefaultCanonPower () {
        return defaultCanonPower;
    }

    /**
     *
     * @return get DefaultTankStamina
     */
    public int getDefaultTankStamina () {
        return defaultTankStamina;
    }

    /**
     *
     * @return get DefaultWallStamina
     */
    public int getDefaultWallStamina () {
        return defaultWallStamina;
    }

    /**
     * set rank
     * @param rank rank
     */
    public void setRank (int rank) {
        this.rank = rank;
    }

    /**
     * set score
     * @param score score
     */
    public void setScore (int score) {
        this.score = score;
    }

    /**
     *
     * @return get SignedUpTime
     */
    public long getSignedUpTime () {
        return signedUpTime;
    }

    /**
     *
     * @return get Score
     */
    public int getScore () {
        return score;
    }

    /**
     *
     * @return get Rank
     */
    public int getRank () {
        return rank;
    }

    /**
     * set NumOfMultiGames
     * @param numOfMultiGames numOfMultiGames
     */
    public void setNumOfMultiGames (int numOfMultiGames) {
        this.numOfMultiGames = numOfMultiGames;
    }

    /**
     * set NumOfSingleGames
     * @param numOfSingleGames numOfSingleGames
     */
    public void setNumOfSingleGames (int numOfSingleGames) {
        this.numOfSingleGames = numOfSingleGames;
    }

    /**
     * set NumOfWinMultiGames
     * @param numOfWinMultiGames numOfWinMultiGames
     */
    public void setNumOfWinMultiGames (int numOfWinMultiGames) {
        this.numOfWinMultiGames = numOfWinMultiGames;
    }

    /**
     * set NumOfWinSingleGames
     * @param numOfWinSingleGames numOfWinSingleGames
     */
    public void setNumOfWinSingleGames (int numOfWinSingleGames) {
        this.numOfWinSingleGames = numOfWinSingleGames;
    }

    /**
     *
     * @return get NumOfMultiGames
     */
    public int getNumOfMultiGames () {
        return numOfMultiGames;
    }

    /**
     *
     * @return get NumOfSingleGames
     */
    public int getNumOfSingleGames () {
        return numOfSingleGames;
    }

    /**
     *
     * @return get NumOfWinMultiGames
     */
    public int getNumOfWinMultiGames () {
        return numOfWinMultiGames;
    }

    /**
     *
     * @return get NumOfWinSingleGames
     */
    public int getNumOfWinSingleGames () {
        return numOfWinSingleGames;
    }

    /**
     *
     * @return get ServerInformationStorage
     */
    public ServerInformationStorage getServerInformationStorage () {
        return serverInformationStorage;
    }
    /**
     *
     * @return tankCode field
     */
    public int getTankCode()
    {
        return tankCode;
    }

    /**
     *
     * @param tankCode the tank's code
     */
    public void setTankCode(int tankCode)
    {
        this.tankCode = tankCode;
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
