package MultiGame.Server;

import MultiGame.Game.GameLoopMulti;
import MultiGame.Status.GameStatus;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable
{
    private ArrayList<Character> data = new ArrayList<>();
    private GameLoopMulti game;
    private boolean wait;

    public ArrayList<Character> getData()
    {
        return data;
    }
    public ObjectOutputStream outputStream = null;
    public InputStream inputStream = null;

    public ClientHandler(Socket connectionSocket , GameLoopMulti game)
    {
        wait = false;
        try
        {
            inputStream = connectionSocket.getInputStream();
            outputStream = new ObjectOutputStream(connectionSocket.getOutputStream());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        this.game = game;
    }

    @Override
    public void run()
    {
        wait = true;
        try
        {
            byte[] buff = new byte[6];
            int l = inputStream.read(buff);

            String temp = new String(buff,0,l);
            System.out.println("*" + temp);

            data.clear();
            data.add(temp.charAt(0));
            data.add(temp.charAt(1));
            data.add(temp.charAt(2));
            data.add(temp.charAt(3));
            data.add(temp.charAt(4));

            GameStatus status = game.getState().getStatus();
            outputStream.reset();
            System.out.println("going to send object");
            outputStream.writeObject(status);
            System.out.println("Done sending");
        }
        catch (IllegalArgumentException | IOException e)
        {
            e.printStackTrace();
        }
        wait = false;
    }

    public boolean isWait () {
        return wait;
    }
}
