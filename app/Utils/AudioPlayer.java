package Utils;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;

public class AudioPlayer {
    private Player player;

    public void close() {
        if (player != null) player.close();
    }

    public void play(String s) {
        try {
            WebConversation webConversation = new WebConversation();
            WebResponse webResponse = webConversation.getResponse("http://translate.google.com/translate_tts?q=" + s);
            BufferedInputStream bis = new BufferedInputStream(webResponse.getInputStream());
            player = new Player(bis);

        } catch (Exception e) {
            e.printStackTrace();
        }
        new Thread() {
            public void run() {
                try {
                    player.play();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        AudioPlayer mp3 = new AudioPlayer();
        mp3.close();
        mp3 = new AudioPlayer();
        mp3.play("hello there!");
    }

     public static void speak(String s) throws Exception {
        AudioPlayer mp3 = new AudioPlayer();
        mp3.close();
        mp3 = new AudioPlayer();
        mp3.play(s);
        System.out.println(s);
    }
}



