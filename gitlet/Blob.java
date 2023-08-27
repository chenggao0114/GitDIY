package gitlet;

import java.io.Serializable;

/** Blob class. Contents of files.
 *  @author Cheng Gao
 */

public class Blob implements Serializable {

    /** The contents in a file. */
    private String contents;

    /** Set STRING to the content. */
    public Blob(String string) {
        contents = string;
    }

    /** Get content.
     * @return contents. */
    public String contents() {
        return contents;
    }

}
