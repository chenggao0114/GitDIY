package gitlet;

import java.io.File;

/** Add Class.
 * @author Cheng Gao */
public class Add extends Command {

    /** Add the file FILE.*/
    public static void run(String file) {
        File tmp = new File(file);
        if (tmp.exists()) {
            String tmpStr = Utils.readContentsAsString(tmp);
            Blob blob = new Blob(tmpStr);
            String cid = branch.sha1(Utils.readContentsAsString(firstFile));
            addHelper(file, blob, cid);
        } else {
            Utils.message("File does not exists.");
        }
    }

    /** Helper function with FILE and BLOB and CID.*/
    protected static void addHelper(String file, Blob blob, String cid) {
        Commit tmp = (Commit) folder.sha1Back(cid);
        String sha1blob = Utils.sha1(Utils.serialize(blob));
        Gitlet git = Utils.readObject(gitletFile, Gitlet.class);
        if (tmp.treemap().get(file) != null
                && tmp.treemap().get(file).equals(sha1blob)) {
            git.remove(file);
        } else {
            folder.save(sha1blob, Utils.serialize(blob));
            git.put(file, sha1blob);
        }

        Utils.writeObject(gitletFile, git);
    }

}
