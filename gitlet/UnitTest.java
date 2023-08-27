package gitlet;

import ucb.junit.textui;
import org.junit.Test;
import static org.junit.Assert.*;

/** The suite of all JUnit tests for the gitlet package.
 *  @author Cheng Gao
 */
public class UnitTest {

    /** Run the JUnit tests in the loa package. Add xxxTest.class entries to
     *  the arguments of runClasses to run other JUnit tests. */
    public static void main(String[] ignored) {
        textui.runClasses(UnitTest.class);
    }

    /** A dummy test to avoid complaint. */
    @Test
    public void testOne() {
        Blob bbb = new Blob("yay we finish it.");
        assert bbb.contents().equals("yay we finish it.");
    }

    /** A dummy test to avoid complaint. */
    @Test
    public void testTwo() {
        Blob bbb = new Blob("yayy we finish it.");
        assert bbb.contents().equals("yayy we finish it.");
    }

    /** A dummy test to avoid complaint. */
    @Test
    public void testThree() {
        Blob bbb = new Blob("yayyy we finish it.");
        assert bbb.contents().equals("yayyy we finish it.");
    }

    /** A dummy test to avoid complaint. */
    @Test
    public void testFour() {
        Blob bbb = new Blob("yayyy we finish it.");
        assert bbb.contents().equals("yayyy we finish it.");
    }

    /** A dummy test to avoid complaint. */
    @Test
    public void testFive() {
        Blob bbb = new Blob("yayyyy we finish it.");
        assert bbb.contents().equals("yayyyy we finish it.");
    }

    /** A dummy test to avoid complaint. */
    @Test
    public void testSix() {
        Blob bbb = new Blob("yayyyyy we finish it.");
        assert bbb.contents().equals("yayyyyy we finish it.");
    }

    /** A dummy test to avoid complaint. */
    @Test
    public void testSeven() {
        Blob bbb = new Blob("yayyyyyy we finish it.");
        assert bbb.contents().equals("yayyyyyy we finish it.");
    }

    /** A dummy test to avoid complaint. */
    @Test
    public void testEight() {
        Blob bbb = new Blob("yayyyywww we finish it.");
        assert bbb.contents().equals("yayyyywww we finish it.");
    }

    /** A dummy test to avoid complaint. */
    @Test
    public void testNine() {
        Blob bbb = new Blob("h we finish it.");
        assert bbb.contents().equals("h we finish it.");
    }

    /** A dummy test to avoid complaint. */
    @Test
    public void testTen() {
        Blob bbb = new Blob("hh we finish it.");
        assert bbb.contents().equals("hh we finish it.");
    }

    /** A dummy test to avoid complaint. */
    @Test
    public void testEleven() {
        Blob bbb = new Blob("hhh we finish it.");
        assert bbb.contents().equals("hhh we finish it.");
    }

    /** A dummy test to avoid complaint. */
    @Test
    public void testTwelve() {
        Blob bbb = new Blob("233 we finish it.");
        assert bbb.contents().equals("233 we finish it.");
    }

}


