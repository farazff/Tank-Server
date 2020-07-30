package Game.Server;

import Game.GameFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ClientHandler implements Runnable
{
    private Socket connectionSocket;
    private char[] data = new char[5];
    private GameFrame frame;
    private DataInputStream in = null;
    private ObjectOutputStream out = null;

    public char[] getData()
    {
        return data;
    }

    public ClientHandler(Socket connectionSocket , GameFrame frame)
    {
        this.connectionSocket = connectionSocket;
        try {
            in = new DataInputStream (connectionSocket.getInputStream ());
            out = new ObjectOutputStream (connectionSocket.getOutputStream ());
        }catch (IllegalArgumentException e)
        {
            System.err.println ("Some went Wrong in start");
        }
        catch (ConnectException e)
        {
            System.err.println ("Couldn't connect to Server");
        }
        catch (SocketException e)
        {
            System.err.println ("Server Not Responding42");
        } catch (IOException e)
        {
            System.err.println ("Some went Wrong");
        }
        this.frame = frame;
    }

    @Override
    public void run()
    {
        try
        {
            String temp = in.readUTF ();
            System.out.println("server got number");

            data = new char[5];
            data = temp.toCharArray ();


            System.out.println("server going to send image");
            BufferedImage img = frame.getFinalImg().get();

            out.writeObject (new ImageIcon (img));
            out.flush ();
            System.out.println("server sent image");
        }catch (IllegalArgumentException e)
        {
            System.err.println ("Some went Wrong in start");
        }
        catch (ConnectException e)
        {
            System.err.println ("Couldn't connect to Server");
        }
        catch (SocketException e)
        {
            System.err.println ("Server Not Responding77");
        } catch (IOException e)
        {
            System.err.println ("Some went Wrong");
        }
    }

    public void close ()
    {
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
            connectionSocket.close ();
        }
        catch (ConnectException e)
        {
            System.err.println ("Couldn't connect to Server");
        }
        catch (SocketException e)
        {
            System.err.println ("Server Not Responding119");
        } catch (IOException e)
        {
            System.err.println ("Some went Wrong");
        }
    }
}
