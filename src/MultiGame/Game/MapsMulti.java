package MultiGame.Game;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import MultiGame.Game.GraphMulti.*;

/**
 * this class handles map creating
 */
public class MapsMulti implements Serializable
{
    private ArrayList<WallMulti> walls;   ////ok to serialize
    private ArrayList< ArrayList<Character> > data = new ArrayList<>();  ////ok to serialize

    private boolean[][] isOk = new boolean[30][30];  ////ok to serialize

    private int x = 0;  ////ok to serialize
    private int y = 0;  ////ok to serialize
    private int horizontal = 0; //ofoqi  ////ok to serialize
    private int vertical = 0; //amodi  ////ok to serialize
    private int houseX;  ////ok to serialize
    private int houseY;  ////ok to serialize
    private int wallStamina;  ////ok to serialize

    /**
     * get the walls of the map
     * @return walls
     */
    public ArrayList<WallMulti> getWalls()
    {
        return walls;
    }

    /**
     * constructor of the Map class
     * @param wallStamina the stamina of the walls
     */
    public MapsMulti(int wallStamina)
    {
        this.wallStamina = wallStamina;
        walls = new ArrayList<>();
        readFromFile();
        x = data.get(0).size();


        if(x%2==1)
            houseX = (x-1)/2;
        else
            houseX = x/2;

        horizontal = (16*720/9)/(houseX);


        if(y%2==1)
            houseY = (y-1)/2;
        else
            houseY = y/2;

        vertical = (720)/(houseY);

        createWalls();
        createGraph();

    }

    /**
     * can put
     * @param x x
     * @param y y
     * @return result
     */
    public boolean canPut(int x,int y)
    {
        int xx = 0 , yy = 0;

        xx = (int) (Math.floor(x/horizontal) + 1);
        yy = (int) (Math.floor(y/vertical) + 1);
        return isOk[yy][xx];
    }


    /**
     * this class converts map to graph
     */
    public void createGraph()
    {
        int vertexNum = (data.size()-1)/2 * (data.get(0).size()-1)/2;
        GraphMulti graph = new GraphMulti(vertexNum + 1);
        for(int j=1;j<data.size();j+=2)
        {
            for(int i=1;i<data.get(0).size();i+=2)
            {
                int thisNum = ((data.get(0).size()-1)/2)*((j-1)/2) + (i+1)/2;
                int findNum = 0;
                int t=0;

                //System.out.println(thisNum + "->");

                if(data.get(j-1).get(i)=='0' || data.get(j-1).get(i)=='2')
                {
                    findNum = thisNum-(data.get(0).size()-1)/2;
                    graph.addEdge(thisNum,findNum);
                    t++;
                }

                if(data.get(j+1).get(i)=='0' || data.get(j+1).get(i)=='2')
                {
                    findNum = thisNum+(data.get(0).size()-1)/2;
                    graph.addEdge(thisNum,findNum);
                    t++;
                }

                if(data.get(j).get(i-1)=='0' || data.get(j).get(i-1)=='2')
                {
                    findNum = thisNum-1;
                    graph.addEdge(thisNum,findNum);
                    t++;
                }

                if(data.get(j).get(i+1)=='0' || data.get(j).get(i+1)=='2')
                {
                    findNum = thisNum + 1;
                    graph.addEdge(thisNum,findNum);
                    t++;
                }
            }
        }

        int m = 0 ;
        int flag = 0;
        for(int i=1;i<=vertexNum;i++)
        {
            if(graph.BFS(i).size() > m)
            {
                m = graph.BFS(i).size();
                flag = i;
            }
        }

        for(int i=0;i<graph.BFS(flag).size();i++)
        {
            int w=0 , h=0;
            int node = graph.BFS(flag).get(i);
            //System.out.print(node + " ");

            while(node>=(data.get(0).size()-1)/2)
            {
                node -= (data.get(0).size()-1)/2;
                w++;
            }

            //System.out.print(node);
            //System.out.println();

            if(node > 0)
            {
                isOk[w+1][node] = true;
            }
            if(node == 0)
            {
                isOk[w][(data.get(0).size()-1)/2] = true;
            }
        }

//        for(int i=1;i<=(data.size()-1)/2;i++)
//        {
//            for(int j=1;j<=(data.get(0).size()-1)/2;j++)
//            {
//                System.out.print(isOk[i][j] + " ");
//            }
//            System.out.println();
//        }

    }

    /**
     * create the walls
     */
    public void createWalls()
    {
        for(int j=0;j<data.size();j++)
        {
            if(j%2==0)
            {
                for(int i=1;i<x;i+=2)
                {
                    if(data.get(j).get(i) == '1')
                    {
                        walls.add(new WallMulti(horizontal * (i - 1)/2, vertical * (j)/2, horizontal, "H",
                                false,wallStamina));
                    }
                    if(data.get(j).get(i) == '2')
                    {
                        walls.add(new WallMulti(horizontal * (i - 1)/2, vertical * (j)/2, horizontal, "H",
                                true,wallStamina));
                    }
                }
            }
            if(j%2==1)
            {
                for(int i=0;i<x;i+=2)
                {
                    if(data.get(j).get(i) == '1')
                    {
                        walls.add(new WallMulti(horizontal * (i)/2,
                                vertical * (j-1)/2, vertical, "V",false,wallStamina));
                    }
                    if(data.get(j).get(i) == '2')
                    {
                        walls.add(new WallMulti(horizontal * (i)/2,
                                vertical * (j-1)/2, vertical, "V",true,wallStamina));
                    }
                }
            }
        }
    }


    /**
     * read a random map form files
     */
    public void readFromFile()
    {

        MapGeneratorMulti mapGenerator = new MapGeneratorMulti();

        File directory = new File("Files/Maps/");

        int count = 0;
        for (File file : Objects.requireNonNull(directory.listFiles()))
        {
            if (file.isFile())
            {
                count++;
            }

        }

        Random random = new Random();
        Integer t = random.nextInt(count) + 1;

        File map = new File("Files/Maps/map" + t.toString() + ".txt");
        try
        {
            FileReader reader = new FileReader(map);
            data.add(new ArrayList<Character>());
            while(reader.ready())
            {
                int temp = reader.read();
                if((char)temp == '1' || (char)temp == '0' || (char)temp == '2')
                {
                    data.get(y).add((char)temp);
                }
                if((char)temp == '\n')
                {
                    data.add(new ArrayList<Character>());
                    y++;
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
