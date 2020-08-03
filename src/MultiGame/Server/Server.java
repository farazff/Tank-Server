package MultiGame.Server;

import GameData.MultiGame;
import MultiGame.Game.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.*;
import java.util.ArrayList;

public class Server implements Runnable
{

    private ArrayList<ClientHandler> clientHandlers;
    private MultiGame multiGame;
    private int port;
    public Server (MultiGame multiGame, int port)
    {
        clientHandlers = new ArrayList<> ();
        this.multiGame = multiGame;
        this.port = port;
    }


    @Override
    public void run ()
    {
        try(ServerSocket welcomingSocket = new ServerSocket(port))
        {

            GameLoopMulti game = new GameLoopMulti( multiGame.getNumberOfPlayers (),
                    multiGame.getTankStamina (),multiGame.getCanonPower ()
                    ,multiGame.getWallStamina (),clientHandlers);
            System.out.println("MultiGame.Server started.\nWaiting for a client ... ");
            for(int i=1;i<=multiGame.getNumberOfPlayers ();i++)
            {
                Socket connectionSocket = welcomingSocket.accept();
                System.out.println("client accepted!");
                ClientHandler clientHandler = new ClientHandler(connectionSocket,game);
                clientHandlers.add(clientHandler);
                multiGame.addUser (clientHandler.getUser ());
            }

            ThreadPoolMulti.init();
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    game.init();
                    ThreadPoolMulti.execute(game);
                }
            }).start();
        }

        catch (IOException ex)
        {
            ex.printStackTrace ();
        }

        System.out.println("done.");
    }

}
