import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Zheyuan Gao
 * @author Cedric Fausey
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /*
     * Test cases for constructor method
     */
    /**
     * Test case for constructor.
     */
    @Test
    public final void testConstructor() {
        Map<String, String> m = this.constructorTest();
        Map<String, String> mExpected = this.constructorRef();

        assertEquals(m, mExpected);
    }

    /*
     * Test cases for add method
     */
    /**
     * Test for add small case.
     */
    @Test
    public final void testAddSmall() {
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();
        m.add("abc", "b");
        mExpected.add("abc", "b");
        assertEquals(m, mExpected);
    }

    /**
     * Test for add routine case.
     */
    @Test
    public final void testAddRoutine1() {
        Map<String, String> m = this.createFromArgsTest("a", "b");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b");
        m.add("abc", "b");
        mExpected.add("abc", "b");
        assertEquals(m, mExpected);
    }

    /**
     * Test for add routine case.
     */
    @Test
    public final void testAddRoutine2() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d");
        m.add("abc", "b");
        mExpected.add("abc", "b");
        assertEquals(m, mExpected);
    }

    /**
     * Test for add routine case.
     */
    @Test
    public final void testAddRoutine3() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d", "e",
                "f");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d", "e", "f");
        m.add("abc", "b");
        mExpected.add("abc", "b");
        assertEquals(m, mExpected);
    }

    /**
     * Test for add challenging case (Upper case letters and Lower case letters
     * as keys).
     */
    @Test
    public final void testAddChallenge() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d", "e",
                "f", "A", "b", "C", "d", "E", "f");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d", "e", "f", "A", "b", "C", "d", "E", "f");
        m.add("G", "b");
        mExpected.add("G", "b");
        assertEquals(m, mExpected);
    }

    /**
     * Test for add large case.
     */
    @Test
    public final void testAddLarge() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d", "e",
                "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
                "s", "t", "u", "v", "w", "x", "y", "z");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
        m.add("abc", "b");
        mExpected.add("abc", "b");
        assertEquals(m, mExpected);
    }

    /*
     * Test cases for remove method
     */
    /**
     * Test for remove small case.
     */
    @Test
    public final void testRemoveSmall() {
        Map<String, String> m = this.createFromArgsTest("abc", "b");
        Map<String, String> mExpected = this.createFromArgsRef("abc", "b");
        assertEquals(m.remove("abc"), mExpected.remove("abc"));
        assertEquals(m, mExpected);
    }

    /**
     * Test for remove routine case.
     */
    @Test
    public final void testRemoveRoutine1() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d");
        assertEquals(m.remove("a"), mExpected.remove("a"));
        assertEquals(m, mExpected);
    }

    /**
     * Test for remove routine case.
     */
    @Test
    public final void testRemoveRoutine2() {
        Map<String, String> m = this.createFromArgsTest("Pizza", "$15", "wings",
                "$7", "cupcake", "$3", "delivery", "$2");
        Map<String, String> mExpected = this.createFromArgsRef("Pizza", "$15",
                "wings", "$7", "cupcake", "$3", "delivery", "$2");
        assertEquals(m.remove("delivery"), mExpected.remove("delivery"));
        assertEquals(m, mExpected);
    }

    /**
     * Test for remove Challenging case (Upper case letters and Lower case
     * letters as keys).
     */
    @Test
    public final void testRemoveChallenge() {
        Map<String, String> m = this.createFromArgsTest("Pizza", "$15", "wings",
                "$7", "cupcake", "$3", "delivery", "$2", "pizza", "$15",
                "Wings", "$7", "Cupcake", "$3", "Delivery", "$2");
        Map<String, String> mExpected = this.createFromArgsRef("Pizza", "$15",
                "wings", "$7", "cupcake", "$3", "delivery", "$2", "pizza",
                "$15", "Wings", "$7", "Cupcake", "$3", "Delivery", "$2");
        assertEquals(m.remove("Delivery"), mExpected.remove("Delivery"));
        assertEquals(m, mExpected);
    }

    /**
     * Test for remove large case.
     */
    @Test
    public final void testRemoveLarge() {
        Map<String, String> m = this.createFromArgsTest("ab", "b", "cd", "d",
                "ef", "f", "gh", "h", "ij", "j", "kl", "l", "mn", "n", "op",
                "p", "qr", "r", "st", "t", "uv", "v", "wx", "x", "yz", "z");
        Map<String, String> mExpected = this.createFromArgsRef("ab", "b", "cd",
                "d", "ef", "f", "gh", "h", "ij", "j", "kl", "l", "mn", "n",
                "op", "p", "qr", "r", "st", "t", "uv", "v", "wx", "x", "yz",
                "z");
        assertEquals(m.remove("cd"), mExpected.remove("cd"));
        assertEquals(m, mExpected);
    }

    /*
     * Test cases for removeAny method
     */
    /**
     * Test for removeAny small case.
     */
    @Test
    public final void testRemoveAnySmall() {
        Map<String, String> m = this.createFromArgsTest("a", "b");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b");
        m.removeAny();
        mExpected.removeAny();
        assertEquals(m, mExpected);
    }

    /**
     * Test for removeAny routine case.
     */
    @Test
    public final void testRemoveAnyRoutine1() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d");
        String key = m.removeAny().key();
        mExpected.remove(key);
        assertEquals(m, mExpected);
    }

    /**
     * Test for removeAny routine case.
     */
    @Test
    public final void testRemoveAnyRoutine2() {
        Map<String, String> m = this.createFromArgsTest("Pizza", "$15", "wings",
                "$7", "cupcake", "$3", "delivery", "$2");
        Map<String, String> mExpected = this.createFromArgsRef("Pizza", "$15",
                "wings", "$7", "cupcake", "$3", "delivery", "$2");
        String key = m.removeAny().key();
        mExpected.remove(key);
        assertEquals(m, mExpected);
    }

    /**
     * Test for removeAny challenging case (Upper case letters and small cases
     * letters as keys).
     */
    @Test
    public final void testRemoveAnyChallenge() {
        Map<String, String> m = this.createFromArgsTest("Pizza", "$15", "wings",
                "$7", "cupcake", "$3", "delivery", "$2", "pizza", "$15",
                "Wings", "$7", "Cupcake", "$3", "Delivery", "$2");
        Map<String, String> mExpected = this.createFromArgsRef("Pizza", "$15",
                "wings", "$7", "cupcake", "$3", "delivery", "$2", "pizza",
                "$15", "Wings", "$7", "Cupcake", "$3", "Delivery", "$2");
        String key = m.removeAny().key();
        mExpected.remove(key);
        assertEquals(m, mExpected);
    }

    /**
     * Test for removeAny large case.
     */
    @Test
    public final void testRemoveAnyLarge() {
        Map<String, String> m = this.createFromArgsTest("ab", "b", "cd", "d",
                "ef", "f", "gh", "h", "ij", "j", "kl", "l", "mn", "n", "op",
                "p", "qr", "r", "st", "t", "uv", "v", "wx", "x", "yz", "z");
        Map<String, String> mExpected = this.createFromArgsRef("ab", "b", "cd",
                "d", "ef", "f", "gh", "h", "ij", "j", "kl", "l", "mn", "n",
                "op", "p", "qr", "r", "st", "t", "uv", "v", "wx", "x", "yz",
                "z");
        String key = m.removeAny().key();
        mExpected.remove(key);
        assertEquals(m, mExpected);
    }

    /*
     * Test cases for value method
     */
    /**
     * Test for value small case.
     */
    @Test
    public final void testValueSmall() {
        Map<String, String> m = this.createFromArgsTest("abc", "b");
        Map<String, String> mExpected = this.createFromArgsRef("abc", "b");
        assertEquals(m.value("abc"), mExpected.value("abc"));
        assertEquals(m, mExpected);
    }

    /**
     * Test for value routine case.
     */
    @Test
    public final void testValueRoutine1() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d");
        assertEquals(m.value("a"), mExpected.value("a"));
        assertEquals(m, mExpected);
    }

    /**
     * Test for value routine case.
     */
    @Test
    public final void testValueRoutine2() {
        Map<String, String> m = this.createFromArgsTest("Pizza", "$15", "wings",
                "$7", "cupcake", "$3", "delivery", "$2");
        Map<String, String> mExpected = this.createFromArgsRef("Pizza", "$15",
                "wings", "$7", "cupcake", "$3", "delivery", "$2");
        assertEquals(m.value("wings"), mExpected.value("wings"));
        assertEquals(m, mExpected);
    }

    /**
     * Test for value routine case.
     */
    @Test
    public final void testValueRoutine3() {
        Map<String, String> m = this.createFromArgsTest("Pizza", "$15", "wings",
                "$7", "cupcake", "$3", "delivery", "$2");
        Map<String, String> mExpected = this.createFromArgsRef("Pizza", "$15",
                "wings", "$7", "cupcake", "$3", "delivery", "$2");
        assertEquals(m.value("Pizza"), mExpected.value("Pizza"));
        assertEquals(m, mExpected);
    }

    /**
     * Test for value large case.
     */
    @Test
    public final void testValueLarge() {
        Map<String, String> m = this.createFromArgsTest("ab", "b", "cd", "d",
                "ef", "f", "gh", "h", "ij", "j", "kl", "l", "mn", "n", "op",
                "p", "qr", "r", "st", "t", "uv", "v", "wx", "x", "yz", "z");
        Map<String, String> mExpected = this.createFromArgsRef("ab", "b", "cd",
                "d", "ef", "f", "gh", "h", "ij", "j", "kl", "l", "mn", "n",
                "op", "p", "qr", "r", "st", "t", "uv", "v", "wx", "x", "yz",
                "z");

        assertEquals(m.value("ij"), mExpected.value("ij"));
        assertEquals(m, mExpected);
    }

    /*
     * Test cases for hasKey method
     */
    /**
     * Test for hasKey small case.
     */
    @Test
    public final void testHasKeySmall() {
        Map<String, String> m = this.createFromArgsTest("abc", "b");
        Map<String, String> mExpected = this.createFromArgsRef("abc", "b");
        assertEquals(m.hasKey("abc"), mExpected.hasKey("abc"));
        assertEquals(m, mExpected);
    }

    /**
     * Test for hasKey routine case (True).
     */
    @Test
    public final void testHasKeyRoutine1() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d");
        assertEquals(m.hasKey("a"), mExpected.hasKey("a"));
        assertEquals(m, mExpected);
    }

    /**
     * Test for hasKey routine case (False).
     */
    @Test
    public final void testHasKeyRoutine2() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d");
        assertEquals(m.hasKey("s"), mExpected.hasKey("s"));
        assertEquals(m, mExpected);
    }

    /**
     * Test for hasKey routine case (True).
     */
    @Test
    public final void testHasKeyRoutine3() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d",
                "abc", "bca", "A", "a", "B", "c");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d", "abc", "bca", "A", "a", "B", "c");
        assertEquals(m.hasKey("abc"), mExpected.hasKey("abc"));
        assertEquals(m, mExpected);
    }

    /**
     * Test for hasKey routine case (False).
     */
    @Test
    public final void testHasKeyRoutine4() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d",
                "abc", "bca", "A", "a", "B", "c");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d", "abc", "bca", "A", "a", "B", "c");
        assertEquals(m.hasKey("C"), mExpected.hasKey("C"));
        assertEquals(m, mExpected);
    }

    /**
     * Test for hasKey large case (True).
     */
    @Test
    public final void testHasKeyLarge1() {
        Map<String, String> m = this.createFromArgsTest("ab", "b", "cd", "d",
                "ef", "f", "gh", "h", "ij", "j", "kl", "l", "mn", "n", "op",
                "p", "qr", "r", "st", "t", "uv", "v", "wx", "x", "yz", "z");
        Map<String, String> mExpected = this.createFromArgsRef("ab", "b", "cd",
                "d", "ef", "f", "gh", "h", "ij", "j", "kl", "l", "mn", "n",
                "op", "p", "qr", "r", "st", "t", "uv", "v", "wx", "x", "yz",
                "z");

        assertEquals(m.hasKey("ij"), mExpected.hasKey("ij"));
        assertEquals(m, mExpected);
    }

    /**
     * Test for hasKey large case (False).
     */
    @Test
    public final void testHasKeyLarge2() {
        Map<String, String> m = this.createFromArgsTest("ab", "b", "cd", "d",
                "ef", "f", "gh", "h", "ij", "j", "kl", "l", "mn", "n", "op",
                "p", "qr", "r", "st", "t", "uv", "v", "wx", "x", "yz", "z");
        Map<String, String> mExpected = this.createFromArgsRef("ab", "b", "cd",
                "d", "ef", "f", "gh", "h", "ij", "j", "kl", "l", "mn", "n",
                "op", "p", "qr", "r", "st", "t", "uv", "v", "wx", "x", "yz",
                "z");

        assertEquals(m.hasKey("i"), mExpected.hasKey("i"));
        assertEquals(m, mExpected);
    }

    /*
     * Test cases for size method
     */
    /**
     * Test for size small case.
     */
    @Test
    public final void testSizeSmall() {
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();
        assertEquals(m.size(), mExpected.size());
    }

    /**
     * Test for size routine case.
     */
    @Test
    public final void testSizeRoutine1() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d");
        assertEquals(m.size(), mExpected.size());
    }

    /**
     * Test for size routine case.
     */
    @Test
    public final void testSizeRoutine2() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d",
                "abc", "bca", "A", "a", "B", "c");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d", "abc", "bca", "A", "a", "B", "c");
        assertEquals(m.size(), mExpected.size());
    }

    /**
     * Test for size routine case.
     */
    @Test
    public final void testSizeRoutine3() {
        Map<String, String> m = this.createFromArgsTest("Pizza", "$15", "wings",
                "$7", "cupcake", "$3", "delivery", "$2", "pizza", "$15",
                "Wings", "$7", "Cupcake", "$3", "Delivery", "$2");
        Map<String, String> mExpected = this.createFromArgsRef("Pizza", "$15",
                "wings", "$7", "cupcake", "$3", "delivery", "$2", "pizza",
                "$15", "Wings", "$7", "Cupcake", "$3", "Delivery", "$2");
        assertEquals(m.size(), mExpected.size());
    }

    /**
     * Test for size large case.
     */
    @Test
    public final void testSizeLarge() {
        Map<String, String> m = this.createFromArgsTest("ab", "b", "cd", "d",
                "ef", "f", "gh", "h", "ij", "j", "kl", "l", "mn", "n", "op",
                "p", "qr", "r", "st", "t", "uv", "v", "wx", "x", "yz", "z");
        Map<String, String> mExpected = this.createFromArgsRef("ab", "b", "cd",
                "d", "ef", "f", "gh", "h", "ij", "j", "kl", "l", "mn", "n",
                "op", "p", "qr", "r", "st", "t", "uv", "v", "wx", "x", "yz",
                "z");

        assertEquals(m.size(), mExpected.size());
    }

}
