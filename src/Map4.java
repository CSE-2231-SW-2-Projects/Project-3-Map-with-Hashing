import java.util.Iterator;
import java.util.NoSuchElementException;

import components.map.Map;
import components.map.Map2;
import components.map.MapSecondary;

/**
 * {@code Map} represented as a hash table using {@code Map}s for the buckets,
 * with implementations of primary methods.
 *
 * @param <K>
 *            type of {@code Map} domain (key) entries
 * @param <V>
 *            type of {@code Map} range (associated value) entries
 * @convention <pre>
 * |$this.hashTable| > 0  and
 * for all i: integer, pf: PARTIAL_FUNCTION, x: K
 *     where (0 <= i  and  i < |$this.hashTable|  and
 *            <pf> = $this.hashTable[i, i+1)  and
 *            x is in DOMAIN(pf))
 *   ([computed result of x.hashCode()] mod |$this.hashTable| = i))  and
 * for all i: integer
 *     where (0 <= i  and  i < |$this.hashTable|)
 *   ([entry at position i in $this.hashTable is not null])  and
 * $this.size = sum i: integer, pf: PARTIAL_FUNCTION
 *     where (0 <= i  and  i < |$this.hashTable|  and
 *            <pf> = $this.hashTable[i, i+1))
 *   (|pf|)
 * </pre>
 * @correspondence <pre>
 * this = union i: integer, pf: PARTIAL_FUNCTION
 *            where (0 <= i  and  i < |$this.hashTable|  and
 *                   <pf> = $this.hashTable[i, i+1))
 *          (pf)
 * </pre>
 *
 * @author Zheyuan Gao
 * @author Cedric Fausey
 *
 */
public class Map4<K, V> extends MapSecondary<K, V> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Default size of hash table.
     */
    private static final int DEFAULT_HASH_TABLE_SIZE = 101;

    /**
     * Buckets for hashing.
     */
    private Map<K, V>[] hashTable;

    /**
     * Total size of abstract {@code this}.
     */
    private int size;

    /**
     * Computes {@code a} mod {@code b} as % should have been defined to work.
     *
     * @param a
     *            the number being reduced
     * @param b
     *            the modulus
     * @return the result of a mod b, which satisfies 0 <= {@code mod} < b
     * @requires b > 0
     * @ensures <pre>
     * 0 <= mod  and  mod < b  and
     * there exists k: integer (a = k * b + mod)
     * </pre>
     */
    private static int mod(int a, int b) {
        assert b > 0 : "Violation of: b > 0";

        int mod = a % b;
        /*
         * If a < 0 and a % b != 0, the result of mod(a, b) should be equal to
         * (a % b) + b, instead of a negative number.
         */
        if (a < 0 && mod != 0) {
            mod += b;
        }
        return mod;
    }

    /**
     * Creator of initial representation.
     *
     * @param hashTableSize
     *            the size of the hash table
     * @requires hashTableSize > 0
     * @ensures <pre>
     * |$this.hashTable| = hashTableSize  and
     * for all i: integer
     *     where (0 <= i  and  i < |$this.hashTable|)
     *   ($this.hashTable[i, i+1) = <{}>)  and
     * $this.size = 0
     * </pre>
     */
    @SuppressWarnings("unchecked")
    private void createNewRep(int hashTableSize) {
        /*
         * With "new Map<K, V>[...]" in place of "new Map[...]" it does not
         * compile; as shown, it results in a warning about an unchecked
         * conversion, though it cannot fail.
         */
        this.hashTable = new Map[hashTableSize];
        /*
         * Set each bucket of the hashTable to be Map2.
         */
        for (int i = 0; i < this.hashTable.length; i++) {
            this.hashTable[i] = new Map2<>();
        }

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Map4() {

        /*
         * Create a new hashTable with DEFAULT_HASH_TABLE_SIZE.
         */
        this.createNewRep(DEFAULT_HASH_TABLE_SIZE);

    }

    /**
     * Constructor resulting in a hash table of size {@code hashTableSize}.
     *
     * @param hashTableSize
     *            size of hash table
     * @requires hashTableSize > 0
     * @ensures this = {}
     */
    public Map4(int hashTableSize) {

        /*
         * Create a new hashTable with the given hashTableSize size.
         */
        this.createNewRep(hashTableSize);

    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Map<K, V> newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep(DEFAULT_HASH_TABLE_SIZE);
    }

    @Override
    public final void transferFrom(Map<K, V> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Map4<?, ?> : ""
                + "Violation of: source is of dynamic type Map4<?,?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Map4<?,?>, and
         * the ?,? must be K,V or the call would not have compiled.
         */
        Map4<K, V> localSource = (Map4<K, V>) source;
        this.hashTable = localSource.hashTable;
        this.size = localSource.size;
        localSource.createNewRep(DEFAULT_HASH_TABLE_SIZE);
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(K key, V value) {
        assert key != null : "Violation of: key is not null";
        assert value != null : "Violation of: value is not null";
        assert !this.hasKey(key) : "Violation of: key is not in DOMAIN(this)";

        /*
         * Using the mod method to decide what bucket in the array this new map
         * pair should go in.
         */
        int bucket = mod(key.hashCode(), this.hashTable.length);
        /*
         * Add the new pair into the map of the bucket location.
         */
        this.hashTable[bucket].add(key, value);
        /*
         * Update the size of the Map4.
         */
        this.size++;

    }

    @Override
    public final Pair<K, V> remove(K key) {
        assert key != null : "Violation of: key is not null";
        assert this.hasKey(key) : "Violation of: key is in DOMAIN(this)";

        /*
         * Using the hashing search to find which bucket should the target in.
         */
        int bucket = mod(key.hashCode(), this.hashTable.length);
        // update the size of Map4.
        this.size--;
        /*
         * Remove and return the pair in the target bucket.
         */
        return this.hashTable[bucket].remove(key);

    }

    @Override
    public final Pair<K, V> removeAny() {
        assert this.size() > 0 : "Violation of: this /= empty_set";
        /*
         * Return the first element of an non-empty bucket. Start from the
         * bucket 0.
         */
        int bucket = 0;
        while (this.hashTable[bucket].size() == 0) {
            bucket++;
        }
        /*
         * Now the bucket indicate the location that has pairs in the bucket.
         * Update the size of Map4.
         */
        this.size--;
        /*
         * Return random one pair from that bucket.
         */
        return this.hashTable[bucket].removeAny();
    }

    @Override
    public final V value(K key) {
        assert key != null : "Violation of: key is not null";
        assert this.hasKey(key) : "Violation of: key is in DOMAIN(this)";

        /*
         * Using the hashing search to find which bucket should the target in.
         */
        int bucket = mod(key.hashCode(), this.hashTable.length);
        /*
         * Return the value of the pair that has the given key.
         */
        return this.hashTable[bucket].value(key);
    }

    @Override
    public final boolean hasKey(K key) {
        assert key != null : "Violation of: key is not null";

        /*
         * Using the hashing search to find which bucket should the target in.
         */
        int bucket = mod(key.hashCode(), this.hashTable.length);
        /*
         * Return whether the bucket has the given key.
         */
        return this.hashTable[bucket].hasKey(key);
    }

    @Override
    public final int size() {

        /*
         * this.size has been updated in add, remove, and removeAny methods. we
         * can just return this.size to return the size of Map4.
         */
        return this.size;
    }

    @Override
    public final Iterator<Pair<K, V>> iterator() {
        return new Map4Iterator();
    }

    /**
     * Implementation of {@code Iterator} interface for {@code Map4}.
     */
    private final class Map4Iterator implements Iterator<Pair<K, V>> {

        /**
         * Number of elements seen already (i.e., |~this.seen|).
         */
        private int numberSeen;

        /**
         * Bucket from which current bucket iterator comes.
         */
        private int currentBucket;

        /**
         * Bucket iterator from which next element will come.
         */
        private Iterator<Pair<K, V>> bucketIterator;

        /**
         * No-argument constructor.
         */
        Map4Iterator() {
            this.numberSeen = 0;
            this.currentBucket = 0;
            this.bucketIterator = Map4.this.hashTable[0].iterator();
        }

        @Override
        public boolean hasNext() {
            return this.numberSeen < Map4.this.size;
        }

        @Override
        public Pair<K, V> next() {
            assert this.hasNext() : "Violation of: ~this.unseen /= <>";
            if (!this.hasNext()) {
                /*
                 * Exception is supposed to be thrown in this case, but with
                 * assertion-checking enabled it cannot happen because of assert
                 * above.
                 */
                throw new NoSuchElementException();
            }
            this.numberSeen++;
            while (!this.bucketIterator.hasNext()) {
                this.currentBucket++;
                this.bucketIterator = Map4.this.hashTable[this.currentBucket]
                        .iterator();
            }
            return this.bucketIterator.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                    "remove operation not supported");
        }

    }

}
