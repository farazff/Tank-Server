package MultiGame.Game;

import java.io.Serializable;

public class PrizeMulti implements Serializable
{
    private int x;  ////ok to serialize
    private int y;   ////ok to serialize
    private String type;   ////ok to serialize
    private String imgLoc;   ////ok to serialize
    private boolean active;  ////ok to serialize

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

    public void deActive()
    {
        active = false;
    }

    public boolean isActive()
    {
        return active;
    }

    public String getType()
    {
        return type;
    }

    public String getImgLoc()
    {
        return imgLoc;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
}
