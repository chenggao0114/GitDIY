package gitlet;

/** RmBranch Class.
 * @author Cheng Gao */
public class RmBranch extends Command {
    /** Remove the branch with NAME. */
    static void run(String name) {
        String nowb = Utils.readContentsAsString(firstFile);
        if (nowb.equals(name)) {
            Utils.message("Cannot remove the current branch.");
        } else if (!branch.exists(name)) {
            Utils.message("A branch with that name does not exist.");
        } else {
            branch.pointer(name).delete();
            if (branch.exists(name)) {
                Utils.message("A branch with that name does not exist.");
            }
        }
    }

}
