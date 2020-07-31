package MultiGame.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class BulletMulti implements Runnable , Serializable

{
    private double x;  ////ok to serialize
    private static final int WALL_ACCURACY = 4; ////ok to serialize
    private static final int TANK_ACCURACY = -10; ////ok to serialize
    private long startTime;  ////ok to serialize
    private double y; ////ok to serialize
    private int height; ////ok to serialize
    private int width; ////ok to serialize
    private String m; ////ok to serialize
    private double degree; ////ok to serialize
    private DirectionMulti direction; ////ok to serialize
    private String imageLoc; ////ok to serialize
    private static final int STEP = 12;////ok to serialize
    private int canonPower;////ok to serialize
    private ArrayList<WallMulti> walls;////ok to serialize
    private ArrayList<TankMulti> tanks;
    private boolean expired; ////ok to serialize

    public BulletMulti (int x, int y, double degree, long startTime, ArrayList<WallMulti> walls,
                   ArrayList<TankMulti> tanks , int canonPower)
    {

        this.degree = degree;
        expired = false;
        findQuarterAndM (degree);
        this.x = x;
        this.y = y;
        this.canonPower = canonPower;
        this.startTime = startTime;
        this.walls = walls;
        this.tanks = tanks;
        imageLoc = "./Images/Bullet/bulletDark1_outline.png";

        BufferedImage image = null;
        try
        {
            image = ImageIO.read (new File ("./Images/Bullet/bulletDark1_outline.png"));
        }
        catch (IOException e) {
            e.printStackTrace ();
        }

        width = image.getWidth ();
        height = image.getHeight ();
    }

    public void setImageLoc(String imageLoc) {
        this.imageLoc = imageLoc;
    }

    private void findQuarterAndM (double degree)
    {
        degree = Math.abs (degree);

        if (degree >= 0 && degree < 90)
            this.direction = DirectionMulti.SOUTHEAST;
        else if (degree >= 90 && degree < 180)
            this.direction = DirectionMulti.SOUTHWEST;
        else if (degree >= 180 && degree < 270)
            this.direction = DirectionMulti.NORTHWEST;
        else if (degree >= 270)
            this.direction = DirectionMulti.NORTHEAST;

        if (degree == 0)
            this.m = "0";
        else if (degree == 90)
            this.m = "infinity";
        else if (degree == 180)
            this.m = "0";
        else if (degree == 270)
            this.m = "infinity";
        else
            this.m = "" + Math.abs (Math.tan (Math.toRadians (degree)));
    }

    public int getCenterX ()  {

        return (int)x + width/2;
    }

    public int getCenterY () {
        return (int)y + height/2;
    }

    protected void checkCoincidence ()
    {
        Iterator<WallMulti> walls = this.walls.iterator ();
        while (walls.hasNext ())
        {
            WallMulti wall = walls.next ();


            if(wall.getType ().equals ("H"))
            {
                if ((getCenterX () < wall.getX () + wall.getLength () + WALL_ACCURACY) &&
                    getCenterX () > wall.getX () - WALL_ACCURACY)
                {
                    if ((getCenterY () <= wall.getY () + wall.getThick () + WALL_ACCURACY) &&
                        getCenterY () >= wall.getY () - WALL_ACCURACY)
                    {
                        if (wall.isDestructible ())
                        {
                            wall.decreaseHealth (canonPower);
                            setExpired ();
                            if (!wall.isOK ())
                                walls.remove ();
                        }
                        else
                            mirrorBack ("X_AXIS");
                    }
                }
                else if ((getCenterX () == wall.getX () + wall.getLength ()) &&
                        getCenterX () == wall.getX ())
                {
                    if ((getCenterY () <= wall.getY () + wall.getThick () + WALL_ACCURACY) &&
                            getCenterY () >= wall.getY () - WALL_ACCURACY)
                    {
                        if (wall.isDestructible ())
                        {
                            wall.decreaseHealth (canonPower);
                            setExpired ();
                            if (!wall.isOK ())
                                walls.remove ();
                        }
                        else
                            mirrorBack ("Y_AXIS");
                    }
                }
            }
            else
            {
                if ((getCenterY () < wall.getY () + wall.getLength () + WALL_ACCURACY) &&
                    getCenterY () > wall.getY () - 3)
                {
                    if ((getCenterX () <= wall.getX () + wall.getThick () + WALL_ACCURACY) &&
                        getCenterX () >= wall.getX () - WALL_ACCURACY)
                    {
                        if (wall.isDestructible ())
                        {
                            wall.decreaseHealth (canonPower);
                            setExpired ();
                            if (!wall.isOK ())
                                walls.remove ();
                        }
                        else
                            mirrorBack ("Y_AXIS");
                    }
                }
                else if ((getCenterY () == wall.getY () + wall.getLength ()) &&
                        getCenterY () == wall.getY ())
                {
                    if ((getCenterX () <= wall.getX () + wall.getThick () + WALL_ACCURACY) &&
                            getCenterX () >= wall.getX () - WALL_ACCURACY)
                    {
                        if (wall.isDestructible ())
                        {
                            wall.decreaseHealth (canonPower);
                            setExpired ();
                            if (!wall.isOK ())
                                walls.remove ();
                        }
                        else
                            mirrorBack ("X_AXIS");
                    }
                }
            }
        }
        Iterator<TankMulti> tanks = this.tanks.iterator ();
        while (tanks.hasNext ())
        {
            TankMulti tank = tanks.next ();

            if ((getCenterY () <= tank.getLocY () + tank.getHeight () + TANK_ACCURACY) &&
                    getCenterY () >= tank.getLocY () - 3) {
                if ((getCenterX () <= tank.getLocX () + tank.getHeight () + TANK_ACCURACY) &&
                        getCenterX () >= tank.getLocX () - TANK_ACCURACY) {
                    tank.looseStamina (canonPower);
                    setExpired ();
                    return;
                }
            }
        }


    }

    public int getCanonPower () {
        return canonPower;
    }

    public ArrayList<TankMulti> getTanks () {
        return tanks;
    }

    public ArrayList<WallMulti> getWalls () {
        return walls;
    }

    public static int getWallAccuracy () {
        return WALL_ACCURACY;
    }

    public static int getTankAccuracy () {
        return TANK_ACCURACY;
    }

    protected void setExpired ()
    {
        expired = true;
    }

    protected void mirrorBack (String axis)
    {
        if (axis == null)
            return;
        if (axis.equals ("Y_AXIS"))
        {
            degree = 180 - degree;
            if (degree < 0)
                degree = 360 + degree;
            findQuarterAndM (degree);

        }
        else if (axis.equals ("X_AXIS"))
        {
            degree = 360 - degree;
            findQuarterAndM (degree);
        }
    }

    private void update ()
    {
        try {
            if (direction.getY_AXIS ().equals ("+"))  // that means UP
            {
                if (m.equals ("infinity"))
                {
                    y += STEP;
                } else
                {
                    double newM = Math.abs (Double.parseDouble (m));

                    if (direction.getX_AXIS ().equals ("+"))
                    {
                        if (newM >= 1)
                        {
                            y += STEP;
                            x += ((1/newM)*STEP);
                        }
                        else if (newM != 0 && newM < 1)
                        {
                            x += STEP;
                            y += newM * STEP;
                        } else if (newM == 0)
                        {
                            x += STEP;
                        }
                    } else if (direction.getX_AXIS ().equals ("-"))
                    {
                        if (newM != 0 && newM < 1)
                        {
                            y += newM * STEP;
                            x -= STEP;
                        }
                        else if (newM >= 1)
                        {
                            x -= ((1/newM)*STEP);
                            y += STEP;
                        } else if (newM == 0)
                        {
                            x -= STEP;
                        }
                    }
                }
            }
            else if (direction.getY_AXIS ().equals ("-"))
            {
                if (m.equals ("infinity"))
                {
                    y -= STEP;
                } else
                {
                    double newM = Math.abs (Double.parseDouble (m));

                    if (direction.getX_AXIS ().equals ("+"))
                    {
                        if (newM >= 1)
                        {
                            y -= STEP;
                            x += ((1/newM)*STEP);
                        }
                        else if (newM != 0 && newM < 1)
                        {

                            x += STEP;
                            y -= newM * STEP;
                        } else if (newM == 0)
                        {
                            x += STEP;
                        }
                    } else if (direction.getX_AXIS ().equals ("-"))
                    {
                        if (newM != 0 && newM < 1)
                        {
                            x -= STEP;
                            y -= newM * STEP;

                        }
                        else if (newM >= 1)
                        {
                            y -= STEP;
                            x -= ((1/newM)*STEP);
                        } else if (newM == 0)
                        {
                            x -= STEP;
                        }
                    }
                }
            }
            checkCoincidence ();
        } catch (NumberFormatException e)
        {
            System.out.println (e.getMessage ());
        }
    }

    public boolean hasExpired ()
    {
        return System.currentTimeMillis () - startTime >= 3500 ||
                expired;
    }

    public long getStartTime () {
        return startTime;
    }

    public boolean getHandExpired ()
    {
        return expired;
    }

    @Override
    public void run () {
        update ();
    }

    public int getX () {
        return ((int)x);
    }

    public int getY () {
        return ((int)y);
    }

    public double getDegree () {
        return degree;
    }

    public String getImageLoc()
    {
        return imageLoc;
    }
}
