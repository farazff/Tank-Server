package MultiGame.Game;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Music extends SwingWorker<Long, Object>
{
    boolean repeat = false;
    Clip clip;
    String filePath = "Files/Sounds/Button.au";

    public void setFilePath(String filePath , boolean repeat)
    {
        this.filePath = filePath;
        this.repeat = repeat;
    }

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
