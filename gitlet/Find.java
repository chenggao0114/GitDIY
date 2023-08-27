package gitlet;

/** Find Class.
 * @author Cheng Gao */
public class Find extends Command {
    /** Find the commit which has MESSAGE.*/
    static void run(String message) {
        String tmp1 = Utils.readContentsAsString(
                Utils.join(content, "commits"));
        String[] all = tmp1.split("\n");
        boolean found = false;
        int count = 1;

        while (count != 0) {
            for (String sha1: all) {
                if (((Commit) folder.sha1Back(sha1))
                        .message().equals(message)) {
                    System.out.println(sha1);
                    found = true;
                }
            }
            count--;
        }
        findHelper(found);
    }
    /** Style using FOUND. */
    static void findHelper(boolean found) {
        if (!found) {
            Utils.message("Found no commit with that message.");
        }
    }

}
