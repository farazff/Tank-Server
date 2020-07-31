package MultiGame.Game;

import java.io.Serializable;

public class WallMulti implements Serializable
{
    private int x,y,length;  ////ok to serialize
    private String type;  ////ok to serialize
    private boolean destructible;  ////ok to serialize
    private boolean isOK;  ////ok to serialize
    private int health;  ////ok to serialize

    public WallMulti(int x,int y,int length,String type,boolean destructible,int health)
    {
        this.x = x;
        this.y = y;
        this.length = length;
        this.type = type;
        this.destructible = destructible;
        isOK = true;
        this.health = health;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getLength()
    {
        return length;
    }

    public int getCenterX ()
    {
        if (getType ().equals ("H"))
        {
            return getX () + (getLength () / 2);
        } else {
            return getX () + (getThick () / 2);
        }
    }

    public int getCenterY ()
    {
        if (getType ().equals ("H"))
        {
            return getY () + (getThick () / 2);
        } else {
            return getY () + (getLength () / 2);
        }
    }
    public int getThick ()
    {
        return 10;
    }

    public String getType()
    {
        return type;
    }

    public boolean isDestructible()
    {
        return destructible;
    }

    public boolean isOK()
    {
        return isOK;
    }

    public int getHealth()
    {
        return health;
    }

    public void decreaseHealth(int count)
    {
        health -= count;
        if(health<=0)
            isOK = false;
    }
}
