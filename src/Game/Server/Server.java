package Game.Server;

import Game.GameFrame;
import Game.GameLoop;
import Game.ThreadPool;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Server
{

    private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    public static void main(String[] args)
    {
        final GameFrame frame = new GameFrame("Simple Ball !");
        ExecutorService pool = Executors.newCachedThreadPool();
        try(ServerSocket welcomingSocket = new ServerSocket(8080))
        {
            int players = 0;
            System.out.print("Server started.\nWaiting for a client ... ");
            for(int i=1;i<=1;i++)
            {
                Socket connectionSocket = welcomingSocket.accept();
                System.out.println("client accepted!");
                ClientHandler clientHandler = new ClientHandler(connectionSocket,frame);
                players++;
                clientHandlers.add(clientHandler);
                pool.execute(clientHandler);
            }

            int finalPlayers = players;

            ThreadPool.init();
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {

                    frame.setLocationRelativeTo(null);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                    frame.initBufferStrategy();

                    GameLoop game = new GameLoop(frame, finalPlayers,
                            100,100,100,clientHandlers);
                    game.init();
                    ThreadPool.execute(game);
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
