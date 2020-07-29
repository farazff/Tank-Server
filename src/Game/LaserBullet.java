package Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class LaserBullet extends Bullet
{

    public LaserBullet (int x, int y, double degree, long startTime, ArrayList<Wall> walls, ArrayList<Tank> tanks, int canonPower) {
        super (x, y, degree, startTime, walls, tanks, canonPower);
        try {
            super.setImage (ImageIO.read (new File ("./Images/Bullet/bulletRed1_outline.png")));
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }


    @Override
    protected void checkCoincidence () {
        Iterator<Wall> walls = this.getWalls ().iterator ();
        while (walls.hasNext ())
        {
            Wall wall = walls.next ();


            if (wall.getType ().equals ("H"))
            {
                if ((getCenterX () < wall.getX () + wall.getLength () + getWallAccuracy ()) &&
                        getCenterX () > wall.getX () - getWallAccuracy ())
                {
                    if ((getCenterY () <= wall.getY () + wall.getThick () + getWallAccuracy ()) &&
                            getCenterY () >= wall.getY () - getWallAccuracy ())
                    {
                        if (wall.isDestructible ())
                        {
                            wall.decreaseHealth (wall.getHealth ());
                            walls.remove ();
                        }
                        else
                            mirrorBack ("X_AXIS");
                    }
                }
                else if ((getCenterX () == wall.getX () + wall.getLength ()) &&
                        getCenterX () == wall.getX ())
                {
                    if ((getCenterY () <= wall.getY () + wall.getThick () + getWallAccuracy ()) &&
                            getCenterY () >= wall.getY () - getWallAccuracy ())
                    {
                        if (wall.isDestructible ())
                        {
                            wall.decreaseHealth (wall.getHealth ());
                            walls.remove ();
                        }
                        else
                            mirrorBack ("Y_AXIS");
                    }
                }
            }
            else
            {
                if ((getCenterY () < wall.getY () + wall.getLength () + getWallAccuracy ()) &&
                        getCenterY () > wall.getY () - 3)
                {
                    if ((getCenterX () <= wall.getX () + wall.getThick () + getWallAccuracy ()) &&
                            getCenterX () >= wall.getX () - getWallAccuracy ())
                    {
                        if (wall.isDestructible ())
                        {

                            wall.decreaseHealth (wall.getHealth ());
                            walls.remove ();
                        }
                        else
                            mirrorBack ("Y_AXIS");
                    }
                }
                else if ((getCenterY () == wall.getY () + wall.getLength ()) &&
                        getCenterY () == wall.getY ())
                {
                    if ((getCenterX () <= wall.getX () + wall.getThick () + getWallAccuracy ()) &&
                            getCenterX () >= wall.getX () - getWallAccuracy ())
                    {
                        if (wall.isDestructible ())
                        {
                            wall.decreaseHealth (wall.getHealth ());
                            walls.remove ();
                        }
                        else
                            mirrorBack ("X_AXIS");
                    }
                }
            }
        }
        Iterator<Tank> tanks = this.getTanks ().iterator ();
        while (tanks.hasNext ())
        {
            Tank tank = tanks.next ();

            if ((getCenterY () <= tank.getLocY () + tank.getHeight () + getTankAccuracy ()) &&
                    getCenterY () >= tank.getLocY () - 3) {
                if ((getCenterX () <= tank.getLocX () + tank.getHeight () + getTankAccuracy ()) &&
                        getCenterX () >= tank.getLocX () - getTankAccuracy ()) {
                    tank.looseStamina (tank.getStamina ());
                    return;
                }
            }
        }
    }

    @Override
    public boolean hasExpired ()
    {
        return System.currentTimeMillis () - getStartTime () >= 2500 ||
                getHandExpired ();
    }
}
