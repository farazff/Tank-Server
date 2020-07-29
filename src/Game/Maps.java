package Game;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import Game.Graph.*;

public class Maps
{
    private ArrayList<Wall> walls;
    private ArrayList< ArrayList<Character> > data = new ArrayList<>();

    private ArrayList< ArrayList<Character> > graph = new ArrayList<>();

    private boolean[][] isOk = new boolean[30][30];

    private int x = 0;
    private int y = 0;
    private int horizontal = 0; //ofoqi
    private int vertical = 0; //amodi
    private int houseX;
    private int houseY;
    private int wallStamina;

    public ArrayList<Wall> getWalls()
    {
        return walls;
    }

    public Maps(int wallStamina)
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


    public boolean canPut(int x,int y)
    {
        int xx = 0 , yy = 0;

        xx = (int) (Math.floor(x/horizontal) + 1);
        yy = (int) (Math.floor(y/vertical) + 1);
        return isOk[yy][xx];
    }


    public void createGraph()
    {
        int vertexNum = (data.size()-1)/2 * (data.get(0).size()-1)/2;
        Graph graph = new Graph(vertexNum + 1);
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
                        walls.add(new Wall(horizontal * (i - 1)/2, vertical * (j)/2, horizontal, "H",
                                false,wallStamina));
                    }
                    if(data.get(j).get(i) == '2')
                    {
                        walls.add(new Wall(horizontal * (i - 1)/2, vertical * (j)/2, horizontal, "H",
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
                        walls.add(new Wall(horizontal * (i)/2,
                                vertical * (j-1)/2, vertical, "V",false,wallStamina));
                    }
                    if(data.get(j).get(i) == '2')
                    {
                        walls.add(new Wall(horizontal * (i)/2,
                                vertical * (j-1)/2, vertical, "V",true,wallStamina));
                    }
                }
            }
        }
    }


    public void readFromFile()
    {

        MapGenerator mapGenerator = new MapGenerator();

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
