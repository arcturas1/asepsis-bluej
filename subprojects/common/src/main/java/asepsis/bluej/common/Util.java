package asepsis.bluej.common;

import java.io.File;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;

public class Util {
    /**
     * Deletes a directory and all its contents regardless of whether it's empty or not.
     *
     * @param dir The directory to delete
     */
    public static void deleteDirectory(File dir) {
        for (File file: dir.listFiles()) {
            if (file.isDirectory())
                deleteDirectory(file);
            file.delete();
        }
        dir.delete();
    }

    public static String[] toStrArray(List<String> lst) {
        return lst.toArray(new String[lst.size()]);
    }
}
