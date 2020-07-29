package Game;

public class Wall
{
    private int x,y,length;
    private String type;
    private boolean destructible;
    private boolean isOK;
    private int health;

    public Wall(int x,int y,int length,String type,boolean destructible,int health)
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
