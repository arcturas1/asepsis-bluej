package asepsis.bluej.gui.main;

import asepsis.bluej.gui.event.EventListener;

public class MainPresenter {
    private final MainView view;
    private final MainModel model;

    public MainPresenter(MainView view, MainModel model) {
        this.view = view;
        this.model = model;
        initPresentationLogic();
    }

    private void initPresentationLogic() {
        view.whenProjectSetupRequested(new View_OnProjectSetupRequest());
        model.whenSetupChanges(new Model_OnSetupChanged());
        model.whenEnableGuiRequested(new Model_OnEnableGuiRequest());
        model.whenDisableGuiRequested(new Model_OnDisableGuiRequest());
    }

    private class View_OnProjectSetupRequest implements EventListener {
        public void onEvent() { model.setupProject(); }
    }

    private class Model_OnSetupChanged implements EventListener {
        public void onEvent() { view.onSetupChange(model.isSetup()); }
    }

    private class Model_OnEnableGuiRequest implements EventListener {
        public void onEvent() { view.setEnable(true); }
    }

    private class Model_OnDisableGuiRequest implements EventListener {
        public void onEvent() { view.setEnable(false); }
    }
}
