import java.util.BitSet;


public class BloomFilter {
	/* BitSet初始分配2^24个bit */ 
	private static final int DEFAULT_SIZE =1<<25; 
	/* 不同哈希函数的种子，一般应取质数 */
	private static final int[] seeds =new int[] { 5, 7, 11, 13, 31, 37, 61 };
	private BitSet bits =new BitSet(DEFAULT_SIZE);
	/* 哈希函数对象 */ 
	private SimpleHash[] func =new SimpleHash[seeds.length];

	public BloomFilter() 
	{
	for (int i =0; i < seeds.length; i++)
	{
		//new 多个hash
	func[i] =new SimpleHash(DEFAULT_SIZE, seeds[i]);
	}
	}

	// 将字符串标记到bits中
	public void add(String value) 
	{
		//遍历所有的hash function
	for (SimpleHash f : func) 
	{
	System.out.println(f.hash(value));
	bits.set(f.hash(value), true);
	}
	}

	//判断字符串是否已经被bits标记
	public boolean contains(String value) 
	{
	if (value ==null) 
	{
	return false;
	}
	boolean ret =true;
	for (SimpleHash f : func) 
	{
    
	ret = ret && bits.get(f.hash(value));
	}
	return ret;
	}

	/* 哈希函数类 */
	public static class SimpleHash 
	{
	private int cap;
	private int seed;

	public SimpleHash(int cap, int seed) 
	{
	this.cap = cap;
	this.seed = seed;
	}

	//hash函数，采用简单的加权和hash,hash到BitSet中指定的位置
	public int hash(String value) 
	{
	int result =0;
	int len = value.length();
	for (int i =0; i < len; i++) 
	{
	result = seed * result + value.charAt(i);
	}
	//若cap=2的4次方，cap-1即为01111；与result相与.即归一化，(cap-1)&result<=cap-1
	return (cap -1) & result;
	}
	}
	public static void main(String[] args) {
		BloomFilter bf=new BloomFilter();
		String a="AD";
		//String b="AAAADDDE";
		String c="BC";
		bf.add(a);
		System.out.println("..............");
		bf.add(c);
		//bf.add(b);
		int result=0,seed=5;
		for (int i =0; i < a.length(); i++) 
		{
		result = seed *result + a.charAt(i);
		}
		System.out.println("a: result "+result);
		result=0;
		for (int i =0; i < c.length(); i++) 
		{
		
		result = seed *result + c.charAt(i);
		}
		System.out.println("c: result "+result);
		System.out.println(bf.contains(c));
	}

}
