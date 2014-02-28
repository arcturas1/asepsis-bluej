package asepsis.bluej;


import asepsis.bluej.gui.ExtensionController;
import bluej.extensions.BlueJ;
import bluej.extensions.Extension;
import bluej.extensions.event.ApplicationEvent;
import bluej.extensions.event.ApplicationListener;

import java.net.MalformedURLException;
import java.net.URL;

public class BluejExtension extends Extension {
    private BlueJ bluej;

    @Override
    public boolean isCompatible() {
        return true;
    }

    @Override
    public void startup(BlueJ blueJ) {
        bluej = blueJ;
        new ExtensionController(bluej);
    }

    @Override
    public String getName() {
        return "ASEPSiS-BlueJ";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public URL getURL() {
        try {
            return new URL("http://github.com/olerass/asepsis-bluej");
        } catch (MalformedURLException ignored) {}
        return null;
    }

    @Override
    public String getDescription() {
        return bluej.getLabel("asepsis.extension.description");
    }
}