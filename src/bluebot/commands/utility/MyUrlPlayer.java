package bluebot.commands.utility;

import net.dv8tion.jda.JDA;
import net.dv8tion.jda.audio.player.URLPlayer;

/**
 * @file MyUrlPlayer.java
 * @author Blue
 * @version 0.1
 * @brief Override the reset() method to be able to use it in PlaySoundCommand.java
 */

public class MyUrlPlayer extends URLPlayer {
    public MyUrlPlayer(JDA api) {
        super(api);
    }

    @Override
    public void reset() {
        this.started = false;
        this.playing = false;
        this.paused = false;
        this.stopped = true;
        this.urlOfResource = null;
        this.resourceStream = null;
    }
}
