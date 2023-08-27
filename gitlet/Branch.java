package gitlet;

import java.io.File;

/** BranchName Class.
 *  @author Cheng Gao
 */
public class Branch {

    /** Get the pointer for branch named NAME.
     * @return */
    File pointer(String name) {
        String tmp = name.replace('/', File.separatorChar);
        return new File(_pointer, tmp);
    }

    /** to check if a branch named NAME exists.
     * @return true iff exists */
    public boolean exists(String name) {
        return pointer(name).exists();
    }

    /** Construct the branch for the system.
     * POINTER to the file we focus on. */
    public Branch(File pointer) {
        this._pointer = pointer;
    }

    /** Change SHA1 of branch named NAME. */
    void change(String name, String sha1) {
        File file = pointer(name);
        if (!file.exists()) {
            Utils.message("A branch with that name does not exist.");
        } else {
            Utils.writeContents(file, sha1);
        }
    }

    /** Seperate a new branch named NAME,
     *  with a unique id SHA1. */
    void separate(String name, String sha1) {
        File file = pointer(name);
        int ff = file.exists() ? 1 : 0;
        switch (ff) {
        case 1:
            Utils.message("A branch with that name already exists.");
            break;
        case 0:
            Utils.writeContents(file, sha1);
            break;
        default:
            ff = 1 - ff;
        }
    }

    /** Get the sha1 code of branch named NAME.
     * @return return the sha1. */
    String sha1(String name) {
        return Utils.readContentsAsString(pointer(name));
    }

    /** the reference file. */
    private File _pointer;

}
