package MultiGame.Status;

import MultiGame.Game.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this classs saves the data of the multiPlayer game
 */

public class GameStatus implements Serializable
{

    private ArrayList<String> winners;
    private boolean gameOver,gameOverAll;
    private int[] kills;
    private ArrayList<TankMulti> tanks;                 ////ok to serialize
    private InteractArrayListMulti<BulletMulti> bullets;     ////ok to serialize
    public int players;                  ////ok to serialize
    private MapsMulti maps;								 ////ok to serialize
    private PrizesMulti prizes;							 ////ok to serialize
    private boolean newPrize , usePrize , shot , explode;
    private ArrayList<String> names;

    /**
     *
     * @param tanks tanks
     * @param bullets bullets
     * @param maps maps
     * @param prizes prizes
     * @param players number of players
     */
    public GameStatus(ArrayList<TankMulti> tanks, InteractArrayListMulti<BulletMulti> bullets ,
                      MapsMulti maps, PrizesMulti prizes,int players)
    {
        names = new ArrayList<>();
        winners = new ArrayList<>();
        gameOverAll = false;
        gameOver = false;
        newPrize = false;
        usePrize = false;
        shot = false;
        explode = false;
        this.tanks = tanks;
        this.bullets = bullets;
        this.maps = maps;
        this.prizes = prizes;
        this.players = players;
    }

    /**
     * updates the status of the game
     * @param tanks tanks
     * @param bullets bullets
     * @param maps maps
     * @param prizes prizes
     * @param players players
     * @param kills kills
     * @param names names
     */
    public void update(ArrayList<TankMulti> tanks, InteractArrayListMulti<BulletMulti> bullets ,
                       MapsMulti maps, PrizesMulti prizes,int players,int[] kills,ArrayList<String> names)
    {
        this.names = names;
        this.kills = kills;
        this.tanks = tanks;
        this.bullets = bullets;
        this.maps = maps;
        this.prizes = prizes;
        this.players = players;
    }

    /**
     *
     * @return isGameOverAll
     */
    public boolean isGameOverAll()
    {
        return gameOverAll;
    }

    /**
     * setGameOverAll
     * @param gameOverAll gameOverAll
     */
    public void setGameOverAll(boolean gameOverAll)
    {
        this.gameOverAll = gameOverAll;
    }

    /**
     *
     * @return tanks field
     */
    public ArrayList<TankMulti> getTanks() {
        return tanks;
    }

    /**
     *
     * @return bullets field
     */
    public InteractArrayListMulti<BulletMulti> getBullets() {
        return bullets;
    }

    /**
     *
     * @return map filed
     */
    public MapsMulti getMaps() {
        return maps;
    }

    /**
     *
     * @return prizes field
     */
    public PrizesMulti getPrizes() {
        return prizes;
    }

    /**
     *
     * @return players field
     */
    public int getPlayers() {
        return players;
    }

    /**
     *
     * @param newPrize set new Prize
     */
    public void setNewPrize(boolean newPrize)
    {
        this.newPrize = newPrize;
    }

    /**
     *
     * @return new prize
     */
    public boolean isNewPrize()
    {
        return newPrize;
    }

    /**
     *
     * @param usePrize set use prize
     */
    public void setUsePrize(boolean usePrize)
    {
        this.usePrize = usePrize;
    }

    /**
     *
     * @return usePrize
     */
    public boolean isUsePrize()
    {
        return usePrize;
    }

    /**
     * set shot
     * @param shot shot
     */
    public void setShot(boolean shot)
    {
        this.shot = shot;
    }

    /**
     *
     * @return shot field
     */
    public boolean isShot()
    {
        return shot;
    }

    /**
     * e explode field
     * @param explode explode
     */
    public void setExplode(boolean explode)
    {
        this.explode = explode;
    }

    /**
     *
     * @return explode
     */
    public boolean isExplode()
    {
        return explode;
    }

    /**
     *
     * @return gameOVer
     */
    public boolean isGameOver()
    {
        return gameOver;
    }

    /**
     * set game over
     * @param gameOver gameOver
     */
    public void setGameOver(boolean gameOver)
    {
        this.gameOver = gameOver;
    }

    /**
     *
     * @return winners field
     */
    public ArrayList<String> getWinners()
    {
        return winners;
    }

    /**
     * add winners
     * @param temp temp
     */
    public void addWinners(String temp)
    {
        winners.add(temp);
    }

    /**
     *
     * @return kills
     */
    public int[] getKills()
    {
        return kills;
    }

    /**
     *
     * @return names
     */
    public ArrayList<String> getNames()
    {
        return names;
    }
}

