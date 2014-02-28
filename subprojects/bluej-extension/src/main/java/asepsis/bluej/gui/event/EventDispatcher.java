package asepsis.bluej.gui.event;

import java.util.HashSet;
import java.util.Set;

public class EventDispatcher {
    private Set<EventListener> listeners = new HashSet<EventListener>();

    public void heardBy(EventListener e) {
        listeners.add(e);
    }

    public void tellListeners() {
        for (EventListener e : listeners)
            e.onEvent();
    }
}
