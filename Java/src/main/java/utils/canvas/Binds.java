package utils.canvas;
import java.awt.event.ActionEvent;
import java.util.UUID;
import java.util.function.Consumer;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class Binds {
    public InputMap im;
    public ActionMap am;
    public Binds(Canvas canvas) {
        this.im = canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        this.am = canvas.getActionMap();
    }
    public void add(String key, String id, Consumer<ActionEvent> handle) {
        this.im.put(KeyStroke.getKeyStroke(key), id);
        this.am.put(id, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                handle.accept(e);
            }
        });
    }
    public void add(String key, Consumer<ActionEvent> handle) {
        this.add(key, UUID.randomUUID().toString(), handle);
    }
    public void rm(String key) {
        KeyStroke k = KeyStroke.getKeyStroke(key);
        String id = (String)this.im.get(k);
        this.im.remove(k);
        this.am.remove(id);
    }
}
