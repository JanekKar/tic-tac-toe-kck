package app.cli.controls;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

public interface Controls {

    boolean isUpKey(KeyStroke keyStroke);
    boolean isDownKey(KeyStroke keyStroke);
    boolean isLeftKey(KeyStroke keyStroke);
    boolean isRightKey(KeyStroke keyStroke);
    boolean isAssertKey(KeyStroke keyStroke);
    boolean isEscapeKey(KeyStroke keyStroke);
    boolean isPlaceKey(KeyStroke keyStroke);
    boolean isQuitKey(KeyStroke keyStroke);
    boolean isPauseGameKey(KeyStroke keyStroke);


}
