#include<iostream>
#include<vector>
#include<algorithm>
using namespace std;
/*
make_heap();、pop_heap();、push_heap();、sort_heap();

他们的头函数是algorithm

首先是make_heap();

他的函数原型是：

void make_heap(first_pointer,end_pointer,compare_function);

一个参数是数组或向量的头指针，第二个向量是尾指针。第三个参数是比较函数的名字
。在缺省的时候，默认是大跟堆。（下面的参数都一样就不解释了）

作用：把这一段的数组或向量做成一个堆的结构。范围是(first,last)

然后是pop_heap();

它的函数原型是：

void pop_heap(first_pointer,end_pointer,compare_function);

作用：pop_heap()不是真的把最大（最小）的元素从堆中弹出来。而是重新排序堆。它
把first和last交换，然后将[first,last-1)的数据再做成一个堆。

接着是push_heap()

void pushheap(first_pointer,end_pointer,compare_function);

作用：push_heap()假设由[first,last-1)是一个有效的堆，然后，再把堆中的新元素加
进来，做成一个堆。

最后是sort_heap()

void sort_heap(first_pointer,end_pointer,compare_function);

作用是sort_heap对[first,last)中的序列进行排序。它假设这个序列是有效堆。（当然
，经过排序之后就不是一个有效堆了）
#include<algorithm>
using namespace std;
bool cmp(int a,int b) //比较函数
{
    return a>b;
}
int main()
{
    int i,number[20]={29,23,20,22,17,15,26,51,19,12,35,40};
    make_heap(&number[0],&number[12]);
    //结果是:51 35 40 23 29 20 26 22 19 12 17 15
    for(i=0;i<12;i++)
        printf("%d ",number[i]);
    printf("\n");
    make_heap(&number[0],&number[12],cmp);
    //结果：12 17 15 19 23 20 26 51 22 29 35 40
    for(i=0;i<12;i++)
        printf("%d ",number[i]);
    printf("\n");
    //加入元素8
    number[12]=8;
    //加入后调整
    push_heap(&number[0],&number[13],cmp);
    //结果：8 17 12 19 23 15 26 51 22 35 40 20
    for(i=0;i<13;i++)
        printf("%d ",number[i]);
    printf("\n");
    //弹出元素8
   pop_heap(&number[0],&number[13],cmp);
    //结果：12 17 15 19 23 20 26 51 22 29 35 40
    for(i=0;i<13;i++)
        printf("%d ",number[i]);
    printf("\n");
    sort_heap(&number[0],&number[12],cmp);
    //结果不用说都知道是有序的了！
    for(i=0;i<12;i++)
       printf("%d ",number[i]);
    return 0;
}
*/
//比较函数
bool cmp(int a,int b) 
{
    return a<b;
}
int main(){
	vector<int> v;
	v.push_back(1);
	v.push_back(4);
	v.push_back(2);
	v.push_back(5);
	make_heap(v.begin(),v.end(),cmp);
	cout<<"........增加元素前........"<<endl; 
	for(int i=0;i<v.size();i++)
	cout<<v[i]<<"  "<<endl; 
	v.push_back(10);
	push_heap(v.begin(),v.end(),cmp);
	cout<<"........增加元素后........"<<endl;
	for(int i=0;i<v.size();i++)
	cout<<v[i]<<"  "<<endl; 
	cout<<"........"<<endl; 
	v.push_back(9);
	/*在pop_heap之后,最好把vector最后一个元素删除*/
	pop_heap(v.begin(),v.end(),cmp);
//	v.pop_back();
	for(int i=0;i<v.size();i++)
	cout<<v[i]<<"  "<<endl; 
	 
	return 1;
}