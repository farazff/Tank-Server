package MultiGame.Status;

import MultiGame.Game.*;

import java.io.Serializable;
import java.util.ArrayList;

public class GameStatus implements Serializable
{

    private ArrayList<String> winners;
    private boolean gameOver;

    private ArrayList<TankMulti> tanks;                 ////ok to serialize
    private InteractArrayListMulti<BulletMulti> bullets;     ////ok to serialize
    public int players;                  ////ok to serialize
    private MapsMulti maps;								 ////ok to serialize
    private PrizesMulti prizes;							 ////ok to serialize
    private boolean newPrize , usePrize , shot , explode;

    public GameStatus(ArrayList<TankMulti> tanks, InteractArrayListMulti<BulletMulti> bullets ,
                      MapsMulti maps, PrizesMulti prizes,int players)
    {
        winners = new ArrayList<>();
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

    public void update(ArrayList<TankMulti> tanks, InteractArrayListMulti<BulletMulti> bullets ,
                      MapsMulti maps, PrizesMulti prizes,int players)
    {
        this.tanks = tanks;
        this.bullets = bullets;
        this.maps = maps;
        this.prizes = prizes;
        this.players = players;
    }

    public ArrayList<TankMulti> getTanks() {
        return tanks;
    }

    public InteractArrayListMulti<BulletMulti> getBullets() {
        return bullets;
    }

    public MapsMulti getMaps() {
        return maps;
    }

    public PrizesMulti getPrizes() {
        return prizes;
    }

    public int getPlayers() {
        return players;
    }

    public void setNewPrize(boolean newPrize)
    {
        this.newPrize = newPrize;
    }

    public boolean isNewPrize()
    {
        return newPrize;
    }

    public void setUsePrize(boolean usePrize)
    {
        this.usePrize = usePrize;
    }

    public boolean isUsePrize()
    {
        return usePrize;
    }

    public void setShot(boolean shot)
    {
        this.shot = shot;
    }

    public boolean isShot()
    {
        return shot;
    }

    public void setExplode(boolean explode)
    {
        this.explode = explode;
    }

    public boolean isExplode()
    {
        return explode;
    }

    public boolean isGameOver()
    {
        return gameOver;
    }

    public void setGameOver(boolean gameOver)
    {
        this.gameOver = gameOver;
    }

    public ArrayList<String> getWinners()
    {
        return winners;
    }

    public void addWinners(String temp)
    {
        winners.add(temp);
    }

}

