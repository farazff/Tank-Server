package MultiGame.Status;

import MultiGame.Game.*;

import java.io.Serializable;
import java.util.ArrayList;

public class GameStatus implements Serializable
{

    private ArrayList<TankMulti> tanks;                 ////ok to serialize
    private InteractArrayListMulti<BulletMulti> bullets;     ////ok to serialize
    public int players;                  ////ok to serialize
    private MapsMulti maps;								 ////ok to serialize
    private PrizesMulti prizes;							 ////ok to serialize

    public GameStatus(ArrayList<TankMulti> tanks, InteractArrayListMulti<BulletMulti> bullets ,
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
}

