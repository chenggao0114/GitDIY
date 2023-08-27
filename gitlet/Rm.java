package gitlet;

import java.io.File;
import java.util.HashMap;

/** Add Class.
 * @author Cheng Gao */
public class Rm extends Command {
    /** Remove the file TARGET from Gitlet.*/
    static void run(String target) {
        _target = target;
        thisInit();
        if (jg) {
            Utils.message("No reason to remove the file.");
        } else if (map.containsKey(target)) {
            Utils.restrictedDelete(new File(target));
            if (gitMap.containsKey(target)
                    || map.containsKey(target)) {
                gitMap.put(target, Waste.sha1());
            }
        } else {
            if (!map.containsKey(target)) {
                gitMap.remove(target);
            }
        }

        Utils.writeObject(gitletFile, gitMap);
    }

    /** help to init. */
    static void thisInit() {
        gitMap = Utils.readObject(gitletFile, Gitlet.class);
        id = branch.sha1(Utils.readContentsAsString(firstFile));
        commit = (Commit) folder.sha1Back(id);
        map = commit.treemap();
        jg = !gitMap.containsKey(_target)
                && !map.containsKey(_target);
    }

    /** Field. */
    private static HashMap<String, String> gitMap;
    /** Field. */
    private static String id;
    /** Field. */
    private static Commit commit;
    /** Field. */
    private static String _target;
    /** Field. */
    private static HashMap<String, String> map;
    /** Field. */
    private static boolean jg;

}
