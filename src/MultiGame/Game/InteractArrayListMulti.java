package MultiGame.Game;

import java.io.Serializable;
import java.util.ArrayList;

public class InteractArrayListMulti <E> extends ArrayList<E> implements Serializable
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
