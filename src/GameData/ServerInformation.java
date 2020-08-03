package GameData;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerInformation implements Serializable
{
    private final char[] password;
    private final String url;
    private static final int MAX_CAPACITY = 100;
    private int currentCapacity;
    private int numOfActiveGames;
    private final HashMap<Integer,MultiGame> multiGames;

    public ServerInformation (String url, char[] password)
    {
        this.url = url;
        this.password = password;

        this.multiGames = new HashMap<> ();



        currentCapacity = MAX_CAPACITY - numOfActiveGames;
    }

    public char[] getPassword () {
        return password;
    }

    public int getNumOfActiveGames () {
        return numOfActiveGames;
    }

    public int getCurrentCapacity () {
        return currentCapacity;
    }

    public String getUrl () {
        return url;
    }

    public void addGame (MultiGame multiGame)
    {
        if (multiGame == null)
            return;
        new Thread (new Runnable () {
            @Override
            public void run () {
                DataInputStream in = null;
                ObjectOutputStream out = null;
                try (Socket socket = new Socket ("127.0.0.1",9093)){
                    out = new ObjectOutputStream (socket.getOutputStream ());
                    out.writeObject (multiGame);
                    out.flush ();

                    in = new DataInputStream (socket.getInputStream ());
                    int res = in.readInt ();
                    multiGames.put (res,multiGame);
                    numOfActiveGames = multiGames.size ();
                } catch (IllegalArgumentException e)
                {
                    System.err.println ("Some went Wrong in start");
                }
                catch (ConnectException e)
                {
                    System.err.println ("Couldn't connect to ServerInformation");
                }
                catch (SocketException e)
                {
                    System.err.println ("ServerInformation Not Responding");
                } catch (IOException e)
                {
                    System.err.println ("Some went Wrong");
                } finally {
                    try {
                        if (in != null)
                            in.close ();
                    }
                    catch (SocketException ignore)
                    {
                    }
                    catch (IOException e)
                    {
                        System.err.println ("Some thing went wrong in closing ServerInputStream");
                    }
                    try {
                        if (out != null)
                            out.close ();
                    }
                    catch (SocketException ignore)
                    {
                    }
                    catch (IOException e)
                    {
                        System.err.println ("Some thing went wrong in closing ServerOutputStream");
                    }
                }
            }
        }).start ();
    }

    public void removeMultiGame (int index)
    {
        try {
            multiGames.remove (index);
            numOfActiveGames = multiGames.size ();
        } catch (IndexOutOfBoundsException e)
        {
            System.out.println ("Some Thing went Wrong in removing indexed multi game");
        }
    }

    public ArrayList<MultiGame> getMultiGamesValue () {
        return new ArrayList<> (multiGames.values ());
    }

    public MultiGame getMultiGame (int index)
    {
        try {
            return multiGames.get (index);
        } catch (IndexOutOfBoundsException e)
        {
            System.out.println ("Some Thing went Wrong in getting indexed multi game");
            return null;
        }
    }

    public HashMap<Integer, MultiGame> getMultiGames () {
        return multiGames;
    }
}
