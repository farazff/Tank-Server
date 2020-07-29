package Game.Server;

import java.util.ArrayList;

public class SynchronizedArrayList
{
    private ArrayList<String> data = new ArrayList<>();

    public synchronized void add(String temp)
    {
        while(data.size() == 5)
        {
            try
            {
                wait();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        data.add(temp);
        System.out.println("write" + temp);
        notifyAll();
    }

    public synchronized ArrayList<String> get()
    {
        while(!(data.size() == 5))
        {
            try
            {
                System.out.println("datanum +=" + data.size());
                System.out.println("waiting.....");
                wait();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        ArrayList<String> current  = new ArrayList<>();
        System.out.println("Giving data");
        current.add(data.get(0));
        current.add(data.get(1));
        current.add(data.get(2));
        current.add(data.get(3));
        current.add(data.get(4));
        data.clear();
        notifyAll();
        return current;
    }

}