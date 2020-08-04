package MultiGame.Server;


import GameData.MultiGame;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;



public class ServerHandler implements Runnable
{
    private Socket socket;
    private int id;

    public ServerHandler (Socket socket, int id)
    {
        this.socket = socket;
        this.id = id;
    }

    @Override
    public void run () {

        ObjectInputStream in = null;
        DataOutputStream out = null;
        try {

            in = new ObjectInputStream (socket.getInputStream ());
            MultiGame multiGame = (MultiGame) in.readObject ();
            Server server = new Server (multiGame,7070 + id);
            new Thread (server).start ();

            out = new DataOutputStream (socket.getOutputStream ());
            out.writeInt ((7070 + id));
            out.flush ();

        } catch (ClassNotFoundException e) {
            System.out.println ("Some thing went wrong in reading objects from server");
        } catch (SocketException e)
        {
            System.err.println ("Client " + id + " 's connection Terminated");
        } catch (IOException e)
        {
            System.err.println ("Some thing went wrong with Client " + id);
        } finally
        {
            try {
                if (in != null)
                    in.close ();
            }
            catch (SocketException ignore)
            {
            }
            catch (IOException e)
            {
                System.err.println ("Some thing went wrong in closing ServerInputStream" +
                        " in Client " + id);
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
                System.err.println ("Some thing went wrong in closing ServerOutputStream" +
                        " in Client " + id);
            }
            try {
                socket.close ();
                System.out.println ("Client " + id + " closed");
            }
            catch (SocketException ignore)
            {
            }
            catch (IOException e)
            {
                System.err.println ("Some thing went wrong in closing client " + id +
                        " connection");
            }
        }
    }
}
