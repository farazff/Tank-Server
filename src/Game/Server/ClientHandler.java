package Game.Server;

import Game.GameFrame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable
{
    private Socket connectionSocket;
    private char[] data = new char[5];
    private GameFrame frame;

    public char[] getData()
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

        try (DataInputStream inputStream = new DataInputStream (connectionSocket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream (connectionSocket.getOutputStream()))
        {


            Thread.sleep(3000);


            while(true)
            {

                String temp = inputStream.readUTF ();
                System.out.println("server got number");

                data = new char[5];
                data = temp.toCharArray ();


                System.out.println("server going to send image");
                BufferedImage img = frame.getFinalImg().get();

                ImageIO.write(img, "jpg", outputStream);
                System.out.println("server sent image");

            }

        } catch (InterruptedException | IOException e)
        {
            e.printStackTrace ();
        } finally
        {
            try
            {
                connectionSocket.close();
            }
            catch (IOException ex)
            {
                System.err.println(ex.getMessage ());
            }
        }
    }


}
