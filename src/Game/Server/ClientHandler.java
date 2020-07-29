package Game.Server;

import Game.GameFrame;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable
{

    private Socket connectionSocket;
    private ArrayList<String> data = new ArrayList<>();
    private GameFrame frame;

    public ArrayList<String> getData()
    {
        return data;
    }

    public ClientHandler(Socket connectionSocket , GameFrame frame)
    {
        this.connectionSocket = connectionSocket;
        this.frame = frame;
    }

    @Override
    public void run()
    {
        try
        {
            OutputStream out = connectionSocket.getOutputStream();
            InputStream in = connectionSocket.getInputStream();

            byte[] buffer1 = new byte[5];


            while(true)
            {
                int read1 = in.read(buffer1);

                //System.out.println(new String(buffer1,0,read1));

                data.clear();
                data.add(String.valueOf(new String(buffer1, 0, read1).charAt(0)));
                data.add(String.valueOf(new String(buffer1, 0, read1).charAt(1)));
                data.add(String.valueOf(new String(buffer1, 0, read1).charAt(2)));
                data.add(String.valueOf(new String(buffer1, 0, read1).charAt(3)));
                data.add(String.valueOf(new String(buffer1, 0, read1).charAt(4)));

            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        finally
        {
            try
            {
                connectionSocket.close();
            }
            catch (IOException ex)
            {
                System.err.println(ex);
            }
        }
    }
}
