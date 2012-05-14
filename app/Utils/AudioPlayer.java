package Utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer {

    /*
       1. Create mapping between events and audio
       2. Find static audio wav files which can be used
       3. In Job model add a boolean which will indicate whether the build was building previously and if it is still broken
          This one should be updated when we refresh the jobs on jobdisplay().
       4. Add the ability to mute/unmute all the sounds
       5. Add ordering
       6. Add config for users + map it to their avatar. If broken show last user who checked-in.
       --> Ability to upload avatars.
       7. Make refresh rate configurable
     */

    public static final String FAILURE_SOUND = "failure.wav";
    public static final String FIXED_SOUND = "fixed.wav";
    public static final String UNSTABLE_SOUND = "unstable.wav";

    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() { // the wrapper thread is unnecessary, unless it blocks on the Clip finishing, see comments
          public void run() {
            try {
              Clip clip = AudioSystem.getClip();
              AudioInputStream inputStream = AudioSystem.getAudioInputStream(AudioPlayer.class.getResourceAsStream("/path/to/sounds/" + url));
              clip.open(inputStream);
              clip.start();
            } catch (Exception e) {
              System.err.println(e.getMessage());
            }
          }
        }).start();
    }
}



