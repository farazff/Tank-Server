package Game;

import java.util.ArrayList;

public class InteractArrayList <E> extends ArrayList<E>
{

    private boolean iterate = false;


    @Override
    public boolean add (E e) {

        while (iterate)
        {
            try{
                Thread.sleep (10);
            } catch (InterruptedException ex)
            {
                ex.printStackTrace ();
            }
        }
        return super.add (e);
    }

    public void setIterate (boolean iterate) {
        this.iterate = iterate;
    }
}
