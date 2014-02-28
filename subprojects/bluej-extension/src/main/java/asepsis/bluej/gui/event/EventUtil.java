package asepsis.bluej.gui.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class EventUtil {
    public static ActionListener asActionListener(final EventListener e) {
        return new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                e.onEvent();
            }
        };
    }

    public static ItemListener asItemListener(final EventListener e) {
        return new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                e.onEvent();
            }
        };
    }
}
