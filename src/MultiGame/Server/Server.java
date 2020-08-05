package MultiGame.Server;

import GameData.GameFinishType;
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

            int type = 5;
            if(multiGame.getGameFinishType() == GameFinishType.DEATH_MATCH)
                type = 1;

            GameLoopMulti game = new GameLoopMulti( multiGame.getNumberOfPlayers (),
                    multiGame.getTankStamina (),multiGame.getCanonPower ()
                    ,multiGame.getWallStamina (),clientHandlers,type);
            System.out.println("MultiGame.Server started with port " + port);
            for(int i=1;i<=multiGame.getNumberOfPlayers ();i++)
            {
                Socket connectionSocket = welcomingSocket.accept();
                System.out.println("client accepted! with port " + port);
                ClientHandler clientHandler = new ClientHandler(connectionSocket,game);
                clientHandlers.add(clientHandler);
                multiGame.addUser ();
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
