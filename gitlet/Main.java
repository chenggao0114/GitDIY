package gitlet;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Cheng Gao
 *  collaborators:
 *  Xiangyu Peng,
 *  Heming Wu (not enrolled),
 *  Yifan Yan (not enrolled).
 *
 *  Collaborate with Xiangyu Peng for the project structures.
 *  Collaborate with Yifan Yan for some trivial detials in I/O.
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) {
        if (args.length == 0) {
            Utils.message("Please enter a command.");
        }
        _args = args;
        start();
    }

    /** Judge which command is input and process it. */
    public static void start() {
        Folder folder = new Folder(Utils.join(".gitlet"));
        if (_args[0].equals("init")) {
            checkNum(1);
            Init.run();
        } else if (!folder.ifInit()) {
            Utils.message("Not in an initialized gitlet directory.");
        }
        String judge = _args[0];
        if (judge.equals("log")) {
            checkNum(1);
            Log.run();
        } else if (judge.equals("status")) {
            checkNum(1);
            Status.run();
        } else if (judge.equals("add")) {
            checkNum(2);
            Add.run(_args[1]);
        } else if (judge.equals("global-log")) {
            checkNum(1);
            GlobalLog.run();
        } else if (judge.equals("find")) {
            checkNum(2);
            Find.run(_args[1]);
        } else if (judge.equals("rm")) {
            checkNum(2);
            Rm.run(_args[1]);
        } else if (judge.equals("branch")) {
            checkNum(2);
            CreateBranch.run(_args[1]);
        } else if (judge.equals("rm-branch")) {
            checkNum(2);
            RmBranch.run(_args[1]);
        } else if (judge.equals("merge")) {
            checkNum(2);
            Merge.run(_args[1]);
        } else {
            another(judge);
        }
    }

    /** CheckoutHelper. For style. */
    static void checkoutHelper() {
        if (_args.length < 4 && _args[1].equals("--")) {
            Checkout.niceCheckout(_args[2]);
        } else if (_args.length < 5 && _args[2].equals("--")) {
            try {
                String id = Folder.idBack(_args[1]);
                Checkout.checkout(id, _args[3]);
            } catch (IllegalArgumentException e) {
                Utils.message("No commit with that id exists.");
            }
        } else {
            Utils.message("Incorrect operands.");
        }
    }

    /** Style for continuing JUDGE. */
    public static void another(String judge) {
        if (judge.equals("checkout")) {
            if (_args.length == 1) {
                Utils.message("Incorrect operands.");
            }
            if (_args.length < 3) {
                if (!Command.branch.exists(_args[1])) {
                    Utils.message("No such branch exists.");
                }
                Checkout.helper(_args[1]);
            } else {
                checkoutHelper();
            }
        } else if (judge.equals("commit")) {
            if (_args[1].equals("")) {
                Utils.message("Please enter a commit message.");
            }
            Cmt.run(_args[1], null);
        } else if (judge.equals("reset")) {
            checkNum(2);
            try {
                Reset.run(Folder.idBack(_args[1]));
            } catch (IllegalArgumentException excp) {
                Utils.message("No commit with that id exists.");
            }
        } else if (judge.equals("add-remote")) {
            checkNum(3);
        } else if (judge.equals("rm-remote")) {
            checkNum(2);
        } else if (judge.equals("push")) {
            checkNum(3);
        } else if (judge.equals("fetch")) {
            checkNum(3);
        } else if (judge.equals("pull")) {
            checkNum(3);
        } else {
            Utils.message("No command with that name exists.");
        }
    }

    /** To check if the number of args is NUM. */
    public static void checkNum(int num) {
        if (_args.length != num) {
            Utils.message("Incorrect number of operands.");
        }
    }

    /** Record the arg message. */
    private static String[] _args;
}
