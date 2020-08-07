package MultiGame.Game;

import java.io.Serializable;

/**
 *  this class saves walls
 */
public class WallMulti implements Serializable
{
    private int x,y,length;  ////ok to serialize
    private String type;  ////ok to serialize
    private boolean destructible;  ////ok to serialize
    private boolean isOK;  ////ok to serialize
    private int health;  ////ok to serialize

    /**
     * the constructor of thw all
     * @param x the x of the left top of the wall
     * @param y the y of the left top of the wall
     * @param length the length of the wall
     * @param type the type of the wall
     * @param destructible bool of the walls destruction
     * @param health the health of the wall
     */
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

    /**
     * get x of the wall
     * @return x field
     */
    public int getX()
    {
        return x;
    }


    /**
     * get y of the wall
     * @return y field
     */
    public int getY()
    {
        return y;
    }

    /**
     * get the center  of the wall
     * @return the center X of the wall
     */
    public int getLength()
    {
        return length;
    }

    /**
     * get the center  of the wall
     * @return the center X of the wall
     */
    public int getCenterX ()
    {
        if (getType ().equals ("H"))
        {
            return getX () + (getLength () / 2);
        } else {
            return getX () + (getThick () / 2);
        }
    }

    /**
     * get the center Y of the wall
     * @return the center Y of the wall
     */
    public int getCenterY ()
    {
        if (getType ().equals ("H"))
        {
            return getY () + (getThick () / 2);
        } else {
            return getY () + (getLength () / 2);
        }
    }

    /**
     *
     * @return get Thick
     */
    public int getThick ()
    {
        return 10;
    }

    /**
     * get th type of the wall
     * @return type filed
     */
    public String getType()
    {
        return type;
    }

    /**
     * check if the wall is destructive
     * @return destructive field
     */
    public boolean isDestructible()
    {
        return destructible;
    }

    /**
     *
     * @return is wall OK ?
     */
    public boolean isOK()
    {
        return isOK;
    }

    /**
     * get the health of the wall
     * @return health of the wall
     */
    public int getHealth()
    {
        return health;
    }

    /**
     * decrease the health of the wall
     * @param count the count that the all looses
     */
    public void decreaseHealth(int count)
    {
        health -= count;
        if(health<=0)
            isOK = false;
    }
}
