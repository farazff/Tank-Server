package MultiGame.Server;

import MultiGame.Status.GameStatus;

import java.io.*;
import java.net.Socket;

public class Client
{

    public static void main(String[] args)
    {
        try(Socket client = new Socket("127.0.0.1", 8080))
        {

            System.out.println("Connected to server.");

            OutputStream outputStream = client.getOutputStream();

            ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());

            while(true)
            {
                outputStream.write("00100".getBytes());
                System.out.println("received");
                GameStatus status = (GameStatus) objectInputStream.readObject();
                System.out.println(status.getTanks().get(0).getDegree());
            }

        }
        catch (IOException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
    }
}
