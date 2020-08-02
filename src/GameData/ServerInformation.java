package GameData;

import java.io.Serializable;
import java.util.ArrayList;

public class ServerInformation implements Serializable
{
    private final char[] password;
    private final String url;
    private static final int MAX_CAPACITY = 100;
    private int currentCapacity;
    private int numOfActiveGames;
    private final ArrayList<MultiGame> multiGames;

    public ServerInformation (String url, ArrayList<MultiGame> multiGames, char[] password)
    {
        this.url = url;
        this.password = password;
        if (multiGames != null)
            this.multiGames = new ArrayList<> (multiGames);
        else
            this.multiGames = new ArrayList<> ();

        if (multiGames == null)
            numOfActiveGames = 0;
        else
            numOfActiveGames = multiGames.size ();

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

    public void addGame (MultiGame MultiGame)
    {
        multiGames.add (MultiGame);
        numOfActiveGames = multiGames.size ();
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

    public ArrayList<MultiGame> getMultiGames () {
        return multiGames;
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
}
