package helpers.fileDropper;

import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.PrintStream;
import java.util.EventObject;

public class FileDrop {
    private Boolean supportsDragAndDrop;

    public FileDrop(PrintStream out, Component component, Border dragBorder, boolean recursive, Listener listener) {

    }

    private boolean supportsDragAndDrop() {
        if (supportsDragAndDrop == null) {
            boolean support = false;
            try {
                Class arbitraryDndClass = Class.forName( "java.awt.dnd.DnDConstants" );
                support = true;
            } catch (Exception ex) {
                support = false;
            }
            supportsDragAndDrop = support;
        }
        return supportsDragAndDrop.booleanValue();
    }

    public interface Listener {
        void filerDropped(java.io.File file);
    }

    public class Event extends EventObject {
        private File file;

        public Event(File file, Object source) {
            super(source);
            this.file = file;
        }

        public File getFile() {
            return file;
        }
    }
}
