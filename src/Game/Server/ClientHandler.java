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
    private ArrayList<String> data = new ArrayList<>();
    private GameFrame frame;

    public ArrayList<String> getData()
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
        try
        {
            InputStream inputStream = connectionSocket.getInputStream();
            OutputStream outputStream = connectionSocket.getOutputStream();

            byte[] buffer = new byte[100];

            try
            {
                Thread.sleep(3000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            while(true)
            {
                int l = inputStream.read(buffer);
                String temp = new String(buffer,0,l);

                System.out.println("server got number");

                data.clear();
                data.add(String.valueOf(temp.charAt(0)));
                data.add(String.valueOf(temp.charAt(1)));
                data.add(String.valueOf(temp.charAt(2)));
                data.add(String.valueOf(temp.charAt(3)));
                data.add(String.valueOf(temp.charAt(4)));

                System.out.println("server going to send image");
                BufferedImage img = frame.getFinalImg().get();

                ImageIO.write(img, "jpg", outputStream);
                System.out.println("server sent image");

            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        finally
        {
            try
            {
                connectionSocket.close();
            }
            catch (IOException ex)
            {
                System.err.println(ex);
            }
        }
    }
}
