package MultiGame.Game;

import GameData.User;
import MultiGame.Status.GameStatus;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class TankMulti implements Runnable , Serializable
{
    int code;
    boolean done;
    private int locX,locY,stamina,degree,height,width ,canonPower,number;  ////ok to serialize
    private String bulletType;   ////ok to serialize
    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT,shot,hasProtection,fireDestroyed,destroyed,canShot;  ////ok to serialize
    private ArrayList<BulletMulti> bullets;  ////ok to serialize
    private ArrayList<WallMulti> walls;  ////ok to serialize
    private ArrayList<TankMulti> tanks;////ok to serialize
    private PrizeMulti prizeOwn;  ////ok to serialize
    private PrizesMulti prizes;  ////ok to serialize
    private ArrayList<Character> data;  ////ok to serialize
    private GameStatus status;
    private User user;
    int[] kills;

    public TankMulti (ArrayList<BulletMulti> bullets, ArrayList<WallMulti> walls, ArrayList<TankMulti> tanks,
                      PrizesMulti prizes,
                      int tankStamina, int canonPower , MapsMulti maps ,
                      ArrayList<Character> data, int number, GameStatus status, User user,int code,int[] kills)
    {
        this.kills = kills;
        this.code = code;
        this.status = status;
        done = false;
        this.number= number;
        this.data = data;
        this.canonPower = canonPower;
        this.prizes = prizes;
        this.bullets = bullets;
        this.walls = walls;
        this.tanks = tanks;
        destroyed = false;
        this.user = user;
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
            BufferedImage tankImage = ImageIO.read(new File("Images/Tanks/Test.png"));
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
            status.setExplode(true);
            status.addWinners(this.getUser().getUserName());
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

    public User getUser () {
        return user;
    }

    public boolean canMoveForward()
    {
        boolean ans = true;

        Iterator<WallMulti> walls = this.walls.iterator();

        while(walls.hasNext ())
        {
            WallMulti wall = walls.next();

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

        Iterator<WallMulti> walls = this.walls.iterator();

        while(walls.hasNext ())
        {
            WallMulti wall = walls.next();

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
        for(PrizeMulti prize : prizes.getPrizes())
        {
            if(prize.isActive())
            {
                if (((locX - prize.getX()) * (locX - prize.getX()) + (locY - prize.getY()) * (locY - prize.getY())) <= 35 * 35) {
                    if (prizeOwn == null)
                    {
                        status.setUsePrize(true);
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


    /**
     * check if the tank can move
     * @param forX the distance of x
     * @param forY the distance of y
     * @return true it the tank can move
     */
    public boolean canMove(int forX , int forY)
    {


        int tankX = locX + width/2;
        int tankY = locY + height/2;


        for(WallMulti wall : walls)
        {
            if(wall.getType().equals("H"))
            {

                int disStart = (wall.getX()-tankX)*(wall.getX()-tankX) + (wall.getY()-tankY)*(wall.getY()-tankY);
                if(disStart<=700)
                {
                    int dis2 = (wall.getX()-(tankX+forX))*(wall.getX()-(tankX+forX)) + (wall.getY()-(tankY+forY))*(wall.getY()-(tankY+forY));
                    if(dis2<disStart)
                        return false;
                }

                int disEnd = ((wall.getX()+wall.getLength())-tankX)*((wall.getX()+wall.getLength())-tankX) + (wall.getY()-tankY)*(wall.getY()-tankY);
                if(disEnd<=1700)
                {
                    int dis2 = ((wall.getX()+wall.getLength())-(tankX+forX))*((wall.getX()+wall.getLength())-(tankX+forX)) + (wall.getY()-(tankY+forY))*(wall.getY()-(tankY+forY));
                    if(dis2<disEnd)
                        return false;
                }

                if(tankX >= wall.getX() && tankX <= (wall.getX() + wall.getLength()))
                {
                    //tank upper than wall
                    if( tankY+20 < wall.getY() && wall.getY()-(tankY+40)<=20 )
                    {
                        if( wall.getY() <= (tankY+20 + forY) )
                        {
                            return false;
                        }
                    }

                    //tank lower than wall
                    if( (tankY-35) > wall.getY() && (tankY-35)-wall.getY()<=25 )
                    {
                        if( wall.getY() >= (tankY - 35 + forY) )
                        {
                            return false;
                        }
                    }
                }
            }

            if(wall.getType().equals("V"))
            {

                int disStart = (wall.getX()-tankX)*(wall.getX()-tankX) + (wall.getY()-tankY)*(wall.getY()-tankY);
                if(disStart<=700)
                {
                    int dis2 = (wall.getX()-(tankX+forX))*(wall.getX()-(tankX+forX)) + (wall.getY()-(tankY+forY))*(wall.getY()-(tankY+forY));
                    if(dis2<disStart)
                        return false;
                }

                int disEnd = (wall.getX()-tankX)*(wall.getX()-tankX) + ((wall.getY()+wall.getLength())-tankY)*((wall.getY()+wall.getLength())-tankY);
                if(disEnd<=3600)
                {
                    int dis2 = (wall.getX()-(tankX+forX))*(wall.getX()-(tankX+forX)) + ((wall.getY()+wall.getLength())-(tankY+forY))*((wall.getY()+wall.getLength())-(tankY+forY));
                    if(dis2<disEnd)
                        return false;
                }


                if(tankY >= wall.getY() && tankY <= (wall.getY() + wall.getLength()))
                {
                    //tank at the left side of the wall
                    if( tankX+20 < wall.getX() && wall.getX()-(tankX+40)<=20 )
                    {
                        if( wall.getX() <= (tankX+20 + forX) )
                        {
                            return false;
                        }
                    }

                    //tank lower than wall
                    if( (tankX-35) > wall.getX() && (tankX-35)-wall.getX()<=25 )
                    {
                        if( wall.getX() >= (tankX - 35 + forX) )
                        {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }




    public void update()
    {
        done = false;
        if(data.size()==0)
        {
            data.add('0');
            data.add('0');
            data.add('0');
            data.add('0');
            data.add('0');
        }

        boolean isPressed = shot;

        keyUP = data.get(0) == '1';
        keyDOWN = data.get(1) == '1';
        keyLEFT = data.get(2) == '1';
        keyRIGHT = data.get(3) == '1';
        shot = data.get(4) == '1';

        if(!shot && isPressed)
        {
            if (canShot)
            {
                status.setShot(true);
                if (getBulletType ().equals ("Laser"))
                {
                    bullets.add (new LaserBulletMulti (getCanonStartX (), getCanonStartY (),
                            getDegree (), System.currentTimeMillis (), walls, tanks,canonPower,this.code,kills));
                    setBulletType ("Normal");
                } else
                {
                    bullets.add (new BulletMulti (getCanonStartX (), getCanonStartY (),
                            getDegree (), System.currentTimeMillis (), walls, tanks,canonPower,this.code,kills));
                }

                canShot = false;
                shot = true;
                new Thread (new Runnable () {
                    @Override
                    public void run () {
                        try {
                            Thread.sleep (100);
                            shot = false;
                        } catch (InterruptedException e) {
                            e.printStackTrace ();
                        }
                    }
                }).start ();
                new Thread (new Runnable () {
                    @Override
                    public void run () {
                        try {
                            Thread.sleep (500);
                            canShot = true;
                        } catch (InterruptedException e) {
                            e.printStackTrace ();
                        }
                    }
                }).start ();
            }
        }

        int forX = (int) (6 * Math.cos (Math.toRadians (this.getDegree ())));
        int forY = (int) (6 * Math.sin (Math.toRadians (this.getDegree ())));

        if(keyUP && canMove(forX , forY) && isEmpty(forX,forY,1))
        {
            this.addLocX(forX);
            this.addLocY(forY);
        }
        if(keyDOWN && canMove(-1*forX , -1*forY) && isEmpty(forX,forY,-1))
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
        this.setLocX(Math.min(this.getLocX(), GameFrameMulti.GAME_WIDTH - 30));
        this.setLocY(Math.max(this.getLocY(), 0));
        this.setLocY(Math.min(this.getLocY(), GameFrameMulti.GAME_HEIGHT - 30));
        done = true;
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

    public ArrayList<TankMulti> getTanks ()
    {
        return tanks;
    }

    public ArrayList<BulletMulti> getBullets ()
    {
        return bullets;
    }

    public ArrayList<WallMulti> getWalls ()
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

    public PrizeMulti getPrizeOwn()
    {
        return prizeOwn;
    }

    public int getNumber()
    {
        return number;
    }
}




