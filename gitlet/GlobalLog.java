package gitlet;

/** GlobalLog Class.
 * @author Cheng Gao */
public class GlobalLog extends Command {
    /** globalLog.*/
    protected static void run() {
        content = Utils.join(".gitlet");
        String tmp1 = Utils.readContentsAsString(
                Utils.join(content, "commits"));
        String[] all = tmp1.split("\n");
        int size = all.length;
        for (int i = 0; i < size; i++) {
            System.out.println("===");
            String sha1 = all[i];
            System.out.println("commit " + sha1);
            Commit commit = (Commit) folder.sha1Back(sha1);
            if (commit.mergeMessage() != null) {
                System.out.println("Merge: " + commit.mergeMessage());
            }
            helper(commit);
            System.out.println();
        }

    }

}
