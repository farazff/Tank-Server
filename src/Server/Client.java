package Server;

import Game.*;
import Game.GameStateMulti;

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

            OutputStream outputStream = client.getOutputStream();


            ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());
            while(true)
            {
                outputStream.write("00100".getBytes());
                GameStateMulti state = (GameStateMulti) objectInputStream.readObject();
                System.out.println(state.getTanks().size());
            }

        }
        catch (IOException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
    }
}
