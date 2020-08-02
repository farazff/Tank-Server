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
		boolean gameOver = false;
		int prizeTime = 1;

		while(!gameOver)
		{
			state.getStatus().setNewPrize(false);
			state.getStatus().setUsePrize(false);
			state.getStatus().setShot(false);
			state.getStatus().setExplode(false);

			try
			{
				prizeTime++;
				if(prizeTime%75==0)
				{
					state.getStatus().setNewPrize(true);
					state.addPrize();
				}
				if(prizeTime%190==0)
				{
					state.getPrizes().getPrizes().get((prizeTime/190)-1).deActive();
				}

				long start = System.currentTimeMillis();
				state.update();

				System.out.println(state.getStatus().isGameOver());

				for(ClientHandler clientHandler : clientHandlers)
				{
					Thread test = new Thread(clientHandler);
					test.start();
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

						if(!clientHandler.isWait ())
							break;
					}
				}

				gameOver = state.getStatus().isGameOver();

				long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
				if (delay > 0)
					Thread.sleep(delay);
			}
			catch (InterruptedException ex)
			{
				ex.printStackTrace();
			}
		}

		for(ClientHandler clientHandler : clientHandlers)
		{
			Thread test = new Thread(clientHandler);
			test.start();
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

				if(!clientHandler.isWait ())
					break;
			}
		}


	}
}
