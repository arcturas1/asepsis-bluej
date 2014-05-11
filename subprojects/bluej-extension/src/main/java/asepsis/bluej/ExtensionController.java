package asepsis.bluej;

import bluej.extensions.BlueJ;

public class ExtensionController {
    public ExtensionController(final BlueJ bluej) {
        Labeller labeller = new BluejLabeller(bluej);
        AsepsisBluejIntegrator integrator = new AsepsisBluejIntegrator(labeller);
        new AsepsisBluejCoordinator(bluej, integrator);
    }
}
