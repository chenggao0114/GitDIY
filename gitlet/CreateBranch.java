package gitlet;

/** CreateBranch Class.
 * @author Cheng Gao */
public class CreateBranch extends Command {
    /** Create a branch named NAME. */
    static void run(String name) {
        firstFile = Utils.join(content, "first");
        branch.separate(name, branch.sha1(
                Utils.readContentsAsString(firstFile)));
    }

}
