package gitlet;

import java.io.Serializable;

/** Static class Waste.
 * @author Cheng Gao
 * */
class Waste implements Serializable {

    /** get sha1. */
    private static String sha1 = Utils.sha1(
            Utils.serialize(new Waste()));
    /** @return sha1. */
    static String sha1() {
        return sha1;
    }
}
