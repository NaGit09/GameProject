package Model.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class SoundManager {

    private Clip clip;
    private final URL[] soundUrl = new URL[30];
    private FloatControl floatControl;
    private int volumeScale = 3;
    private float volume;

    public SoundManager() {
        soundUrl[0] = getClass().getResource("/resources/sounds/BlueBoyAdventure.wav");
        soundUrl[1] = getClass().getResource("/resources/sounds/coin.wav");
        soundUrl[2] = getClass().getResource("/resources/sounds/powerup.wav");
        soundUrl[3] = getClass().getResource("/resources/sounds/unlock.wav");
        soundUrl[4] = getClass().getResource("/resources/sounds/fanfare.wav");
        soundUrl[5] = getClass().getResource("/resources/sounds/hitmonster.wav");
        soundUrl[6] = getClass().getResource("/resources/sounds/receivedamage.wav");
        soundUrl[7] = getClass().getResource("/resources/sounds/cuttree.wav");
        soundUrl[8] = getClass().getResource("/resources/sounds/levelup.wav");
        soundUrl[9] = getClass().getResource("/resources/sounds/cursor.wav");
        soundUrl[10] = getClass().getResource("/resources/sounds/burning.wav");
        soundUrl[11] = getClass().getResource("/resources/sounds/gameover.wav");
        soundUrl[12] = getClass().getResource("/resources/sounds/stairs.wav");
    }

    public void setFile(int index) {

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrl[index]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public void checkVolume() {
        switch (volumeScale) {
            case 0 -> volume = -80f;
            case 1 -> volume = -20f;
            case 2 -> volume = -12f;
            case 3 -> volume = -5f;
            case 4 -> volume = 1f;
            case 5 -> volume = 6f;
        }

        floatControl.setValue(volume);
    }

    public int getVolumeScale() {
        return volumeScale;
    }

    public SoundManager setVolumeScale(int volumeScale) {
        this.volumeScale = volumeScale;
        return this;
    }
}
