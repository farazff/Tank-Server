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
	private int t;
	int[] kills;
	ArrayList<String> names;

	/**
	 * create a game loop
	 * @param players players
	 * @param tankStamina tankStamina
	 * @param canonPower canonPower
	 * @param wallStamina wallStamina
	 * @param clientHandlers  clientHandlers
	 * @param t time
	 */
	public GameLoopMulti( int players,
						  int tankStamina, int canonPower, int wallStamina,
						  ArrayList<ClientHandler> clientHandlers , int t)
	{
		kills = new int[players];
		this.t = t;
		this.clientHandlers = clientHandlers;
		this.tankStamina = tankStamina;
		this.canonPower = canonPower;
		this.wallStamina = wallStamina;
		this.players = players;
	}

	/**
	 *
	 * @return get State
	 */
	public GameStateMulti getState()
	{
		return state;
	}

	/**
	 * This must be called before the game loop starts.
	 */
	public void init()
	{
		state = new GameStateMulti(players,tankStamina,canonPower, wallStamina,clientHandlers,kills,names);
	}

	@Override
	public void run()
	{
		for(int i=1;i<=t;i++)
		{
			if(i!=1)
				init();

			names.clear();
			for(ClientHandler clientHandler : clientHandlers)
			{
				names.add(clientHandler.getUser().getUserName());
			}

			boolean gameOver = false;
			int prizeTime = 1;

			while (!gameOver)
			{
				state.getStatus().setNewPrize(false);
				state.getStatus().setUsePrize(false);
				state.getStatus().setShot(false);
				state.getStatus().setExplode(false);

				try
				{
					prizeTime++;
					if(prizeTime % 75 == 0)
					{
						state.getStatus().setNewPrize(true);
						state.addPrize();
					}
					if(prizeTime % 190 == 0)
					{
						state.getPrizes().getPrizes().get((prizeTime / 190) - 1).deActive();
					}

					long start = System.currentTimeMillis();
					state.update();

					for(ClientHandler clientHandler : clientHandlers)
					{
						if(clientHandler.isActive())
						{
							Thread test = new Thread(clientHandler);
							test.start();
							while(true)
							{
								try
								{
									Thread.sleep(9);
								}
								catch(InterruptedException e)
								{
									e.printStackTrace();
								}

								if(!clientHandler.isWait())
									break;
							}
						}
					}

					gameOver = state.getStatus().isGameOver();

					long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
					if(delay > 0)
						Thread.sleep(delay);
				}
				catch(InterruptedException ex)
				{
					ex.printStackTrace();
				}
			}

			state.update();
			for (ClientHandler clientHandler : clientHandlers)
			{
				Thread test = new Thread(clientHandler);
				test.start();
				while(true)
				{
					try
					{
						Thread.sleep(10);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}

					if(!clientHandler.isWait())
						break;
				}
			}

			try
			{
				Thread.sleep(4000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}


		}

		state.getStatus().setGameOverAll(true);
		for (ClientHandler clientHandler : clientHandlers)
		{
			Thread test = new Thread(clientHandler);
			test.start();
			while(true)
			{
				try
				{
					Thread.sleep(10);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}

				if(!clientHandler.isWait())
					break;
			}
		}


	}
}
