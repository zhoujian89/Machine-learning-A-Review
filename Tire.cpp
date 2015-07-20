/********************************************
给出很多单词,统计出以某个字符串为前缀的单词数量(单词本身也是自己的前缀);

算法分析：
字典树
数据结构： 
Trie树,又称单词查找树或字典树,是一种树形结构,是一种哈希树的变种; 
 
基本原理： 
Trie树的核心思想是空间换时间,利用字符串的公共前缀来降低查询时间的开销以达到提高效率的目的; 
 
应用： 
用于统计和排序大量的字符串(但不仅限于字符串),所以经常被搜索引擎系统用于文本词频统计; 
 
优点： 
最大限度地减少无谓的字符串比较,查询效率比哈希表高; 
 
基本特性： 
(1)根节点不包含字符,除根节点外每一个节点都只包含一个字符; 
(2)从根节点到某一节点,路径上经过的字符连接起来,为该节点对应的字符串; 
(3)每个节点的所有子节点包含的字符都不相同; 
*********************************************/
#include<iostream>
#include<cstring>
#include<cstdlib>
#include<cstdio>
#include<climits>
#include<algorithm>
using namespace std;

const int MAX=26;//26个字母 
const int N=12;

struct Trie //Trie结点声明
{
    bool isStr;//标记该结点处是否构成一个串
    int prefix;//统计前缀
    Trie *next[MAX];//一个指针数组，存放着指向各个儿子节点的指针，总共有0-25个child 
};

void insert(Trie *root,const char *s) //将单词s插入到字典树中
{
    if(root==NULL||*s=='\0')
        return;
    Trie *p=root;
    while(*s)
    {
        if(p->next[*s-'a']==NULL)//如果不存在存储该字符的节点，则建立结点
        {
            Trie *temp=new Trie;
            for(int i=0; i<MAX; i++)
            {
                temp->next[i]=NULL;
            }
            temp->isStr=false;
            temp->prefix=0;
            p->next[*s-'a']=temp;
            p=p->next[*s-'a'];
        }
        else
        {
            p=p->next[*s-'a'];
        }
        p->prefix++;//前缀个数加1 
        s++;//让指针s指向下一个字符
    }
    p->isStr=true;//单词结束的地方标记此处可以构成一个串
}

int search(Trie *root,const char *s)//查找某个单词s是否已经存在
{
    Trie *p=root;
    while(p&&*s)
    {
        p=p->next[*s-'a'];
        s++;
    }
    return (p&&p->isStr);  //在单词结束处的标记为true时，单词才存在
}

void del(Trie *root)//释放整个字典树占的堆区空间
{
    for(int i=0; i<MAX; i++)
    {
        if(root->next[i]!=NULL)
        {
            del(root->next[i]);
        }
    }
    delete root;
}

int solve(Trie *root,const char *s)//统计前缀
{
    Trie *p=root;
    while(p&&*s)
    {//表示第*s-'a'个child 
        if(p->next[*s-'a']==NULL)
        {
            return 0;
        }
        p=p->next[*s-'a'];
        s++;
    }
    return p->prefix;
}

int main()
{
/*
	文件中输入：
bcd
acd
abedf
bbb
bbc
cccc
#
aa
bb
c
#
path: 文件名。 
mode: 文件打开的模式。和fopen中的模式（如r, w,）相同。 
stream: 一个文件，通常使用标准流文件（stdin, stdout, stderr）。 
返回值：成功，则返回一个path所指定的文件的指针。失败，返回NULL。（一般都不使用它的返回值） 

功能：简单说，就是实现重定向。把预定义的几个标准流文件(stdin, stdout, stderr)定向到由path指定的文件中。 
freopen("debug\\in.txt","r",stdin)的作用就是把stdin重定向到debug\\in.txt文件中，这样在用cin或是
用scanf输入时便不会从标准输入流提取数据。而是从in.txt文件中获取输入。只要把输入事先粘贴到 
in.txt，调试时就方便多。 
	*/ 
    freopen("D:\\kd.txt","r",stdin);
    char s[N];//最多只能输入N-1个字符，最后一个字符为\0 
    Trie *root = new Trie;
    for(int i=0; i<MAX; i++)
    {
        root->next[i]=NULL;
    }
    root->isStr=false;
    root->prefix=0;
    //先从stdio流获取个字符串，之后判断其是不是“#”字符串，如果不是的话就继续执行循环体
	//gets() 从stdin流中读取字符串，直至接受到换行符或EOF时停止，并将读取的结果存放在buffer指针所指向的字符数组.
	//换行符不作为读取串的内容，读取的换行符被转换为‘\0’空字符，并由此来结束字符串。 
	//gets()函数用来从标准输入设备（键盘）读取字符串直到换行符结束，但换行符会被丢弃，然后在末尾添加'\0'字符
    while(gets(s),strcmp(s,"#"))
    {
        insert(root,s);     //先建立字典树
    }
    while(gets(s),strcmp(s,"#"))
    {
        printf("%s  %d\n", s,solve(root,s));
    }
    del(root); //释放空间很重要
    return 0;
}
