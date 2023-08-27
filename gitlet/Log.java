package gitlet;

/** Log Class.
 * @author Cheng Gao */
public class Log extends Command {

    /** log.*/
    static void run() {
        firstFile = Utils.join(content, "first");
        String nowb = branch.sha1(Utils.readContentsAsString(firstFile));
        Commit commit = (Commit) folder.sha1Back(nowb);

        if (commit != null) {
            System.out.println("===");
            if (commit != null) {
                System.out.println("commit " + nowb);
                if (commit.mergeMessage() != null) {
                    System.out.println("Merge: " + commit.mergeMessage());
                }
                helper(commit);
            }
        }

        for (Commit last = (Commit) folder.sha1Back(commit.parentID());
             last != null; last = (Commit) folder.sha1Back(last.parentID())) {
            System.out.println();
            System.out.println("===");
            nowb = ((Commit) folder.sha1Back(nowb)).parentID();
            if (last.message().equals("initial commit")) {
                String tmp1 = Utils.readContentsAsString(
                        Utils.join(content, "commits"));
                System.out.println("commit " + tmp1.split("\n")[0]);

            } else {
                System.out.println("commit " + nowb);
            }
            if (last.mergeMessage() != null) {
                System.out.println("Merge: " + last.mergeMessage());
            }
            helper(last);
        }
    }



}
