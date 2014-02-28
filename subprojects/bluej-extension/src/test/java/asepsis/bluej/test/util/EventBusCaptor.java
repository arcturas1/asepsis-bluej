package asepsis.bluej.test.util;

import com.google.common.eventbus.Subscribe;

import static org.junit.Assert.assertNotNull;

public class EventBusCaptor<T> {
    public T event;
    private Class<T> clazz;

    public EventBusCaptor(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Subscribe
    public void capture(T e) { event = e; }

    public void assertCapture() {
        assertNotNull("Expected " + clazz.getSimpleName() + " event to arrive on EventBus, but it didn't.", event);
    }
}
