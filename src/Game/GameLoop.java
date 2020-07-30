package Game;

import Game.Server.ClientHandler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameLoop implements Runnable , Serializable
{

	/**
	 * Frame Per Second.
	 * Higher is better, but any value above 24 is fine.
	 */
	public static final int FPS = 54;

	private GameFrame canvas;
	private GameState state;
	private int players,tankStamina,canonPower, wallStamina;
	private ArrayList<ClientHandler> clientHandlers;

	public GameLoop(GameFrame frame , int players,
					int tankStamina, int canonPower, int wallStamina,
					ArrayList<ClientHandler> clientHandlers)
	{
		this.clientHandlers = clientHandlers;
		this.tankStamina = tankStamina;
		this.canonPower = canonPower;
		this.wallStamina = wallStamina;
		this.players = players;
		canvas = frame;
	}

	/**
	 * This must be called before the game loop starts.
	 */
	public void init()
	{
		state = new GameState(players,tankStamina,canonPower, wallStamina,clientHandlers);
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
				//
				state.update();
				canvas.render(state);
				ExecutorService pool = Executors.newCachedThreadPool ();
				for (ClientHandler clientHandler : clientHandlers)
					pool.execute (clientHandler);
				pool.shutdown ();
				gameOver = state.gameOver;
				//
				long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
				if (delay > 0)
					Thread.sleep(delay);
			}
			catch (InterruptedException | IOException ex)
			{
				ex.printStackTrace();
			}
		}

		try
		{
			canvas.render(state);
			ExecutorService pool = Executors.newCachedThreadPool ();
			for (ClientHandler clientHandler : clientHandlers)
				pool.execute (clientHandler);
			pool.shutdown ();
		}
		catch (IOException | InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
