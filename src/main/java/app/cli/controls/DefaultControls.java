package app.cli.controls;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;


public class DefaultControls implements Controls {

    private static Controls instance;

    private DefaultControls() {
    }

    public static Controls getInstance() {
        if (instance == null)
            instance = new DefaultControls();
        return instance;
    }

    @Override
    public String getName() {
        return "default";
    }

    @Override
    public boolean isUpKey(KeyStroke keyStroke) {
        return keyStroke.getKeyType() == KeyType.ArrowUp;
    }

    @Override
    public boolean isDownKey(KeyStroke keyStroke) {
        return keyStroke.getKeyType() == KeyType.ArrowDown;
    }

    @Override
    public boolean isLeftKey(KeyStroke keyStroke) {
        return keyStroke.getKeyType() == KeyType.ArrowLeft;
    }

    @Override
    public boolean isRightKey(KeyStroke keyStroke) {
        return keyStroke.getKeyType() == KeyType.ArrowRight;
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
    public boolean isQuitKey(KeyStroke keyStroke) {
        return keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'q';
    }

    @Override
    public boolean isPauseGameKey(KeyStroke keyStroke) {
        return keyStroke.getKeyType() == KeyType.Escape;
    }

    @Override
    public boolean isPlaceKey(KeyStroke keyStroke) {
        return keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == ' ';
    }
}
