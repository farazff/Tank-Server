package GameData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * this is the server's Storage
 */
public class ServerInformationStorage implements Serializable
{
    private ArrayList<ServerInformation> serverData;
    private boolean iterate;

    /**
     * creates new server information
     */
    public ServerInformationStorage ()
    {
        this.serverData = new ArrayList<> ();
        iterate = true;
    }

    /**
     * set server Data
     * @param serverData serverData
     */
    public void setServerData (ArrayList<ServerInformation> serverData) {
        this.serverData = serverData;
    }

    /**
     * set Iterate
     * @param iterate iterate
     */
    public void setIterate (boolean iterate) {
        this.iterate = iterate;
    }

    /**
     *
     * @return server's data
     */
    public ArrayList<ServerInformation> getServerData () {
        return serverData;
    }

    /**
     * add new Server
     * @param serverInformation serverInformation
     */
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

    /**
     * remove a server
     * @param serverInformation serverInformation
     */
    public void removeServer (ServerInformation serverInformation)
    {
        this.serverData.remove (serverInformation);
    }
}
