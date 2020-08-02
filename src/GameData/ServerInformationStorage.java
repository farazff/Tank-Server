package GameData;

import java.io.Serializable;
import java.util.ArrayList;

public class ServerInformationStorage implements Serializable
{
    private ArrayList<ServerInformation> serverData;
    private boolean iterate;

    public ServerInformationStorage ()
    {
        this.serverData = new ArrayList<> ();
        iterate = true;
    }

    public void setServerData (ArrayList<ServerInformation> serverData) {
        this.serverData = serverData;
    }

    public void setIterate (boolean iterate) {
        this.iterate = iterate;
    }

    public ArrayList<ServerInformation> getServerData () {
        return serverData;
    }

    public void addNewServer (ServerInformation serverInformation)
    {
        try {
            while (!iterate)
            {
                Thread.sleep (250);
            }
        }catch (InterruptedException e)
        {
            e.printStackTrace ();
        }
        finally {
            this.serverData.add (serverInformation);
        }
    }

    public void removeServer (ServerInformation serverInformation)
    {
        this.serverData.remove (serverInformation);
    }
}
