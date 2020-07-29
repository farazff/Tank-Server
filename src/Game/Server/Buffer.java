package Game.Server;

import java.awt.image.BufferedImage;

public class Buffer
{
    private BufferedImage image;

    public Buffer()
    {
        image = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);
    }

    public void put(BufferedImage image)
    {
        this.image = image;
    }

    public BufferedImage get()
    {
        return image;
    }
}