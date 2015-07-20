package java.util;
import java.io.*;

public class HashMap<K,V>
    extends AbstractMap<K,V>
    implements Map<K,V>, Cloneable, Serializable
{

    // ϵͳĬ�ϳ�ʼ������������2��n���ݣ����ǳ����Ż����ǵ�
    static final int DEFAULT_INITIAL_CAPACITY = 16;

    // ϵͳĬ���������
    static final int MAXIMUM_CAPACITY = 1 << 30;

    // ϵͳĬ�ϸ������ӣ����ڹ��캯����ָ��
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    // ���ڴ洢�ı����ȿ��Ե������ұ�����2��n����
    transient Entry[] table;

    // ��ǰmap��key-valueӳ������Ҳ���ǵ�ǰsize
    transient int size;

    // ��ֵ
    int threshold;

    // ��ϣ��ĸ�������
    final float loadFactor;

    // ����ȷ��ʹ�õ�������ʱ��HashMap��δ���и���
    transient volatile int modCount;

    // ����һ����ָ����ʼ�����ͼ������ӵĿ� HashMap��
    public HashMap(int initialCapacity, float loadFactor) {
        // ���ָ����ʼ����С��0���״�
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                                               initialCapacity);
        // �����ʼ��������ϵͳĬ��������������ʼ����Ϊ�������
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        // ���loadFactorС��0����loadFactor��NaN�����״�
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                                               loadFactor);

        // Ѱ��һ��2��k����capacityǡ�ô���initialCapacity
        int capacity = 1;
        while (capacity < initialCapacity)
            capacity <<= 1;

        // ���ü�������
        this.loadFactor = loadFactor;
        // ������ֵΪcapacity * loadFactor��ʵ���ϵ�HashMap��ǰsize���������ֵʱ��HashMap����Ҫ����һ���ˡ�
        threshold = (int)(capacity * loadFactor);
        // ����һ��capacity���ȵ��������ڱ�������
        table = new Entry[capacity];
        // ��ʼ��ʼ��
        init();
    }

    // ����һ����ָ����ʼ������Ĭ�ϼ������� (0.75) �Ŀ� HashMap��
    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    // ����һ������Ĭ�ϳ�ʼ���� (16) ��Ĭ�ϼ������� (0.75) �Ŀ� HashMap��
    public HashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        threshold = (int)(DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
        table = new Entry[DEFAULT_INITIAL_CAPACITY];
        init();
    }

    // ����һ��ӳ���ϵ��ָ�� Map ��ͬ���� HashMap��
    public HashMap(Map<? extends K, ? extends V> m) {
        this(Math.max((int) (m.size() / DEFAULT_LOAD_FACTOR) + 1,
                      DEFAULT_INITIAL_CAPACITY), DEFAULT_LOAD_FACTOR);
        putAllForCreate(m);
    }

    // �ڲ����ù���

    // ����һ���շ�������δ�����Ӷ�����չ���÷������ڳ�ʼ��֮�󣬲���Ԫ��֮ǰ
    void init() {
    }

    // Ԥ����hashֵ������ϲ����ɢhash���У�����Ͱû�г������
    static int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    // ���ض�Ӧhashֵ������
    static int indexFor(int h, int length) {
        /*****************
         * ����length��2��n���ݣ�����h & (length-1)�൱��h % length��
         * ����length����2���Ʊ�ʾΪ1000...0����ôlength-1Ϊ0111...1��
         * ��ô�����κ�С��length����h����ʽ��������䱾��h��
         * ����h = length����ʽ�������0��
         * ���ڴ���length����h�����0111...1λ�������
         * ��0111...1�߻��߳�����ͬ��λ�����0��
         * �൱�ڼ�ȥj��length����ʽ�����h-j*length��
         * �����൱��h % length��
         * ����һ���ܳ��õ���������h & 1�൱��h % 2��
         * ��Ҳ��Ϊʲôlengthֻ����2��n���ݵ�ԭ��Ϊ���Ż���
         */
        return h & (length-1);
    }

    // ���ص�ǰmap��key-valueӳ������Ҳ���ǵ�ǰsize
    public int size() {
        return size;
    }

    // ��HashMap�Ƿ��ǿյģ����sizeΪ0����Ϊ��
    public boolean isEmpty() {
        return size == 0;
    }

    // ����ָ������ӳ���ֵ��������ڸü���˵����ӳ�䲻�����κ�ӳ���ϵ���򷵻� null
    public V get(Object key) {
        // ���keyΪnull
        if (key == null)
            return getForNullKey();
        // ��hashCodeֵԤ����
        int hash = hash(key.hashCode());
        // �õ���Ӧ��hashֵ��Ͱ��������Ͱ���ǣ���ͨ��next��ȡ��һ��Ͱ
        for (Entry<K,V> e = table[indexFor(hash, table.length)];
             e != null;
             e = e.next) {
            Object k;
            // ���hashֵ��ȣ�����key�����֤�����Ͱ��Ķ�����������Ҫ��
            if (e.hash == hash && ((k = e.key) == key || key.equals(k)))
                return e.value;
        }
        // ����Ͱ���ұ��ˣ�û�ҵ���Ҫ�ģ����Է���null
        return null;
    }

    // ���Ҫ�õ�keyΪnull��ֵ����ͨ�������ȡ
    private V getForNullKey() {
        // ����table[0]�������Ͱ
        for (Entry<K,V> e = table[0]; e != null; e = e.next) {
            // ����Ͱ��key�ǲ���null�����򷵻���Ӧֵ
            if (e.key == null)
                return e.value;
        }
        // û�ҵ�����null
        return null;
    }

    // �����ӳ���������ָ������ӳ���ϵ���򷵻�true
    public boolean containsKey(Object key) {
        return getEntry(key) != null;
    }

    // ͨ��key��ȡһ��value
    final Entry<K,V> getEntry(Object key) {
        // ���keyΪnull����hashΪ0��������hash����Ԥ����
        int hash = (key == null) ? 0 : hash(key.hashCode());
        // �õ���Ӧ��hashֵ��Ͱ��������Ͱ���ǣ���ͨ��next��ȡ��һ��Ͱ
        for (Entry<K,V> e = table[indexFor(hash, table.length)];
             e != null;
             e = e.next) {
            Object k;
            // ���hashֵ��ȣ�����key�����֤�����Ͱ��Ķ�����������Ҫ��
            if (e.hash == hash &&
                ((k = e.key) == key || (key != null && key.equals(k))))
                return e;
        }
        // ����Ͱ���ұ��ˣ�û�ҵ���Ҫ�ģ����Է���null
        return null;
    }


    // �ڴ�ӳ���й���ָ��ֵ��ָ�����������ӳ����ǰ������һ���ü���ӳ���ϵ�����ֵ���滻
    public V put(K key, V value) {
        // ���keyΪnullʹ��putForNullKey����ȡ
        if (key == null)
            return putForNullKey(value);
        // ʹ��hash����Ԥ����hashCode
        int hash = hash(key.hashCode());
        // ��ȡ��Ӧ������
        int i = indexFor(hash, table.length);
        // �õ���Ӧ��hashֵ��Ͱ��������Ͱ���ǣ���ͨ��next��ȡ��һ��Ͱ
        for (Entry<K,V> e = table[i]; e != null; e = e.next) {
            Object k;
            // ���hash��ͬ����key��ͬ
            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                // ��ȡ��ǰ��value
                V oldValue = e.value;
                // ��Ҫ�洢��value���ȥ
                e.value = value;
                e.recordAccess(this);
                // ���ؾɵ�value
                return oldValue;
            }
        }

        modCount++;
        addEntry(hash, key, value, i);
        return null;
    }

    // keyΪnull��ô��value
    private V putForNullKey(V value) {
        // ����table[0]������Ͱ
        for (Entry<K,V> e = table[0]; e != null; e = e.next) {
            // ���key��null
            if (e.key == null) {
                // ȡ��oldValue��������value
                V oldValue = e.value;
                e.value = value;
                e.recordAccess(this);
                // ����oldValue
                return oldValue;
            }
        }
        modCount++;
        addEntry(0, null, value, 0);
        return null;
    }

    // �����費��Ҫ�����µ�Ͱ
    private void putForCreate(K key, V value) {
        // ���keyΪnull������hashΪ0��������hash����Ԥ����
        int hash = (key == null) ? 0 : hash(key.hashCode());
        // ��ȡ��Ӧ������
        int i = indexFor(hash, table.length);

        // ��������Ͱ
        for (Entry<K,V> e = table[i]; e != null; e = e.next) {
            Object k;
            // �����hash��ͬ����key��ͬ����ô����Ҫ�����µ�Ͱ���˳�
            if (e.hash == hash &&
                ((k = e.key) == key || (key != null && key.equals(k)))) {
                e.value = value;
                return;
            }
        }

        // ������Ҫ�����µ�Ͱ
        createEntry(hash, key, value, i);
    }

    // ����Map�������ж�Ӧ��Ͱ
    private void putAllForCreate(Map<? extends K, ? extends V> m) {
        for (Iterator<? extends Map.Entry<? extends K, ? extends V>> i = m.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry<? extends K, ? extends V> e = i.next();
            putForCreate(e.getKey(), e.getValue());
        }
    }

    // �����µ�������resize���HashMap
    void resize(int newCapacity) {
        // ����oldTable
        Entry[] oldTable = table;
        // ����ɵ�����
        int oldCapacity = oldTable.length;
        // ����ɵ������Ѿ���ϵͳĬ����������ˣ���ô����ֵ���ó����ε����ֵ���˳�
        if (oldCapacity == MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }

        // �����µ������½�һ��table
        Entry[] newTable = new Entry[newCapacity];
        // ��tableת����newTable
        transfer(newTable);
        // ��table����newTable
        table = newTable;
        // ������ֵ
        threshold = (int)(newCapacity * loadFactor);
    }

    // �����и������Ͱ���ŵ��µ�table��
    void transfer(Entry[] newTable) {
        // �õ��ɵ�table
        Entry[] src = table;
        // �õ��µ�����
        int newCapacity = newTable.length;
        // ����src��������и���
        for (int j = 0; j < src.length; j++) {
            // ȡ���������Ͱ��Ҳ��������
            Entry<K,V> e = src[j];
            // ���e��Ϊ��
            if (e != null) {
                // ����ǰ�������null
                src[j] = null;
                // �������ӵ�����Ͱ
                do {
                    // ȡ���¸�Ͱ
                    Entry<K,V> next = e.next;
                    // Ѱ���µ�����
                    int i = indexFor(e.hash, newCapacity);
                    // ����e.nextΪnewTable[i]�����Ͱ��Ҳ�������������ϣ�
                    e.next = newTable[i];
                    // ��e���newTable[i]
                    newTable[i] = e;
                    // ����eΪ��һ��Ͱ
                    e = next;
                } while (e != null);
            }
        }
    }

    // ��ָ��ӳ�������ӳ���ϵ���Ƶ���ӳ���У���Щӳ���ϵ���滻��ӳ��Ŀǰ���ָ��ӳ�������м�������ӳ���ϵ
    public void putAll(Map<? extends K, ? extends V> m) {
        // ������Ҫ���ƶ��ٸ�ӳ���ϵ
        int numKeysToBeAdded = m.size();
        if (numKeysToBeAdded == 0)
            return;

        // ���Ҫ���Ƶ�ӳ���ϵ����ֵ��Ҫ��
        if (numKeysToBeAdded > threshold) {
            // ���¼����µ�������resize
            int targetCapacity = (int)(numKeysToBeAdded / loadFactor + 1);
            if (targetCapacity > MAXIMUM_CAPACITY)
                targetCapacity = MAXIMUM_CAPACITY;
            int newCapacity = table.length;
            while (newCapacity < targetCapacity)
                newCapacity <<= 1;
            if (newCapacity > table.length)
                resize(newCapacity);
        }

        // ������key-valueӳ��Ž���HashMap
        for (Iterator<? extends Map.Entry<? extends K, ? extends V>> i = m.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry<? extends K, ? extends V> e = i.next();
            put(e.getKey(), e.getValue());
        }
    }

    // �Ӵ�ӳ�����Ƴ�ָ������ӳ���ϵ��������ڣ�
    public V remove(Object key) {
        Entry<K,V> e = removeEntryForKey(key);
        return (e == null ? null : e.value);
    }

    // ����keyɾ��Ͱ�������ض�Ӧvalue
    final Entry<K,V> removeEntryForKey(Object key) {
        int hash = (key == null) ? 0 : hash(key.hashCode());
        int i = indexFor(hash, table.length);
        // �ҵ���Ӧ�ĸ���
        Entry<K,V> prev = table[i];
        Entry<K,V> e = prev;

        // ��������Ͱ
        while (e != null) {
            Entry<K,V> next = e.next;
            Object k;
            // ����ҵ���Ӧ��Ͱ
            if (e.hash == hash &&
                ((k = e.key) == key || (key != null && key.equals(k)))) {
                modCount++;
                // size��1
                size--;
                // �����һ������Ҫɾ��Ͱ
                if (prev == e)
                    // ��table[i]������һ��Ͱ
                    table[i] = next;
                else
                    // ������һ��Ͱ����һ��������һ��Ͱ
                    prev.next = next;
                e.recordRemoval(this);
                return e;
            }
            prev = e;
            e = next;
        }

        return e;
    }

    // ����Ͱ��ɾ��map���ֵ
    final Entry<K,V> removeMapping(Object o) {
        // ���o����Map.Entry��ʵ�������˳�����null
        if (!(o instanceof Map.Entry))
            return null;

        // ��oת��Map.Entry
        Map.Entry<K,V> entry = (Map.Entry<K,V>) o;
        // �õ�����key
        Object key = entry.getKey();
        // �õ���Ӧ��hash
        int hash = (key == null) ? 0 : hash(key.hashCode());
        // �õ���Ӧ������
        int i = indexFor(hash, table.length);
        Entry<K,V> prev = table[i];
        Entry<K,V> e = prev;

        // ��������Ͱ
        while (e != null) {
            Entry<K,V> next = e.next;
            // ����ҵ���Ӧ��Ͱ����ɾ����
            if (e.hash == hash && e.equals(entry)) {
                modCount++;
                size--;
                if (prev == e)
                    table[i] = next;
                else
                    prev.next = next;
                e.recordRemoval(this);
                return e;
            }
            prev = e;
            e = next;
        }

        // �����ظ�Ͱ
        return e;
    }

    // �Ӵ�ӳ�����Ƴ�����ӳ���ϵ���˵��÷��غ�ӳ�佫Ϊ��
    public void clear() {
        modCount++;
        Entry[] tab = table;
        // ����table�е����и��ӣ�Ȼż����Ϊnull
        for (int i = 0; i < tab.length; i++)
            tab[i] = null;
        // ����sizeΪ0
        size = 0;
    }

    // �����ӳ�佫һ��������ӳ�䵽ָ��ֵ���򷵻� true
    public boolean containsValue(Object value) {
    // ���valueΪ�գ��򷵻�containsNullValue�����ķ���ֵ
    if (value == null)
            return containsNullValue();

    Entry[] tab = table;
        // ����table���и��ӣ�����
        for (int i = 0; i < tab.length ; i++)
            // ���������е�ÿ��Ͱ
            for (Entry e = tab[i] ; e != null ; e = e.next)
                // ���ֵ��ͬ���򷵻�true
                if (value.equals(e.value))
                    return true;
    // ���򷵻�false
    return false;
    }

    // ��valueΪnull�Ĵ�������ûʲô�ر��
    private boolean containsNullValue() {
    Entry[] tab = table;
        for (int i = 0; i < tab.length ; i++)
            for (Entry e = tab[i] ; e != null ; e = e.next)
                if (e.value == null)
                    return true;
    return false;
    }

    // ���ش� HashMap ʵ����ǳ�������������Ƽ���ֵ����
    public Object clone() {
        HashMap<K,V> result = null;
    try {
        result = (HashMap<K,V>)super.clone();
    } catch (CloneNotSupportedException e) {
        // assert false;
    }
        result.table = new Entry[table.length];
        result.entrySet = null;
        result.modCount = 0;
        result.size = 0;
        result.init();
        result.putAllForCreate(this);

        return result;
    }

    // ����class�������Ҳ��������˵��Ͱ
    static class Entry<K,V> implements Map.Entry<K,V> {
        final K key;
        V value;
        Entry<K,V> next;
        final int hash;

        // ���캯��
        Entry(int h, K k, V v, Entry<K,V> n) {
            value = v;
            next = n;
            key = k;
            hash = h;
        }
        
        // ����key
        public final K getKey() {
            return key;
        }

        // ����value
        public final V getValue() {
            return value;
        }

        // ����value
        public final V setValue(V newValue) {
        V oldValue = value;
            value = newValue;
            return oldValue;
        }

        // �Ƿ���ͬ
        public final boolean equals(Object o) {
            // ���o����Map.Entry��ʵ������ô�϶�����ͬ��
            if (!(o instanceof Map.Entry))
                return false;
            // ��oת��Map.Entry
            Map.Entry e = (Map.Entry)o;
            // �õ�key��value�Ա��Ƿ���ͬ����ͬ��Ϊtrue
            Object k1 = getKey();
            Object k2 = e.getKey();
            if (k1 == k2 || (k1 != null && k1.equals(k2))) {
                Object v1 = getValue();
                Object v2 = e.getValue();
                if (v1 == v2 || (v1 != null && v1.equals(v2)))
                    return true;
            }
            // ����Ϊfalse
            return false;
        }

        // hashCode
        public final int hashCode() {
            return (key==null   ? 0 : key.hashCode()) ^
                   (value==null ? 0 : value.hashCode());
        }

        // ����String
        public final String toString() {
            return getKey() + "=" + getValue();
        }

        // ʹ�ø÷���֤����key�Ѿ��ڸ�map��
        void recordAccess(HashMap<K,V> m) {
        }

        // �÷�����¼��key�Ѿ����Ƴ���
        void recordRemoval(HashMap<K,V> m) {
        }
    }

    // ���һ���µ�Ͱ�������key��value
    void addEntry(int hash, K key, V value, int bucketIndex) {
    // �����Ӧtable��ֵ
    Entry<K,V> e = table[bucketIndex];
        // Ȼ�����µ�Ͱ��ס�ɵ�Ͱ������
        table[bucketIndex] = new Entry<K,V>(hash, key, value, e);
        // �����ǰsize���ڵ�����ֵ
        if (size++ >= threshold)
            // ��������
            resize(2 * table.length);
    }

    // �½�һ��Ͱ���÷�������Ҫ�ж��Ƿ񳬹���ֵ
    void createEntry(int hash, K key, V value, int bucketIndex) {
    Entry<K,V> e = table[bucketIndex];
        table[bucketIndex] = new Entry<K,V>(hash, key, value, e);
        size++;
    }

    // �ڲ�class HashIterator������
    private abstract class HashIterator<E> implements Iterator<E> {
        Entry<K,V> next;    // ��һ��Ͱ
        int expectedModCount;    // ����HashMapû�б��
        int index;        // ��ǰ������
        Entry<K,V> current;    // ��ǰ��Ͱ

        // ���췽��
        HashIterator() {
            // ����modCount����Ϊ���HashMap�������κβ���modCount�������ӣ������������modCount�仯�ˣ��Ϳ����׳�ʧ��
            expectedModCount = modCount;
            // �����һ��Ͱ
            if (size > 0) {
                Entry[] t = table;
                while (index < t.length && (next = t[index++]) == null)
                    ;
            }
        }

        // ������û����һ��Ͱ
        public final boolean hasNext() {
            return next != null;
        }

        // ��ȡ��һ��Ͱ
        final Entry<K,V> nextEntry() {
            // modCount�仯�ˣ��׳�ʧ��
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            // �õ�next
            Entry<K,V> e = next;
            // ���nextΪ�գ��׳�ʧ��
            if (e == null)
                throw new NoSuchElementException();

            // ���next.nextΪ�գ���next����Ϊ��һ�������е�Ͱ������Ϊ�ø��ӵ���һ��Ͱ
            if ((next = e.next) == null) {
                Entry[] t = table;
                while (index < t.length && (next = t[index++]) == null)
                    ;
            }
        // ��current��ֵ
        current = e;
            // ����e
            return e;
        }

        // ɾ��
        public void remove() {
            // �����ǰΪ�գ��׳�
            if (current == null)
                throw new IllegalStateException();
            // modCount�仯�ˣ��׳�ʧ��
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            // ��õ�ǰ��key
            Object k = current.key;
            // ����currentΪnull
            current = null;
            // ɾ������Ӧkey��Ԫ��
            HashMap.this.removeEntryForKey(k);
            // ����expectedModCount
            expectedModCount = modCount;
        }

    }

    // �ڲ�class ValueIterator�����������ǿ��Կ����޸���next����
    private final class ValueIterator extends HashIterator<V> {
        public V next() {
            return nextEntry().value;
        }
    }

    // �ڲ�class KeyIterator�����������ǿ��Կ����޸���next����
    private final class KeyIterator extends HashIterator<K> {
        public K next() {
            return nextEntry().getKey();
        }
    }

    // �ڲ�class EntryIterator�����������ǿ��Կ����޸���next����
    private final class EntryIterator extends HashIterator<Map.Entry<K,V>> {
        public Map.Entry<K,V> next() {
            return nextEntry();
        }
    }

    // �����Ӧ�� iterator() ����
    Iterator<K> newKeyIterator()   {
        return new KeyIterator();
    }
    Iterator<V> newValueIterator()   {
        return new ValueIterator();
    }
    Iterator<Map.Entry<K,V>> newEntryIterator()   {
        return new EntryIterator();
    }

    private transient Set<Map.Entry<K,V>> entrySet = null;

    /**
     * ���ش�ӳ�����������ļ��� Set ��ͼ��
     * �� set ��ӳ���֧�֣����Զ�ӳ��ĸ��Ľ���ӳ�ڸ� set �У�
     * ��֮��Ȼ������ڶ� set ���е�����ͬʱ�޸���ӳ�䣨ͨ���������Լ��� remove �������⣩��
     * ���������ǲ�ȷ���ġ��� set ֧��Ԫ�ص��Ƴ���ͨ�� 
     * Iterator.remove��Set.remove��removeAll��retainAll �� clear ����
     * �ɴӸ�ӳ�����Ƴ���Ӧ��ӳ���ϵ������֧�� add �� addAll ������
     */
    public Set<K> keySet() {
        Set<K> ks = keySet;
        // ���keySetΪ�գ���ͨ���½�һ��KeySet
        return (ks != null ? ks : (keySet = new KeySet()));
    }

    // �ڲ���KeySet
    private final class KeySet extends AbstractSet<K> {
        // ����iterator����
        public Iterator<K> iterator() {
            return newKeyIterator();
        }
        // ����size
        public int size() {
            return size;
        }
        // ����contains
        public boolean contains(Object o) {
            return containsKey(o);
        }
        // ����remove
        public boolean remove(Object o) {
            return HashMap.this.removeEntryForKey(o) != null;
        }
        // ����clear
        public void clear() {
            HashMap.this.clear();
        }
    }

    /**
     * ���ش�ӳ����������ֵ�� Collection ��ͼ��
     * �� collection ��ӳ���֧�֣����Զ�ӳ��ĸ��Ľ���ӳ�ڸ� collection �У�
     * ��֮��Ȼ������ڶ� collection ���е�����ͬʱ�޸���ӳ�䣨ͨ���������Լ��� remove �������⣩��
     * ���������ǲ�ȷ���ġ��� collection ֧��Ԫ�ص��Ƴ���
     * ͨ�� Iterator.remove��Collection.remove��removeAll��retainAll �� clear ����
     * �ɴӸ�ӳ�����Ƴ���Ӧ��ӳ���ϵ������֧�� add �� addAll ������
     */
    public Collection<V> values() {
        Collection<V> vs = values;
        return (vs != null ? vs : (values = new Values()));
    }

    // �ڲ���Values
    private final class Values extends AbstractCollection<V> {
        public Iterator<V> iterator() {
            return newValueIterator();
        }
        public int size() {
            return size;
        }
        public boolean contains(Object o) {
            return containsValue(o);
        }
        public void clear() {
            HashMap.this.clear();
        }
    }

    /**
     * ���ش�ӳ����������ӳ���ϵ�� Set ��ͼ�� 
     * �� set ��ӳ��֧�֣����Զ�ӳ��ĸ��Ľ���ӳ�ڴ� set �У�
     * ��֮��Ȼ������ڶ� set ���е�����ͬʱ�޸���ӳ��
     * ��ͨ���������Լ��� remove ����������ͨ���ڸõ��������ص�ӳ������ִ�� setValue �������⣩��
     * ���������ǲ�ȷ���ġ��� set ֧��Ԫ�ص��Ƴ���
     * ͨ�� Iterator.remove��Set.remove��removeAll��retainAll �� clear ����
     * �ɴӸ�ӳ�����Ƴ���Ӧ��ӳ���ϵ������֧�� add �� addAll ������
     */
    public Set<Map.Entry<K,V>> entrySet() {
    return entrySet0();
    }

    private Set<Map.Entry<K,V>> entrySet0() {
        Set<Map.Entry<K,V>> es = entrySet;
        return es != null ? es : (entrySet = new EntrySet());
    }

    // �ڲ���EntrySet
    private final class EntrySet extends AbstractSet<Map.Entry<K,V>> {
        public Iterator<Map.Entry<K,V>> iterator() {
            return newEntryIterator();
        }
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<K,V> e = (Map.Entry<K,V>) o;
            Entry<K,V> candidate = getEntry(e.getKey());
            return candidate != null && candidate.equals(e);
        }
        public boolean remove(Object o) {
            return removeMapping(o) != null;
        }
        public int size() {
            return size;
        }
        public void clear() {
            HashMap.this.clear();
        }
    }

    // ���л�����
    private void writeObject(java.io.ObjectOutputStream s)
        throws IOException
    {
    Iterator<Map.Entry<K,V>> i =
        (size > 0) ? entrySet0().iterator() : null;

    s.defaultWriteObject();

    s.writeInt(table.length);

    s.writeInt(size);

    if (i != null) {
        while (i.hasNext()) {
        Map.Entry<K,V> e = i.next();
        s.writeObject(e.getKey());
        s.writeObject(e.getValue());
        }
        }
    }

    private static final long serialVersionUID = 362498820763181265L;

    // ͨ�����ж�ȡ����
    private void readObject(java.io.ObjectInputStream s)
         throws IOException, ClassNotFoundException
    {
    s.defaultReadObject();

    int numBuckets = s.readInt();
    table = new Entry[numBuckets];

        init();

    int size = s.readInt();

    for (int i=0; i<size; i++) {
        K key = (K) s.readObject();
        V value = (V) s.readObject();
        putForCreate(key, value);
    }
    }

    int   capacity()     { return table.length; }
    float loadFactor()   { return loadFactor;   }
}