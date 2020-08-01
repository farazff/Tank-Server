package MultiGame.Game;

import MultiGame.Server.ClientHandler;
import MultiGame.Status.GameStatus;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameLoopMulti implements Runnable , Serializable
{

	/**
	 * Frame Per Second.
	 * Higher is better, but any value above 24 is fine.
	 */
	public static final int FPS = 54;

	private GameFrameMulti canvas;
	private GameStateMulti state;
	private int players,tankStamina,canonPower, wallStamina;
	private ArrayList<ClientHandler> clientHandlers;

	public GameLoopMulti( int players,
						 int tankStamina, int canonPower, int wallStamina,
						 ArrayList<ClientHandler> clientHandlers)
	{
		this.clientHandlers = clientHandlers;
		this.tankStamina = tankStamina;
		this.canonPower = canonPower;
		this.wallStamina = wallStamina;
		this.players = players;
	}

	public GameStateMulti getState()
	{
		return state;
	}

	public void init()
	{
		state = new GameStateMulti(players,tankStamina,canonPower, wallStamina,clientHandlers);
	}

	@Override
	public void run()
	{
		int gameOver = 0;
		while(gameOver == 0)
		{
			try
			{

				long start = System.currentTimeMillis();
				state.update();

				ExecutorService pool = Executors.newCachedThreadPool();
				for(ClientHandler clientHandler : clientHandlers)
				{
					Thread test = new Thread(clientHandler);
					test.start();
					while(true)
					{
						if(!test.isAlive())
							break;
					}
				}
				pool.shutdown();

				try
				{
					while(!pool.isTerminated())
					{
						Thread.sleep(2);
					}
				}
				catch(InterruptedException e)
				{
					e.printStackTrace ();
				}

				gameOver = state.gameOver;

				long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
				if (delay > 0)
					Thread.sleep(delay);
			}
			catch (InterruptedException ex)
			{
				ex.printStackTrace();
			}
		}

		try
		{
			canvas.render(state);
		}
		catch (IOException | InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
