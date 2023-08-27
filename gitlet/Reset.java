package gitlet;

/** Reset Class.
 * @author Cheng Gao */
public class Reset extends Command {
    /** Reset the id ARG1. */
    static void run(String arg1) {
        Checkout.checkoutAll(arg1);
        branch.change(Utils.readContentsAsString(firstFile), arg1);
    }

}
