package MultiGame.Game;

import java.io.Serializable;
/**
 * this class represents a single prize
 */

public class PrizeMulti implements Serializable
{
    private int x;  ////ok to serialize
    private int y;   ////ok to serialize
    private String type;   ////ok to serialize
    private String imgLoc;   ////ok to serialize
    private boolean active;  ////ok to serialize

    /**
     * comstructor of the Prize class
     * @param rand type of the prize
     * @param x the x coordinate of the prize
     * @param y the y coordinate of the prize
     */
    public PrizeMulti(int rand , int x ,int y)
    {
        this.x = x;
        this.y = y;
        active = true;
        if(rand==1)
        {
            type = "Protect";
            imgLoc = ("Images/Prizes/Protect.png");
        }
        if(rand==2)
        {
            type = "Laser";
            imgLoc = ("Images/Prizes/Laser.png");
        }
        if(rand==3)
        {
            type = "Health";
            imgLoc = ("Images/Prizes/Health.png");
        }
        if(rand==4)
        {
            type = "Power2";
            imgLoc = ("Images/Prizes/Power2.png");
        }
        if(rand==5)
        {
            type = "Power3";
            imgLoc =("Images/Prizes/Power3.png");
        }

    }

    /**
     * de active the prize
     */
    public void deActive()
    {
        active = false;
    }

    /**
     * get active
     * @return active field
     */
    public boolean isActive()
    {
        return active;
    }


    /**
     * get the type of the prize
     * @return type field
     */
    public String getType()
    {
        return type;
    }


    /**
     * image of the prize
     * @return the img field
     */
    public String getImgLoc()
    {
        return imgLoc;
    }

    /**
     * get the x coordinate of the prize
     * @return x field
     */
    public int getX()
    {
        return x;
    }

    /**
     * get the x coordinate of the prize
     * @return x field
     */
    public int getY()
    {
        return y;
    }
}
