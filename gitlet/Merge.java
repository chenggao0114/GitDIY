package gitlet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

/** Add Class.
 * @author Cheng Gao */
public class Merge extends Command {
    /** Merge to the branch named NAME. */
    static void run(String name) {
        branch = new Branch(pointerFolder);
        if (branch.exists(name)) {
            if (!Utils.readObject(gitletFile, Gitlet.class).isEmpty()) {
                Utils.message("You have uncommitted changes.");
            } else if (nowb.equals(name)) {
                Utils.message("Cannot merge a branch with itself.");
            }

            String last = cid;
            String mergeID = branch.sha1(name);
            String sid = mergeID;
            allcommits.add(last);

            Commit lastCommit = nowCommit;
            for (; lastCommit.parentID() != null;) {
                if (!last.equals(mergeID)) {
                    last = lastCommit.parentID();
                    lastCommit = (Commit) folder.sha1Back(last);
                    allcommits.add(last);
                } else {
                    Utils.message(
                            "Given branch is an ancestor of"
                                    + " the current branch.");
                }
            }

            Commit sCommit = (Commit) folder.sha1Back(mergeID);
            for (int count = 0; sid != null && !allcommits.contains(sid);) {
                sid = sCommit.parentID();
                count++;
                if (count > 0) {
                    sCommit = (Commit) folder.sha1Back(sid);
                }
            }

            sid = (sid == null) ? last : sid;
            if (!cid.equals(sid)) {
                String msg = "Merged " + name + " into " + nowb + ".";
                HashMap<String, String> sContents;
                sContents = sCommit.treemap();
                HashMap<String, String> nowContents = nowCommit.treemap();
                HashMap<String, String> mContents =
                        ((Commit) folder.sha1Back(mergeID)).treemap();
                boolean jg = mergeContents(nowContents, mContents, sContents);
                Cmt.run(msg, cid.substring(0, 7) + " "
                        + mergeID.substring(0, 7));
                if (jg) {
                    Utils.message("Encountered a merge conflict.");
                }
            } else {
                branch.change(nowb, sid);
                Utils.message("Current branch fast-forwarded.");
            }
        } else {
            Utils.message("A branch with that name does not exist.");
        }
    }

    /** Do merge with NOW, MERGE, SPLIT.
     * @return  */
    static boolean mergeContents(HashMap<String, String> now,
                                 HashMap<String, String> merge,
                                 HashMap<String, String> split) {
        HashSet<String> pack = new HashSet<>();
        boolean res = false;
        if (!(res && res)) {
            pack.addAll(now.keySet());
            pack.addAll(merge.keySet());
        }
        pack.addAll(split.keySet());
        Iterator<String> g = pack.iterator();

        while (g.hasNext()) {
            String e = g.next();
            if (split.get(e) == null) {
                if (merge.get(e) != null) {
                    if (now.get(e) == null) {
                        help(e, now, merge);
                    } else if (!Objects.equals(now.get(e), merge.get(e))) {
                        mergeHelper(e, now.get(e), merge.get(e));
                        res = true;
                    }
                }
            } else {
                boolean tmp = merge.get(e) != null
                        && merge.get(e).equals(split.get(e));
                if (tmp) {
                    continue;
                }

                if (now.get(e) != null && now.get(e).equals(split.get(e))) {
                    if (merge.get(e) == null
                            || merge.get(e).equals(split.get(e))) {
                        Utils.restrictedDelete(Utils.join(e));
                        Rm.run(e);
                    } else {
                        help(e, now, merge);
                    }

                } else if (!Objects.equals(now.get(e), merge.get(e))) {
                    mergeHelper(e, now.get(e), merge.get(e));
                    res = true;
                }

            }
        }
        return res;
    }

    /** Merge into E using NOWBLOB and MBLOB. */
    static void mergeHelper(String e, String nowBlob, String mBlob) {
        String domi = ((Blob) folder.sha1Back(mBlob) != null)
                ? ((Blob) folder.sha1Back(mBlob)).contents() : "";
        if (!domi.equals("")) {
            domi = domi + "\n";
        }
        String tmp = "<<<<<<< HEAD\n"
                + ((Blob) folder.sha1Back(nowBlob)).contents()
                + "\n=======\n" + domi + ">>>>>>>\n";
        Utils.writeContents(Utils.join(e), tmp);
        String ccid = branch.sha1(Utils.readContentsAsString(firstFile));
        Add.addHelper(e, new Blob(tmp), ccid);
    }

    /** Add a E, NOW, MERGE helper. */
    private static void help(String e,
                             HashMap<String, String> now,
                             HashMap<String, String> merge) {
        if (Utils.join(e).exists() && !now.containsKey(e)) {
            Utils.message("There is an untracked file in the way; "
                    + "delete it or add it first.");
        }
        Blob b = (Blob) folder.sha1Back(merge.get(e));
        Utils.writeContents(Utils.join(e), b.contents());
        String ccid = branch.sha1(Utils.readContentsAsString(firstFile));
        Add.addHelper(e, b, ccid);
    }

    /** The branch now. */
    private static String nowb = Utils.readContentsAsString(Utils.join(content,
            "first"));
    /** the id of the branch now. */
    private static String cid = branch.sha1(nowb);
    /** the commit now. */
    private static Commit nowCommit = (Commit) folder.sha1Back(cid);
    /** all the commits. */
    private static HashSet<String> allcommits = new HashSet<>();
}
