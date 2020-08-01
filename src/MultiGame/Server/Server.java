package MultiGame.Server;

import MultiGame.Game.*;


import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.*;
import java.util.ArrayList;

public class Server
{

    private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    public static void main(String[] args)
    {
        try(ServerSocket welcomingSocket = new ServerSocket(8080))
        {
            int players = 2;
            GameLoopMulti game = new GameLoopMulti( players,
                    100,100,100,clientHandlers);
            System.out.print("MultiGame.Server started.\nWaiting for a client ... ");
            for(int i=1;i<=2;i++)
            {
                Socket connectionSocket = welcomingSocket.accept();
                System.out.println("client accepted!");
                ClientHandler clientHandler = new ClientHandler(connectionSocket,game);
                players++;
                clientHandlers.add(clientHandler);
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
