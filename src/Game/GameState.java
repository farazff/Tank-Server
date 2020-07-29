package Game;

import Game.Server.ClientHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameState
{

	private ArrayList<Tank> tanks;
	private InteractArrayList<Bullet> bullets;
	public int gameOver,players;
	private Maps maps;
	private Prizes prizes;
	private Thread t1;
	private ArrayList<ClientHandler> clientHandlers;

	public GameState(int players,int tankStamina,int canonPower,int wallStamina,
					 ArrayList<ClientHandler> clientHandlers)
	{
		this.clientHandlers = clientHandlers;
		this.players = players;
		maps = new Maps(wallStamina);
		bullets = new InteractArrayList<> ();
		tanks = new ArrayList<> ();
		prizes = new Prizes(maps,tanks);

		for(int i=1;i<=players;i++)
		{
			Tank tank1 = new Tank(bullets, maps.getWalls(), tanks, prizes,
					tankStamina, canonPower, maps,clientHandlers.get(i-1).getData());
			tanks.add(tank1);
		}

		gameOver = 0;
		t1 = new Thread(prizes);
		t1.start();
	}

	public Prizes getPrizes()
	{
		return prizes;
	}

	public ArrayList<Tank> getTanks ()
	{
		return tanks;
	}

	public Maps getMaps()
	{
		return maps;
	}

	/**
	 * The method which updates the game state.
	 */
	public void update()
	{

		ExecutorService executorService = Executors.newCachedThreadPool();

		Iterator<Bullet> bulletIterator = bullets.iterator();
		bullets.setIterate(true);
		while(bulletIterator.hasNext())
		{
			Bullet bullet = bulletIterator.next ();
			if (bullet.hasExpired())
				bulletIterator.remove();
			else
				executorService.execute(bullet);
		}
		bullets.setIterate(false);


		Iterator<Tank> tankIterator = tanks.iterator();
		while(tankIterator.hasNext())
		{
			Tank tank = tankIterator.next();

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
				Thread.sleep(1);
			}
		}
		catch(InterruptedException e)
		{
			e.printStackTrace ();
		}
	}

	public ArrayList<Bullet> getBullets () {
		return bullets;
	}

}

