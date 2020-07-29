package Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Prize
{
    private int x;
    private int y;
    private String type;
    private BufferedImage img;
    private boolean active;

    public Prize(int rand , int x ,int y)
    {
        this.x = x;
        this.y = y;
        active = true;
        if(rand==1)
        {
            type = "Protect";
            try
            {
                img = ImageIO.read(new File("Images/Prizes/Protect.png"));
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        if(rand==2)
        {
            type = "Laser";
            try
            {
                img = ImageIO.read(new File("Images/Prizes/Laser.png"));
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        if(rand==3)
        {
            type = "Health";
            try
            {
                img = ImageIO.read(new File("Images/Prizes/Health.png"));
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        if(rand==4)
        {
            type = "Power2";
            try
            {
                img = ImageIO.read(new File("Images/Prizes/Power2.png"));
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        if(rand==5)
        {
            type = "Power3";
            try
            {
                img = ImageIO.read(new File("Images/Prizes/Power3.png"));
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(10000);
                    deActive();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
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

    public BufferedImage getImg()
    {
        return img;
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
