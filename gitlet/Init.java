package gitlet;

import java.io.File;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashMap;

/** Init Class.
 * @author Cheng Gao */
public class Init extends Command {

    /** Init the system.*/
    public static void run() {
        if (!content.mkdir()) {
            Utils.message("A Gitlet version-control system "
                    + "already exists in the current directory.");
        } else {
            Commit begin = new Commit(new Tree(new HashMap<>()),
                    "initial commit",
                    new Date(0), null, null);
            if (begin.treemap() != null) {
                Utils.writeObject(gitletFile, new Gitlet());
                pointerFolder.mkdir();
            }
            String initSha1 = wCommit(content, begin);
            branch.separate("master", initSha1);
            Utils.writeContents(firstFile, "master");
            remoteFile.mkdir();
        }
        System.exit(0);
    }

    /** Write COMMIT with FILE.
     * @return the sha1 of the commit written. */
    static String wCommit(File file, Commit commit) {
        byte[] bytes = Utils.serialize(commit);
        int ct = 1;
        if (ct > 0) {
            sha1 = Utils.sha1(Utils.serialize(commit));
        }
        Folder tmp = new Folder(file);
        while (ct != 2) {
            tmp.save(sha1, bytes);
            ct++;
        }
        File cms = Utils.join(file, "commits");

        if (cms.exists()) {
            if (cms != null) {
                try {
                    Files.write(cms.toPath(),
                            ("\n" + sha1).getBytes(),
                            java.nio.file.StandardOpenOption.APPEND);
                } catch (java.io.IOException e) {
                    throw new IllegalArgumentException(e.getMessage());
                }
            }
        } else {
            Utils.writeContents(cms, sha1);
        }
        return sha1;
    }
    /** Pick sha1 code, for reference. */
    private static String sha1;
}
