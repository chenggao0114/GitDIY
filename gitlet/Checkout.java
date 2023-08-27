package gitlet;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/** Checkout Class.
 * @author Cheng Gao */
public class Checkout extends Command {

    /** Checkout to ID and File named NAME. */
    static void checkout(String id, String name) {
        File willCheck = Utils.join(name);
        Commit commit = (Commit) folder.sha1Back(id);
        HashMap<String, String> map = commit.treemap();

        if (!willCheck.exists()) {
            Utils.message("File does not exist in that commit.");
        } else if (id == null || !folder.hashBack(id).exists()) {
            Utils.message("No commit with that id exists.");
        }

        Blob b = (Blob) folder.sha1Back(map.get(name));

        if (willCheck.exists()) {
            Utils.writeContents(willCheck, b.contents());
        }
    }

    /** Check out Branch named NAME. */
    static void helper(String name) {
        String nowb = Utils.readContentsAsString(firstFile);
        if (name.equals(nowb)) {
            Utils.message("No need to checkout the current branch.");
        }
        checkoutAll(branch.sha1(name));
        Utils.writeContents(firstFile, name);
    }

    /** checkoutAll COMMIT with ID. */
    static void checkoutAll(String id) {
        Commit commit = (Commit) folder.sha1Back(id);
        HashMap<String, String> gtrack = commit.treemap();
        Commit tmp = (Commit) folder.sha1Back(cid);
        HashMap<String, String> ctrack = tmp.treemap();

        if (!ctrack.isEmpty()) {
            Iterator<String> g = ctrack.keySet().iterator();
            while (g.hasNext()) {
                String file = g.next();
                if (!gtrack.containsKey(file)) {
                    Utils.restrictedDelete(file);
                }
            }
        }

        if (!gtrack.isEmpty()) {
            Iterator<String> g = gtrack.keySet().iterator();
            while (g.hasNext()) {
                String file = g.next();
                if (Utils.join(file).exists() && !ctrack.containsKey(file)) {
                    Utils.message("There is an untracked file "
                            + "in the way; delete it or add it first.");
                }
            }
        }

        if (!gtrack.isEmpty()) {
            Iterator<Entry<String, String>> iter =
                    gtrack.entrySet().iterator();
            while (iter.hasNext()) {
                Entry<String, String> e = iter.next();
                Blob newBlob = (Blob) folder.sha1Back(e.getValue());
                Utils.writeContents(Utils.join(e.getKey()), newBlob.contents());
            }
        }

        Utils.writeObject(gitletFile, new Gitlet());
    }



    /** Checkout to file named NAME. */
    static void niceCheckout(String name) {
        checkout(cid, name);
    }

    /** Current ID. */
    private static String cid =
            branch.sha1(Utils.readContentsAsString(firstFile));


}
