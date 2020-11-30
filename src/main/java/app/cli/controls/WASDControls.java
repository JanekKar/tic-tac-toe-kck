package app.cli.controls;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

public class WASDControls implements Controls {
    private static Controls instance;

    private WASDControls() {
    }

    public static Controls getInstance() {
        if (instance == null)
            instance = new WASDControls();
        return instance;
    }

    @Override
    public boolean isUpKey(KeyStroke keyStroke) {
        return keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'w';
    }

    @Override
    public boolean isDownKey(KeyStroke keyStroke) {
        return keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 's';
    }

    @Override
    public boolean isLeftKey(KeyStroke keyStroke) {
        return keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'a';
    }

    @Override
    public boolean isRightKey(KeyStroke keyStroke) {
        return keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'd';
    }

    @Override
    public boolean isAssertKey(KeyStroke keyStroke) {
        return keyStroke.getKeyType() == KeyType.Enter;
    }

    @Override
    public boolean isEscapeKey(KeyStroke keyStroke) {
        return keyStroke.getKeyType() == KeyType.Escape;
    }

    @Override
    public boolean isPlaceKey(KeyStroke keyStroke) {
        return keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == ' ';
    }

    @Override
    public boolean isQuitKey(KeyStroke keyStroke) {
        return keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'q';
    }

    @Override
    public boolean isPauseGameKey(KeyStroke keyStroke) {
        return keyStroke.getKeyType() == KeyType.Escape;
    }
}
