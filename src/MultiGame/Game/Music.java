package MultiGame.Game;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
/**
 * this class handles every sound that is playing in the game
 */
public class Music extends SwingWorker<Long, Object>
{
    boolean repeat = false;
    Clip clip;
    String filePath = "Files/Sounds/Button.au";

    /**
     * set the music file pth and the repeat mode
     * @param filePath the path of the music file
     * @param repeat if true the music will repeat and if false it will be played just once
     */
    public void setFilePath(String filePath , boolean repeat)
    {
        this.filePath = filePath;
        this.repeat = repeat;
    }

    /**
     * we can start the music with this method
     */
    public void start()
    {
        clip.start();
    }

    @Override
    protected Long doInBackground()
    {
        try
        {
            AudioInputStream audioInputStream;
            audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            if(repeat)
                clip.loop(Clip.LOOP_CONTINUOUSLY);

            start();
        }
        catch (UnsupportedAudioFileException | LineUnavailableException | IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
