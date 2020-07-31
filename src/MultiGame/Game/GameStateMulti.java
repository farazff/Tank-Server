package MultiGame.Game;

import MultiGame.Server.ClientHandler;
import MultiGame.Status.GameStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class GameStateMulti implements Serializable
{

	private ArrayList<TankMulti> tanks;                 ////ok to serialize
	private InteractArrayListMulti<BulletMulti> bullets;     ////ok to serialize
	public int gameOver,players;                  ////ok to serialize
	private MapsMulti maps;								 ////ok to serialize
	private PrizesMulti prizes;							 ////ok to serialize
	private GameStatus status;


	public GameStateMulti(int players,int tankStamina,int canonPower,int wallStamina,
						  ArrayList<ClientHandler> clientHandlers)
	{
		////not ok to serialize
		this.players = players;
		maps = new MapsMulti(wallStamina);
		bullets = new InteractArrayListMulti<> ();
		tanks = new ArrayList<> ();
		prizes = new PrizesMulti(maps,tanks);

		for(int i=1;i<=players;i++)
		{
			TankMulti tank1 = new TankMulti(bullets, maps.getWalls(), tanks, prizes,
					tankStamina, canonPower, maps,clientHandlers.get(i-1).getData());
			tanks.add(tank1);
		}


		//status = new MultiGame.Status.GameStatus(tanks,bullets,maps,prizes,players);


		gameOver = 0;
		Thread t1 = new Thread(prizes);
		t1.start();

	}

	public GameStatus getStatus()
	{
		return status;
	}

	public PrizesMulti getPrizes()
	{
		return prizes;
	}

	public ArrayList<TankMulti> getTanks ()
	{
		return tanks;
	}

	public MapsMulti getMaps()
	{
		return maps;
	}

	/**
	 * The method which updates the game state.
	 */
	public void update()
	{

		ExecutorService executorService = Executors.newCachedThreadPool();

		Iterator<BulletMulti> bulletIterator = bullets.iterator();
		bullets.setIterate(true);
		while(bulletIterator.hasNext())
		{
			BulletMulti bullet = bulletIterator.next ();
			if (bullet.hasExpired())
				bulletIterator.remove();
			else
				executorService.execute(bullet);
		}
		bullets.setIterate(false);


		Iterator<TankMulti> tankIterator = tanks.iterator();
		while(tankIterator.hasNext())
		{
			TankMulti tank = tankIterator.next();

			if(tank.isDestroyed ())
				tankIterator.remove ();
			else
				executorService.execute(tank);
		}
		executorService.shutdown();

		try
		{
			while(!executorService.isTerminated())
			{
				Thread.sleep(15);
			}
		}
		catch(InterruptedException e)
		{
			e.printStackTrace ();
		}

		try
		{
			while(true)
			{
				boolean isDone = executorService.awaitTermination(5, TimeUnit.MILLISECONDS);
				if(isDone)
				{
					System.out.println("Changing status");
					status = new GameStatus(tanks,bullets,maps,prizes,players);
					System.out.println("done updating");
					break;
				}
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public ArrayList<BulletMulti> getBullets () {
		return bullets;
	}

}

