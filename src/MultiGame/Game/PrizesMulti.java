package MultiGame.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class PrizesMulti implements Serializable
{
    private ArrayList<PrizeMulti> prizes;  ////ok to serialize
    private ArrayList<TankMulti> tanks;////ok to serialize
    private MapsMulti maps;  ////ok to serialize

    public PrizesMulti(MapsMulti maps , ArrayList<TankMulti> tanks)
    {
        prizes = new ArrayList<>();
        this.maps = maps;
        this.tanks = tanks;
    }

    public void putPrize()
    {


        Random random = new Random();
        int r = random.nextInt(5) + 1;
        int x, y;

        do
        {
            x = random.nextInt(1240) + 20;
            y = random.nextInt(650) + 60;
        } while(!isEmpty(x, y));

        prizes.add(new PrizeMulti(r, x, y));

    }

    public boolean isEmpty(int x ,int y)
    {
        for(int i=0;i<tanks.size();i++)
        {
            if( ((x-tanks.get(i).getLocX())*(x-tanks.get(i).getLocX()) +
                    (y-tanks.get(i).getLocY())*(y-tanks.get(i).getLocY())) < (50)*(50) )
            {
                return false;
            }
        }

        for(int i=0;i<maps.getWalls().size();i++)
        {
            int wallX = maps.getWalls().get(i).getX();
            int wallY = maps.getWalls().get(i).getY();
            int wallL = maps.getWalls().get(i).getLength();

            if(maps.getWalls().get(i).getType().equals("H"))
            {
                if(x>=wallX-50 && x<= (wallX + wallL + 50) )
                {
                    if( (y-wallY<=80 && y-wallY>=0) || (wallY-y<=80 && wallY-y>=0) )
                    {
                        return false;
                    }
                }
            }
            if(maps.getWalls().get(i).getType().equals("V"))
            {
                if(y>=wallY-50 && y<= (wallY + wallL + 50) )
                {
                    if((x-wallX<=80 && x-wallX>=0) || (wallX-x<=80 && wallX-x>=0))
                    {
                        return false;
                    }
                }
            }
        }

        for(int i= 0 ; i<prizes.size();i++)
        {
            if( ( (x-prizes.get(i).getX())*(x-prizes.get(i).getX()) +
                    (y-prizes.get(i).getY())*(y-prizes.get(i).getY()) ) <= 39*39 )
            {
                return false;
            }
        }

        return true;
    }

    public ArrayList<PrizeMulti> getPrizes()
    {
        return prizes;
    }

}
