package Game.Graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MapGenerator
{
   public MapGenerator()
    {
        try
        {
            FileWriter writer = new FileWriter(new File("Files/Maps/map1.txt"));

            Random random = new Random();

            int x = (random.nextInt(4)+3) *2 + 1;
            int y = (random.nextInt(4)+3) *2 + 1;

            for(int i=1;i<=x;i++)
                writer.write("1");
           writer.write("\n");

            for(int j=2;j<=y-1;j++)
            {
                writer.write("1");
                if(j%2==0)
                {
                    for(int i=2;i<=x-1;i++)
                    {
                        if(i%2 == 0)
                          writer.write("0");
                        else
                            writer.write(String.valueOf(random.nextInt(2)+1));
                    }
                }
                else
                {
                    for(int i=2;i<=x-1;i++)
                    {
                        if(i%2 == 1)
                            writer.write(String.valueOf(random.nextInt(2)+1));
                        else
                            writer.write(String.valueOf(random.nextInt(2)));
                    }
                }

                writer.write("1");
                writer.write("\n");
            }

            for(int i=1;i<=x;i++)
               writer.write("1");

            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }
}