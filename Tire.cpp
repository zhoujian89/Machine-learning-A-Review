/********************************************
�����ܶ൥��,ͳ�Ƴ���ĳ���ַ���Ϊǰ׺�ĵ�������(���ʱ���Ҳ���Լ���ǰ׺);

�㷨������
�ֵ���
���ݽṹ�� 
Trie��,�ֳƵ��ʲ��������ֵ���,��һ�����νṹ,��һ�ֹ�ϣ���ı���; 
 
����ԭ�� 
Trie���ĺ���˼���ǿռ任ʱ��,�����ַ����Ĺ���ǰ׺�����Ͳ�ѯʱ��Ŀ����Դﵽ���Ч�ʵ�Ŀ��; 
 
Ӧ�ã� 
����ͳ�ƺ�����������ַ���(�����������ַ���),���Ծ�������������ϵͳ�����ı���Ƶͳ��; 
 
�ŵ㣺 
����޶ȵؼ�����ν���ַ����Ƚ�,��ѯЧ�ʱȹ�ϣ���; 
 
�������ԣ� 
(1)���ڵ㲻�����ַ�,�����ڵ���ÿһ���ڵ㶼ֻ����һ���ַ�; 
(2)�Ӹ��ڵ㵽ĳһ�ڵ�,·���Ͼ������ַ���������,Ϊ�ýڵ��Ӧ���ַ���; 
(3)ÿ���ڵ�������ӽڵ�������ַ�������ͬ; 
*********************************************/
#include<iostream>
#include<cstring>
#include<cstdlib>
#include<cstdio>
#include<climits>
#include<algorithm>
using namespace std;

const int MAX=26;//26����ĸ 
const int N=12;

struct Trie //Trie�������
{
    bool isStr;//��Ǹý�㴦�Ƿ񹹳�һ����
    int prefix;//ͳ��ǰ׺
    Trie *next[MAX];//һ��ָ�����飬�����ָ��������ӽڵ��ָ�룬�ܹ���0-25��child 
};

void insert(Trie *root,const char *s) //������s���뵽�ֵ�����
{
    if(root==NULL||*s=='\0')
        return;
    Trie *p=root;
    while(*s)
    {
        if(p->next[*s-'a']==NULL)//��������ڴ洢���ַ��Ľڵ㣬�������
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
        p->prefix++;//ǰ׺������1 
        s++;//��ָ��sָ����һ���ַ�
    }
    p->isStr=true;//���ʽ����ĵط���Ǵ˴����Թ���һ����
}

int search(Trie *root,const char *s)//����ĳ������s�Ƿ��Ѿ�����
{
    Trie *p=root;
    while(p&&*s)
    {
        p=p->next[*s-'a'];
        s++;
    }
    return (p&&p->isStr);  //�ڵ��ʽ������ı��Ϊtrueʱ�����ʲŴ���
}

void del(Trie *root)//�ͷ������ֵ���ռ�Ķ����ռ�
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

int solve(Trie *root,const char *s)//ͳ��ǰ׺
{
    Trie *p=root;
    while(p&&*s)
    {//��ʾ��*s-'a'��child 
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
	�ļ������룺
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
path: �ļ����� 
mode: �ļ��򿪵�ģʽ����fopen�е�ģʽ����r, w,����ͬ�� 
stream: һ���ļ���ͨ��ʹ�ñ�׼���ļ���stdin, stdout, stderr���� 
����ֵ���ɹ����򷵻�һ��path��ָ�����ļ���ָ�롣ʧ�ܣ�����NULL����һ�㶼��ʹ�����ķ���ֵ�� 

���ܣ���˵������ʵ���ض��򡣰�Ԥ����ļ�����׼���ļ�(stdin, stdout, stderr)������pathָ�����ļ��С� 
freopen("debug\\in.txt","r",stdin)�����þ��ǰ�stdin�ض���debug\\in.txt�ļ��У���������cin����
��scanf����ʱ�㲻��ӱ�׼��������ȡ���ݡ����Ǵ�in.txt�ļ��л�ȡ���롣ֻҪ����������ճ���� 
in.txt������ʱ�ͷ���ࡣ 
	*/ 
    freopen("D:\\kd.txt","r",stdin);
    char s[N];//���ֻ������N-1���ַ������һ���ַ�Ϊ\0 
    Trie *root = new Trie;
    for(int i=0; i<MAX; i++)
    {
        root->next[i]=NULL;
    }
    root->isStr=false;
    root->prefix=0;
    //�ȴ�stdio����ȡ���ַ�����֮���ж����ǲ��ǡ�#���ַ�����������ǵĻ��ͼ���ִ��ѭ����
	//gets() ��stdin���ж�ȡ�ַ�����ֱ�����ܵ����з���EOFʱֹͣ��������ȡ�Ľ�������bufferָ����ָ����ַ�����.
	//���з�����Ϊ��ȡ�������ݣ���ȡ�Ļ��з���ת��Ϊ��\0�����ַ������ɴ��������ַ����� 
	//gets()���������ӱ�׼�����豸�����̣���ȡ�ַ���ֱ�����з������������з��ᱻ������Ȼ����ĩβ���'\0'�ַ�
    while(gets(s),strcmp(s,"#"))
    {
        insert(root,s);     //�Ƚ����ֵ���
    }
    while(gets(s),strcmp(s,"#"))
    {
        printf("%s  %d\n", s,solve(root,s));
    }
    del(root); //�ͷſռ����Ҫ
    return 0;
}
