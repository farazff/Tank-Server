package MultiGame.Game;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * this class will reduce concurrent modification
 * @param <E> any Thing
 */
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

    /**
     * sets Iterate that means now the list is iterating
     * @param iterate iterate
     */
    public void setIterate (boolean iterate) {
        this.iterate = iterate;
    }
}
