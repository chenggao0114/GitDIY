package gitlet;

import java.io.File;
import java.io.Serializable;
import java.io.ByteArrayInputStream;

/** Folder class.
 * @author Cheng Gao
 * */
public class Folder implements Serializable {

    /** The content objects of the folder. */
    private final File objects;

    /** Construct fold with CONTENT inside. */
    Folder(File content) {
        objects = Utils.join(content, "objects");
    }

    /** Judge if initialized.
     * @return */
    public boolean ifInit() {
        return objects.exists();
    }

    /** Find the file with hashcode HASH.
     * @return such file. */
    File hashBack(String hash) {
        String tmp1 = hash.substring(0, 2);
        String tmp2 = hash.substring(2);
        return new File(new File(objects, tmp1), tmp2);
    }

    /** Find the complete commit id with hashcode IDHASH.
     * @return the commit id if exists. */
    static String idBack(String idhash) {
        File allCommits = Utils.join(".gitlet", "commits");
        byte[] tmp = Utils.readContents(allCommits);
        String[] allIds = new String(tmp).split("\n");
        for (String id: allIds) {
            if (id.startsWith(idhash)) {
                return id;
            }
        }
        Utils.message("No commit with that id exists.");
        return null;
    }

    /** Trace an SHA1 to get the object.
     * @return the object with SHA1. */
    Object sha1Back(String sha1) {
        if (sha1 == null) {
            return null;
        } else {
            try (ByteArrayInputStream stream =
                         new ByteArrayInputStream(
                                 Utils.readContents(hashBack(sha1)));
                 java.io.ObjectInput input =
                         new java.io.ObjectInputStream(stream)) {
                return input.readObject();
            } catch (java.io.IOException | ClassNotFoundException e) {
                throw new GitletException(e.getMessage());
            }
        }
    }

    /** Save the bytes B,
     *  and create a file with first two letters of HASH. */
    void save(String hash, byte[] b) {
        String tmp1 = hash.substring(0, 2);
        String tmp2 = hash.substring(2);
        File tmp = new File(objects, tmp1);
        int count = 0;
        while (count != 1) {
            count++;
            tmp.mkdirs();
            Utils.writeContents(new File(tmp, tmp2), b);
        }
    }


}
