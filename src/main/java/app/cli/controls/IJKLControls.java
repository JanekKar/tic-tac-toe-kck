package app.cli.controls;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

public class IJKLControls implements Controls {

    private static Controls instance;

    private IJKLControls() {
    }

    public static Controls getInstance() {
        if (instance == null)
            instance = new IJKLControls();
        return instance;
    }

    @Override
    public String getName() {
        return "ijkl";
    }

    @Override
    public boolean isUpKey(KeyStroke keyStroke) {
        return keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'i';
    }

    @Override
    public boolean isDownKey(KeyStroke keyStroke) {
        return keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'k';
    }

    @Override
    public boolean isLeftKey(KeyStroke keyStroke) {
        return keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'j';
    }

    @Override
    public boolean isRightKey(KeyStroke keyStroke) {
        return keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'l';
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
