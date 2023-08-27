package gitlet;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.HashSet;
import java.util.List;

/** Add Class.
 * @author Cheng Gao */
public class Status extends Command {
    /** Status. */
    static void run() {
        status1();
        status2();
        status3();
        status4();
        status5();
    }

    /** Status1. */
    static void status1() {
        System.out.println("=== Branches ===");
        List<String> all = Utils.plainFilenamesIn(pointerFolder);
        int size = all.size();
        int count = 0;
        while (count < size) {
            String str = all.get(count);
            if (!str.equals(Utils.readContentsAsString(firstFile))) {
                System.out.println(str);
            } else {
                System.out.println("*" + str);
            }
            count++;
        }
    }

    /** Status2. */
    static void status2() {
        HashMap<String, String> gits =
                Utils.readObject(gitletFile, Gitlet.class);
        System.out.println("\n=== Staged Files ===");
        for (Entry<String, String> e : gits.entrySet()) {
            int count = 0;
            if (e.getValue().equals(Waste.sha1())) {
                count++;
            } else {
                System.out.println(e.getKey());
            }
            if (count == -1) {
                System.exit(0);
            }
        }
    }

    /** Status3. */
    static void status3() {
        HashMap<String, String> gits =
                Utils.readObject(gitletFile, Gitlet.class);
        System.out.println("\n=== Removed Files ===");
        for (Entry<String, String> e : gits.entrySet()) {
            int count = 0;
            if (!e.getValue().equals(Waste.sha1())) {
                count++;
            } else {
                System.out.println(e.getKey());
            }
            if (count == -1) {
                System.exit(0);
            }
        }
    }

    /** Status4. */
    static void status4() {
        System.out.println("\n=== Modifications Not Staged For Commit ===");
        _mn = new HashMap<>();
        _untracked = new HashSet<>();
        HashMap<String, String> gits =
                Utils.readObject(gitletFile, Gitlet.class);
        String id = branch.sha1(Utils.readContentsAsString(firstFile));
        _data = ((Commit) folder.sha1Back(id)).treemap();
        List<String> tmp = Utils.plainFilenamesIn(Utils.join("."));
        HashSet<String> allF = new HashSet<>(tmp);
        allF.addAll(gits.keySet());
        allF.addAll(_data.keySet());
        statusHelper(allF);
    }

    /** Status5 . */
    static void status5() {
        for (Entry<String, String> e : _mn.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue());
        }
        int size = _untracked.size();
        System.out.println("\n=== Untracked Files ===");
        java.util.Iterator<String> g = _untracked.iterator();
        while (g.hasNext()) {
            String th = g.next();
            boolean jg = th.equals("proj3.iml")
                    || th.equals(".gitignore")
                    || th.equals("Makefile")
                    || th.equals(".DS_Store")
                    || th.equals("gitlet-design.txt");
            if (!jg) {
                System.out.println(th);
            }
        }
    }

    /** StatusHelper using ALLF. */
    static void statusHelper(HashSet<String> allF) {
        HashMap<String, String> gits =
                Utils.readObject(gitletFile, Gitlet.class);

        for (String f : allF) {
            if (!Utils.join(f).exists()) {
                if (gits.containsKey(f)) {
                    if (!gits.get(f).equals(Waste.sha1())) {
                        _mn.put(f, "(deleted)");
                    }
                } else {
                    _mn.put(f, "(deleted)");
                }
            } else {
                String contents = Utils.readContentsAsString(Utils.join(f));
                if (gits.containsKey(f)) {
                    String fileID = gits.get(f);
                    int count = 0;
                    if (Utils.join(f).exists()) {
                        count++;
                        if (!fileID.equals(Waste.sha1())) {
                            Blob blob = (Blob) folder.sha1Back(fileID);
                            if (count < 0) {
                                System.exit(0);
                            }
                            if (!java.util.Objects.equals(
                                    blob.contents(), contents)) {
                                _mn.put(f, "(modified)");
                            }
                        } else {
                            _mn.put(f, "(modified)");
                        }
                    }
                } else if (_data.containsKey(f)) {
                    if (!_data.get(f).equals(Waste.sha1())) {
                        String str =
                                ((Blob) folder.sha1Back(_data.get(f))).
                                        contents();
                        if (!java.util.Objects.equals(str, contents)) {
                            _mn.put(f, "(modified)");
                        }
                    } else {
                        _mn.put(f, "(modified)");
                    }
                } else {
                    _untracked.add(f);
                }
            }
        }
    }

}
