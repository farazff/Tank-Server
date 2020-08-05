package MultiGame.Game;

import java.io.Serializable;
/**
 * this enum represents Direction in game
 */
public enum DirectionMulti implements Serializable
{
    NORTHWEST ("-","-"),
    NORTHEAST ("-","+"),
    SOUTHWEST ("+","-"),
    SOUTHEAST ("+","+");


    private final String Y_AXIS;
    private final String X_AXIS;
    /**
     * creates new Direction
     * @param y_AXIS y_AXIS
     * @param x_AXIS x_AXIS
     */
    DirectionMulti (String y_AXIS, String x_AXIS)
    {
        this.X_AXIS = x_AXIS;
        this.Y_AXIS = y_AXIS;
    }

    /**
     *
     * @return get X_AXIS
     */
    public String getX_AXIS () {
        return X_AXIS;
    }

    /**
     *
     * @return get Y_AXIS
     */
    public String getY_AXIS () {
        return Y_AXIS;
    }
}
