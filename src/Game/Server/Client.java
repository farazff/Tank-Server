package Game.Server;

import Game.GameFrame;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client
{

    public static void main(String[] args)
    {
        try(Socket client = new Socket("127.0.0.1", 8080))
        {
            System.out.println("Connected to server.");

            //InputStream inputStream = client.getInputStream();
            //OutputStream outputStream = client.getOutputStream();
            //ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());

            byte[] buffer = new byte[2048];
            while(true)
            {
                objectOutputStream.writeObject(new Test(677));
            }
        }
        catch (IOException ex)
        {
            System.err.println(ex);
        }
    }
}
