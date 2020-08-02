package MultiGame.Server;

import GameData.User;
import MultiGame.Game.GameLoopMulti;
import MultiGame.Status.GameStatus;
import MultiGame.TransferData;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable
{
    private ArrayList<Character> data = new ArrayList<>();
    private GameLoopMulti game;
    private boolean wait;
    private User user;

    public ArrayList<Character> getData()
    {
        return data;
    }

    public User getUser () {
        return user;
    }

    public ObjectOutputStream outputStream = null;
    public ObjectInputStream inputStream = null;

    public ClientHandler(Socket connectionSocket , GameLoopMulti game)
    {
        wait = false;
        try
        {
            inputStream = new ObjectInputStream (connectionSocket.getInputStream());
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
            TransferData transferData = (TransferData) inputStream.readObject ();
            String temp = transferData.getCommand ();
            System.out.println("*" + temp);
            user = transferData.getUser ();
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
        catch (IllegalArgumentException | IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        wait = false;
    }

    public boolean isWait () {
        return wait;
    }
}
