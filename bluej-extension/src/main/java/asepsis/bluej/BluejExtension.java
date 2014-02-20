package asepsis.bluej;

import bluej.extensions.BlueJ;
import bluej.extensions.Extension;

public class BluejExtension extends Extension {
    @Override
    public boolean isCompatible() {
        return true;
    }

    @Override
    public void startup(BlueJ blueJ) {
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getVersion() {
        return "";
    }

}