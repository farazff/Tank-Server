package Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Tank implements Runnable , Serializable
{
    private int locX,locY,stamina,degree,height,width ,canonPower;
    private static String bulletType;
    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT,shot,hasProtection,fireDestroyed,destroyed,canShot;
    private ArrayList<Bullet> bullets;
    private ArrayList<Wall> walls;
    private ArrayList<Tank> tanks;
    private BufferedImage tankImage;
    private static BufferedImage fireImage,fireDestroyImage;
    private Prize prizeOwn;
    private Prizes prizes;
    private ArrayList<String> data;

    static
    {
        try
        {
            fireImage = ImageIO.read (new File ("./Images/Bullet/shotLarge.png"));
            fireDestroyImage = ImageIO.read (new File ("./Images/Explosion/explosion3.png"));
        }
        catch (IOException e)
        {
            e.printStackTrace ();
        }
    }

    public Tank (ArrayList<Bullet> bullets, ArrayList<Wall> walls, ArrayList<Tank> tanks, Prizes prizes,
                int tankStamina,int canonPower , Maps maps ,
                ArrayList<String> data)
    {
        this.data = data;
        this.canonPower = canonPower;
        this.prizes = prizes;
        this.bullets = bullets;
        this.walls = walls;
        this.tanks = tanks;
        destroyed = false;
        fireDestroyed = false;
        hasProtection = false;
        canShot = true;
        keyUP = false;
        keyDOWN = false;
        keyRIGHT = false;
        keyLEFT = false;
        shot = false;
        prizeOwn = null;
        bulletType = "Normal";
        do
        {
            locX = new Random().nextInt(((16 * 720) / 9) - 200) + 100;
            locY = new Random().nextInt(720 - 200) + 100;

        }while(!canMoveForward() || !maps.canPut(locX,locY) || !isEmpty(0,0,1));

        stamina = tankStamina;
        degree = 45;
        try
        {
            tankImage = ImageIO.read (new File (getImageAddress ()));
            height = tankImage.getHeight ();
            width = tankImage.getWidth ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    public void looseStamina (int damage)
    {
        if(!hasProtection)
            stamina -= damage;
        if(stamina <= 0)
        {
            fireDestroyed = true;
            keyUP = false;
            keyDOWN = false;
            keyLEFT = false;
            keyRIGHT = false;
            new Thread (new Runnable () {
                @Override
                public void run ()
                {
                    try
                    {
                        Thread.sleep (200);
                        destroyed = true;
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace ();
                    }
                }
            }).start ();
        }
    }

    public boolean canMoveForward()
    {
        boolean ans = true;

        Iterator<Wall> walls = this.walls.iterator();

        while(walls.hasNext ())
        {
            Wall wall = walls.next();

            if(wall.getType ().equals ("H"))
            {
                if(locX>=wall.getX()-15 && locX<=wall.getX()+wall.getLength()+15)
                {
                    if(wall.getY()-locY<=50 && wall.getY()-locY>=0 && degree>=0 && degree<=150)
                    {
                        ans=false;
                    }
                    if(locY-wall.getY()<=8 && locY-wall.getY()>=0 && degree>=180 && degree<=360)
                    {
                        ans=false;
                    }
                }
            }

            if(wall.getType ().equals ("V"))
            {
                if(locY>=wall.getY()-15 &&locY<=wall.getY()+wall.getLength()+15)
                {
                    if(wall.getX()-locX<=50 && wall.getX()-locX>=0 &&
                            ((degree>=0 && degree<=90)||(degree>=270 && degree<=360)) )
                    {
                        ans=false;
                    }
                    if(locX-wall.getX()<=8 && locX-wall.getX()>=0 &&
                            degree>=90 && degree<=270 )
                    {
                        ans=false;
                    }
                }
            }
        }
        return ans;
    }

    public boolean canMoveBackward()
    {
        boolean ans = true;

        Iterator<Wall> walls = this.walls.iterator();

        while(walls.hasNext ())
        {
            Wall wall = walls.next();

            if(wall.getType ().equals ("H"))
            {
                if(locX>=wall.getX()-15 && locX<=wall.getX()+wall.getLength()+15)
                {
                    //tank upper than wall
                    if(wall.getY()-locY<=60 && wall.getY()-locY>=0 && degree>=270 && degree<=360)
                    {
                        ans=false;
                    }
                    //tank lower than wall
                    if(locY-wall.getY()<=8 && locY-wall.getY()>=0 && degree>=0 && degree<=180)
                    {
                        ans=false;
                    }
                }
            }

            if(wall.getType ().equals ("V"))
            {
                if(locY>=wall.getY()-15 &&locY<=wall.getY()+wall.getLength()+15)
                {
                    //tank at the left side of the wall
                    if(wall.getX()-locX<=60 && wall.getX()-locX>=0 &&
                            (degree>=90 && degree<=270) )
                    {
                        ans=false;
                    }
                    //tank at the right side of the wall
                    if(locX-wall.getX()<=8 && locX-wall.getX()>=0 &&
                            ((degree>=0 && degree<=90)||(degree>=270 && degree<=360)) )
                    {
                        ans=false;
                    }
                }
            }
        }
        return ans;
    }

    public boolean isEmpty(int forX , int forY , int dir)
    {
        boolean ans = true;
        if(dir==-1)
        {
            forX *= (-1);
            forY *= (-1);
        }

        for(int i=0;i<tanks.size();i++)
        {
            if(tanks.get(i)!=this)
            {
                int thisX = this.getLocX() + forX;
                int thisY = this.getLocY() + forY;
                int otherX = tanks.get(i).getLocX();
                int otherY = tanks.get(i).getLocY();
                if(   ( (thisX-otherX)*(thisX-otherX) + (thisY-otherY)*(thisY-otherY) ) <= (56)*(56)  )
                {
                    ans = false;
                    break;
                }
            }
        }
        return ans;
    }

    public void checkPrize()
    {
        for(Prize prize : prizes.getPrizes())
        {
            if(prize.isActive())
            {
                if (((locX - prize.getX()) * (locX - prize.getX()) + (locY - prize.getY()) * (locY - prize.getY())) <= 35 * 35) {
                    if (prizeOwn == null)
                    {
                        Music music = new Music();
                        music.setFilePath("Files/Sounds/GetPrize.au",false);
                        music.execute();

                        prize.deActive();
                        prizeOwn = prize;
                        if (prize.getType().equals("Health")) {
                            stamina += (stamina / 10);
                        }
                        if (prize.getType().equals("Power2")) {
                            canonPower *= 2;
                        }
                        if (prize.getType().equals("Power3")) {
                            canonPower *= 3;
                        }
                        if (prize.getType ().equals ("Laser")) {
                            setBulletType ("Laser");
                        }

                        if (prize.getType ().equals ("Protect")) {
                            setProtection (true);
                        }

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(10000);
                                    prizeOwn = null;
                                    if (prize.getType().equals("Power2")) {
                                        canonPower /= 2;
                                    }
                                    if (prize.getType().equals("Power3")) {
                                        canonPower /= 3;
                                    }
                                    if (prize.getType ().equals ("Laser")) {
                                        setBulletType ("Normal");
                                    }
                                    Thread.sleep (5000);
                                    if (prize.getType ().equals ("Protect")) {
                                        setProtection (false);
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }
            }
        }
    }

    public void update()
    {
        ArrayList<String> hh = data;
        System.out.println(hh.get(0)
                + "*" + hh.get(1)
                + "*" + hh.get(2)
                + "*" + hh.get(3)
                + "*" + hh.get(4));

        if(hh.get(0).equals("1"))
            keyUP = true;
        if(hh.get(0).equals("0"))
            keyUP = false;

        if(hh.get(1).equals("1"))
            keyDOWN = true;
        if(hh.get(1).equals("0"))
            keyDOWN = false;

        if(hh.get(2).equals("1"))
            keyLEFT = true;
        if(hh.get(2).equals("0"))
            keyLEFT = false;

        if(hh.get(3).equals("1"))
            keyRIGHT = true;
        if(hh.get(3).equals("0"))
            keyRIGHT = false;

        if(hh.get(4).equals("1"))
            shot = true;
        if(hh.get(4).equals("0"))
            shot = false;

        int forX = (int) (6 * Math.cos (Math.toRadians (this.getDegree ())));
        int forY = (int) (6 * Math.sin (Math.toRadians (this.getDegree ())));

        if(keyUP && canMoveForward() && isEmpty(forX,forY,1))
        {
            this.addLocX(forX);
            this.addLocY(forY);
        }
        if(keyDOWN && canMoveBackward() && isEmpty(forX,forY,-1))
        {
            this.addLocX(-1*forX);
            this.addLocY(-1*forY);
        }

        checkPrize();

        if(keyRIGHT && !keyLEFT)
            this.increaseDegree();

        if(!keyRIGHT  && keyLEFT)
            this.decreaseDegree();


        this.setLocX(Math.max(this.getLocX(), 0));
        this.setLocX(Math.min(this.getLocX(), GameFrame.GAME_WIDTH - 30));
        this.setLocY(Math.max(this.getLocY(), 0));
        this.setLocY(Math.min(this.getLocY(), GameFrame.GAME_HEIGHT - 30));
    }

    @Override
    public void run()
    {
        this.update();
    }

    public boolean isFireDestroyed ()
    {
        return fireDestroyed;
    }

    public boolean isDestroyed ()
    {
        return destroyed;
    }

    public boolean isShot ()
    {
        return shot;
    }

    public boolean isCanShot ()
    {
        return canShot;
    }

    public  BufferedImage getTankImage ()
    {
        return tankImage;
    }

    public static BufferedImage getFireDestroyImage ()
    {
        return fireDestroyImage;
    }

    public static BufferedImage getFireImage ()
    {
        return fireImage;
    }

    public int getCenterX ()
    {
        return locX + width / 2 - 2;
    }

    public int getCenterY ()
    {
        return locY + height / 2 - 2;
    }

    public int getCanonStartX ()
    {
        return getCenterX () +
                ((int) (Math.sqrt (968) * Math.cos (Math.toRadians (degree))));
    }

    public int getCanonStartY ()
    {
        return getCenterY () +
                ((int) (Math.sqrt (968) * Math.sin (Math.toRadians (degree))));
    }

    public String getImageAddress ()
    {
        String imageAddress = "Images/Tanks/red315.png";
        return imageAddress;
    }

    public int getLocX ()
    {
        return locX;
    }

    public void setLocX (int locX)
    {
        this.locX = locX;
    }

    public void addLocX (int adder)
    {
        locX += adder;
    }

    public int getLocY ()
    {
        return locY;
    }

    public void setLocY (int locY)
    {
        this.locY = locY;
    }

    public void addLocY (int adder)
    {
        locY += adder;
    }

    public int getStamina ()
    {
        return stamina;
    }

    public ArrayList<Tank> getTanks ()
    {
        return tanks;
    }

    public ArrayList<Bullet> getBullets ()
    {
        return bullets;
    }

    public ArrayList<Wall> getWalls ()
    {
        return walls;
    }

    public int getDegree ()
    {
        return degree;
    }

    public void increaseDegree ()
    {
        degree += 10;
        if (degree >= 360)
            degree = 0;
    }

    public void decreaseDegree ()
    {
        degree -= 10;
        if (degree <= 0)
        {
            degree = 359;
        }
    }

    public void setProtection (boolean hasProtection)
    {
        this.hasProtection = hasProtection;
    }

    public void setBulletType (String bulletType)
    {
        this.bulletType = bulletType;
    }

    public String getBulletType ()
    {
        return bulletType;
    }

    public int getHeight ()
    {
        return height;
    }

    public Prize getPrizeOwn()
    {
        return prizeOwn;
    }

}




