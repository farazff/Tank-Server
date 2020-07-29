package Game.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable
{
    private Socket connectionSocket;

    private SynchronizedArrayList data = new SynchronizedArrayList();

    public SynchronizedArrayList getData()
    {
        return data;
    }

    public ClientHandler(Socket connectionSocket)
    {
        this.connectionSocket = connectionSocket;
    }

    @Override
    public void run()
    {
        try
        {
            OutputStream out = connectionSocket.getOutputStream();
            InputStream in = connectionSocket.getInputStream();

            byte[] buffer1 = new byte[1];
            byte[] buffer2 = new byte[1];
            byte[] buffer3 = new byte[1];
            byte[] buffer4 = new byte[1];
            byte[] buffer5 = new byte[1];

            while(true)
            {
                int read1 = in.read(buffer1);
                int read2 = in.read(buffer2);
                int read3 = in.read(buffer3);
                int read4 = in.read(buffer4);
                int read5 = in.read(buffer5);

//                System.out.println("RECV from " + ": " + new String(buffer1, 0, read1)
//                       +" " + new String(buffer2, 0, read2) + " " + new String(buffer3, 0, read3)
//                 + " " + new String(buffer4, 0, read4) + " " +
//                 new String(buffer5, 0, read5));

                data.add(new String(buffer1, 0, read1));
                data.add(new String(buffer2, 0, read2));
                data.add(new String(buffer3, 0, read3));
                data.add(new String(buffer4, 0, read4));
                data.add(new String(buffer5, 0, read5));
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
