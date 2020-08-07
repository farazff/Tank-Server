package MultiGame.Server;

import GameData.User;
import MultiGame.Game.GameLoopMulti;
import MultiGame.Status.GameStatus;
import MultiGame.Status.NullStatus;
import MultiGame.TransferData;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * this class handles client for multi games (User)
 */
public class ClientHandler implements Runnable
{
    boolean active;
    private ArrayList<Character> data = new ArrayList<>();
    private GameLoopMulti game;
    private boolean wait;
    private User user;

    /**
     *
     * @return list of data
     */
    public ArrayList<Character> getData()
    {
        return data;
    }

    /**
     *
     * @return user
     */
    public User getUser ()
    {
        return user;
    }


    public ObjectOutputStream outputStream = null;
    public ObjectInputStream inputStream = null;

    /**
     * creates new Client handler
     * @param connectionSocket connectionSocket
     * @param game game
     */
    public ClientHandler(Socket connectionSocket , GameLoopMulti game)
    {
        active = true;
        wait = true;
        try
        {
            inputStream = new ObjectInputStream (connectionSocket.getInputStream());
            outputStream = new ObjectOutputStream(connectionSocket.getOutputStream());

            TransferData transferData = (TransferData) inputStream.readObject ();
            user = transferData.getUser ();
            outputStream.reset();
            outputStream.writeObject(new NullStatus ());

        }
        catch (IllegalArgumentException | IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        wait = false;
        this.game = game;
    }

    @Override
    public void run()
    {
        wait = true;
        try
        {
            TransferData transferData = (TransferData) inputStream.readObject ();
            String temp = transferData.getCommand ();
            user = transferData.getUser ();
            data.clear();
            data.add(temp.charAt(0));
            data.add(temp.charAt(1));
            data.add(temp.charAt(2));
            data.add(temp.charAt(3));
            data.add(temp.charAt(4));

            GameStatus status = game.getState().getStatus();
            outputStream.reset();
            outputStream.writeObject(status);
        }
        catch (SocketException e)
        {
            active = false;
        }
        catch(IllegalArgumentException | IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        wait = false;
    }

    /**
     *
     * @return is wait
     */
    public boolean isWait ()
    {
        return wait;
    }

    /**
     *
     * @return is Active
     */
    public boolean isActive()
    {
        return active;
    }

    /**
     * sets Active
     * @param active active
     */
    public void setActive(boolean active)
    {
        this.active = active;
    }

}
