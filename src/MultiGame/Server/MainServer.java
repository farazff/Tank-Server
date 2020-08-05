package MultiGame.Server;

import MultiGame.Game.GameLoopMulti;
import MultiGame.Game.ThreadPoolMulti;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * the main server of all multiPlayer games
 */

public class MainServer
{


    public static void main(String[] args)
    {
        int i = 0;
        ExecutorService pool = Executors.newCachedThreadPool ();
        try(ServerSocket welcomingSocket = new ServerSocket(9093))
        {
            System.out.println ("Wait for client ....");
            while (true)
            {
                pool.execute (new ServerHandler (welcomingSocket.accept (),i));
                i++;
            }
        }

        catch (IOException ex)
        {
            ex.printStackTrace ();
        }

        System.out.println("done.");
    }
}
