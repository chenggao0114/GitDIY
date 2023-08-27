package gitlet;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

/** Add Class.
 * A superclass for all commands.
 * @author Cheng Gao */
public class Command {

    /** Field. */
    protected static File content = Utils.join(".gitlet");
    /** And. */
    protected static HashMap<String, String> _data = new HashMap<>();
    /** And. */
    protected static Folder folder = new Folder(content);
    /** And. */
    protected static File pointerFolder = Utils.join(content, "pointer");
    /** And. */
    protected static Branch branch = new Branch(pointerFolder);
    /** And. */
    protected static File gitletFile = Utils.join(content, "gitlet");

    /** Input COMMIT. a common-used output helper.*/
    static void helper(Commit commit) {
        SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z");
        System.out.println("Date: " + sdf.format(commit.timeStamp()));
        System.out.println(commit.message());
    }

    /** And. */
    protected static File firstFile = Utils.join(content, "first");
    /** And. */
    protected static HashSet<String> _untracked = new HashSet<>();
    /** And. */
    protected static File remoteFile = Utils.join(content, "remote");
    /** And. */
    protected static HashMap<String, String> _mn = new HashMap<>();

    /** to record the index of the system. */
    public static class Gitlet extends
            HashMap<String, String> implements Serializable {
    }
}
