import java.util.BitSet;


public class BloomFilter {
	/* BitSet��ʼ����2^24��bit */ 
	private static final int DEFAULT_SIZE =1<<25; 
	/* ��ͬ��ϣ���������ӣ�һ��Ӧȡ���� */
	private static final int[] seeds =new int[] { 5, 7, 11, 13, 31, 37, 61 };
	private BitSet bits =new BitSet(DEFAULT_SIZE);
	/* ��ϣ�������� */ 
	private SimpleHash[] func =new SimpleHash[seeds.length];

	public BloomFilter() 
	{
	for (int i =0; i < seeds.length; i++)
	{
		//new ���hash
	func[i] =new SimpleHash(DEFAULT_SIZE, seeds[i]);
	}
	}

	// ���ַ�����ǵ�bits��
	public void add(String value) 
	{
		//�������е�hash function
	for (SimpleHash f : func) 
	{
	System.out.println(f.hash(value));
	bits.set(f.hash(value), true);
	}
	}

	//�ж��ַ����Ƿ��Ѿ���bits���
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

	/* ��ϣ������ */
	public static class SimpleHash 
	{
	private int cap;
	private int seed;

	public SimpleHash(int cap, int seed) 
	{
	this.cap = cap;
	this.seed = seed;
	}

	//hash���������ü򵥵ļ�Ȩ��hash,hash��BitSet��ָ����λ��
	public int hash(String value) 
	{
	int result =0;
	int len = value.length();
	for (int i =0; i < len; i++) 
	{
	result = seed * result + value.charAt(i);
	}
	//��cap=2��4�η���cap-1��Ϊ01111����result����.����һ����(cap-1)&result<=cap-1
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
