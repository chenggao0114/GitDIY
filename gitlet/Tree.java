package gitlet;

import java.util.HashMap;
import java.io.Serializable;

/** Tree class.
 *  A point to a file group
 *  @author Cheng Gao
 */
class Tree implements Serializable {

    /** Set a MAP. */
    Tree(HashMap<String, String> map) {
        _map = new HashMap<>(map);
    }

    /** map of the Tree. */
    protected HashMap<String, String> _map;

}
