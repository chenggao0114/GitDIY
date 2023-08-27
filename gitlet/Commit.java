package gitlet;

import java.io.Serializable;

/** Commit Class.
 *  @author Cheng Gao
 */
public class Commit implements Serializable {

    /** The message of the commit. */
    private String _message;
    /** @return the commit message. */
    String message() {
        return _message;
    }

    /** The date of the commit. */
    private java.util.Date _timeStamp;
    /** @return time stamp of the commit. */
    java.util.Date timeStamp() {
        return _timeStamp;
    }

    /** The tree of the commit. */
    protected Tree _tree;
    /** @return tree of the commit. */
    java.util.HashMap<String, String> treemap() {
        return _tree._map;
    }

    /** The parent of the commit. */
    private String _parentID;
    /** @return parent of the commit. */
    String parentID() {
        return _parentID;
    }

    /** The merge message for the commit. */
    private String _mergeMessage;
    /** The getter method for parent of the commit.
     * @return the merge commit message; null if not a merge commit.*/
    String mergeMessage() {
        return _mergeMessage;
    }

    /** Construct a commit with
     * TREE, MESSAGE, TIMESTAMP, PARENTID,
     * and MERGEMESSAGE (if has a merge). */
    public Commit(Tree tree, String message, java.util.Date timeStamp,
                  String mergeMessage, String parentID) {
        _tree = tree;
        _message = message;
        _timeStamp = timeStamp;
        _mergeMessage = mergeMessage;
        _parentID = parentID;
    }

}
