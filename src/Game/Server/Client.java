package Game.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client
{

    public static void main(String[] args)
    {
        try(Socket client = new Socket("127.0.0.1", 8080))
        {
            System.out.println("Connected to server.");
            OutputStream out = client.getOutputStream();
            InputStream in = client.getInputStream();
            byte[] buffer = new byte[2048];
            while (true)
            {
                Scanner scanner = new Scanner(System.in);
                out.write(scanner.next().getBytes());
                out.write(scanner.next().getBytes());
                out.write(scanner.next().getBytes());
                out.write(scanner.next().getBytes());
                out.write(scanner.next().getBytes());
//                for(int i=1;i<=10;i++)
//                {
//                    out.write("1".getBytes());
//                    out.write("2".getBytes());
//                    out.write("3".getBytes());
//                    out.write("4".getBytes());
//                    out.write("5".getBytes());
//                }
            }
        }
        catch (IOException ex)
        {
            System.err.println(ex);
        }
    }
}
