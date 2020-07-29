package Game;

import Game.Server.SynchronizedArrayList;

import javax.imageio.ImageIO;
import javax.swing.plaf.TableHeaderUI;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Tank implements Runnable
{
    private int locX,locY,stamina;
    private int degree;
    private static String imageAddress = "Images/Tanks/red315.png";
    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private boolean shot;
    private boolean hasProtection;
    private String bulletType;
    private boolean fireDestroyed;
    private boolean destroyed;
    private boolean mousePress;
    private int height;
    private int width;
    private int mouseX, mouseY;
    private KeyHandler keyHandler;
    private boolean canShot;
    private ArrayList<Bullet> bullets;
    private ArrayList<Wall> walls;
    private ArrayList<Tank> tanks;
    private  BufferedImage tankImage;
    private static BufferedImage fireImage;
    private static BufferedImage fireDestroyImage;
    private Prize prizeOwn;
    private Prizes prizes;
    public Prize getPrizeOwn()
    {
        return prizeOwn;
    }
    private int canonPower;
    private Maps maps;
    private SynchronizedArrayList synchronizedArrayList;

    static {
        try {
            fireImage = ImageIO.read (new File ("./Images/Bullet/shotLarge.png"));
            fireDestroyImage = ImageIO.read (new File ("./Images/Explosion/explosion3.png"));
        } catch (IOException e)
        {
            e.printStackTrace ();
        }
    }

    public Tank (ArrayList<Bullet> bullets, ArrayList<Wall> walls, ArrayList<Tank> tanks, Prizes prizes,
                int tankStamina,int canonPower , Maps maps ,
                 SynchronizedArrayList synchronizedArrayList)
    {
        this.synchronizedArrayList = synchronizedArrayList;
        this.maps = maps;
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
        mousePress = false;
        mouseX = 0;
        mouseY = 0;
        keyHandler = new KeyHandler ();
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

    public void setProtection (boolean hasProtection) {
        this.hasProtection = hasProtection;
    }



    public void setBulletType (String bulletType) {
        this.bulletType = bulletType;
    }

    public String getBulletType () {
        return bulletType;
    }

    public int getHeight () {
        return height;
    }

    public int getWidth () {
        return width;
    }

    public void looseStamina (int damage) {
        if (!hasProtection)
            stamina -= damage;
        if (stamina <= 0) {
            fireDestroyed = true;
            keyUP = false;
            keyDOWN = false;
            keyLEFT = false;
            keyRIGHT = false;
            new Thread (new Runnable () {
                @Override
                public void run () {
                    try {
                        Thread.sleep (200);
                        destroyed = true;
                    } catch (InterruptedException e) {
                        e.printStackTrace ();
                    }
                }
            }).start ();
        }

    }

    public KeyHandler getKeyHandler () {
        return keyHandler;
    }


    public int getCenterX () {
        return locX + width / 2 - 2;
    }

    public int getCenterY () {
        return locY + height / 2 - 2;
    }

    public int getCanonStartX () {

        return getCenterX () +
                ((int) (Math.sqrt (968) * Math.cos (Math.toRadians (degree))));
    }

    public int getCanonStartY () {
        return getCenterY () +
                ((int) (Math.sqrt (968) * Math.sin (Math.toRadians (degree))));
    }


    public String getImageAddress () {
        return imageAddress;
    }

    public int getLocX () {
        return locX;
    }

    public void setLocX (int locX) {
        this.locX = locX;
    }

    public void addLocX (int adder) {
        locX += adder;
    }

    public int getLocY () {
        return locY;
    }

    public void setLocY (int locY) {
        this.locY = locY;
    }

    public void addLocY (int adder) {
        locY += adder;
    }

    public int getStamina () {
        return stamina;
    }

    public ArrayList<Tank> getTanks () {
        return tanks;
    }

    public ArrayList<Bullet> getBullets () {
        return bullets;
    }

    public ArrayList<Wall> getWalls () {
        return walls;
    }

    public int getDegree () {
        return degree;
    }

    public void increaseDegree () {
        degree += 10;
        if (degree >= 360)
            degree = 0;
    }

    public void decreaseDegree () {
        degree -= 10;
        if (degree <= 0) {
            degree = 359;
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


    public void update()
    {

        System.out.println("Looking for data");

        ArrayList<String> hh = synchronizedArrayList.get();


        System.out.println(hh.get(0)
                + "*" + hh.get(1)
                + "*" + hh.get(2)
                + "*" + hh.get(3)
                + "*" + hh.get(4));

        if(mousePress)
        {
            this.setLocY( mouseY - 30 / 2 );
            this.setLocX( mouseX - 30 / 2 );
        }

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


    @Override
    public void run()
    {
        this.update();
    }

    public boolean isFireDestroyed () {
        return fireDestroyed;
    }

    public boolean isDestroyed () {
        return destroyed;
    }

    public boolean isShot () {
        return shot;
    }

    public boolean isCanShot () {
        return canShot;
    }

    public void setDegree (int degree) {
        this.degree = degree;
    }

    public void setCanShot (boolean canShot) {
        this.canShot = canShot;
    }

    public void setShot (boolean shot) {
        this.shot = shot;
    }

    public  BufferedImage getTankImage () {
        return tankImage;
    }

    public static BufferedImage getFireDestroyImage () {
        return fireDestroyImage;
    }

    public static BufferedImage getFireImage () {
        return fireImage;
    }

    protected class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed (KeyEvent e) {
            if (!(destroyed || fireDestroyed)) {
                switch (e.getKeyCode ()) {
                    case KeyEvent.VK_UP:
                        keyUP = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        keyDOWN = true;
                        break;
                    case KeyEvent.VK_LEFT:
                        keyLEFT = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        keyRIGHT = true;
                        break;
                }
            }
        }

        @Override
        public void keyReleased (KeyEvent e) {
            if (!(destroyed || fireDestroyed)) {
                switch (e.getKeyCode ()) {
                    case KeyEvent.VK_UP:
                        keyUP = false;
                        break;
                    case KeyEvent.VK_DOWN:
                        keyDOWN = false;
                        break;
                    case KeyEvent.VK_LEFT:
                        keyLEFT = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        keyRIGHT = false;
                        break;
                    case KeyEvent.VK_SPACE:
                        if (canShot) {
                            Music music = new Music ();
                            music.setFilePath ("Files/Sounds/Bullet.au", false);
                            music.execute ();
                            if (getBulletType ().equals ("Laser"))
                            {
                                bullets.add (new LaserBullet (getCanonStartX (), getCanonStartY (),
                                        getDegree (), System.currentTimeMillis (), walls, tanks,canonPower));
                                setBulletType ("Normal");
                            } else
                            {
                                bullets.add (new Bullet (getCanonStartX (), getCanonStartY (),
                                        getDegree (), System.currentTimeMillis (), walls, tanks,canonPower));
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
            }

        }
    }

    public int getCanonPower()
    {
        return canonPower;
    }
}




