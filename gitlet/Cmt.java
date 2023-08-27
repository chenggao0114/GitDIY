package gitlet;

import java.util.Date;
import java.util.HashMap;

/** Cmt Class.
 * @author Cheng Gao */
public class Cmt extends Command {

    /** Commit with MESSAGE. MERGEMESSAGE. */
    static void run(String message, String mergeMessage) {
        String head = Utils.readContentsAsString(firstFile);
        Gitlet git = Utils.readObject(gitletFile, Gitlet.class);
        int ifempty = 0;
        if (!git.isEmpty()) {
            Commit commit = (Commit) folder.sha1Back(branch.sha1(head));
            HashMap<String, String> parent = commit.treemap();
            for (HashMap.Entry<String, String> e: git.entrySet()) {
                if (e.getValue().equals(Waste.sha1())) {
                    parent.remove(e.getKey());
                } else {
                    if (git.isEmpty()) {
                        Utils.message("No changes added to the commit.");
                        git.clear();
                        Utils.writeObject(gitletFile, git);
                    }
                    parent.put(e.getKey(), e.getValue());
                }
            }
            if (ifempty == 0) {
                git.clear();
                Utils.writeObject(gitletFile, git);
            }

            Commit newCommit = new Commit(new Tree(parent),
                    message, new Date(), mergeMessage, branch.sha1(head));
            branch.change(head, Init.wCommit(content, newCommit));
        } else {
            Utils.message("No changes added to the commit.");
        }
    }

}
