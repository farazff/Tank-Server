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
	int[] kills;
	private ArrayList<String> names;


	public GameStateMulti(int players,int tankStamina,int canonPower,int wallStamina,
						  ArrayList<ClientHandler> clientHandlers,int[] kills,ArrayList<String> names)
	{
		////not ok to serialize
		this.names = names;
		this.kills = kills;
		this.players = players;
		maps = new MapsMulti(wallStamina);
		bullets = new InteractArrayListMulti<> ();
		tanks = new ArrayList<> ();
		prizes = new PrizesMulti(maps,tanks);

		status = new GameStatus(tanks,bullets,maps,prizes,players);

		for(int i=1;i<=players;i++)
		{
			TankMulti tank1 = new TankMulti(bullets, maps.getWalls(), tanks, prizes,
					tankStamina, canonPower, maps,clientHandlers.get(i-1).getData(),i,status,
					clientHandlers.get (i-1).getUser (),i,kills);
			tanks.add(tank1);
		}

		gameOver = 0;
	}

	public void addPrize()
	{
		prizes.putPrize();
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
		if (!executorService.isTerminated ())
		{
			try {
				Thread.sleep (1);
			} catch (InterruptedException e)
			{
				e.printStackTrace ();
			}
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

		while(true)
		{
			try
			{
				Thread.sleep (10);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace ();
			}

			boolean done = true;

			for (TankMulti tank : tanks)
			{
				if(!tank.done)
				{
					done = false;
					break;
				}
			}


			if(done)
				break;
		}

		if(tanks.size()==1)
		{
			status.addWinners(tanks.get(0).getUser().getUserName());
			tanks.clear();
			status.setGameOver(true);
		}

		status.update(tanks,bullets,maps,prizes,players,kills,names);
	}

	public ArrayList<BulletMulti> getBullets () {
		return bullets;
	}

}

