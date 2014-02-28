package asepsis.bluej.gui;

import bluej.extensions.BlueJ;

public class BluejLabeller implements Labeller {
    private BlueJ blueJ;

    public BluejLabeller(BlueJ blueJ) {
        this.blueJ = blueJ;
    }

    @Override
    public String getLabel(String key) {
        return blueJ.getLabel(key);
    }
}
