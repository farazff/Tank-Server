package Game;

public enum Direction
{
    NORTHWEST ("-","-"),
    NORTHEAST ("-","+"),
    SOUTHWEST ("+","-"),
    SOUTHEAST ("+","+");


    private final String Y_AXIS;
    private final String X_AXIS;
    Direction (String y_AXIS, String x_AXIS)
    {
        this.X_AXIS = x_AXIS;
        this.Y_AXIS = y_AXIS;
    }

    public String getX_AXIS () {
        return X_AXIS;
    }

    public String getY_AXIS () {
        return Y_AXIS;
    }
}
