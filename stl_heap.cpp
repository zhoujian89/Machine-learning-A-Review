#include<iostream>
#include<vector>
#include<algorithm>
using namespace std;
/*
make_heap();��pop_heap();��push_heap();��sort_heap();

���ǵ�ͷ������algorithm

������make_heap();

���ĺ���ԭ���ǣ�

void make_heap(first_pointer,end_pointer,compare_function);

һ�������������������ͷָ�룬�ڶ���������βָ�롣�����������ǱȽϺ���������
����ȱʡ��ʱ��Ĭ���Ǵ���ѡ�������Ĳ�����һ���Ͳ������ˣ�

���ã�����һ�ε��������������һ���ѵĽṹ����Χ��(first,last)

Ȼ����pop_heap();

���ĺ���ԭ���ǣ�

void pop_heap(first_pointer,end_pointer,compare_function);

���ã�pop_heap()������İ������С����Ԫ�شӶ��е�������������������ѡ���
��first��last������Ȼ��[first,last-1)������������һ���ѡ�

������push_heap()

void pushheap(first_pointer,end_pointer,compare_function);

���ã�push_heap()������[first,last-1)��һ����Ч�Ķѣ�Ȼ���ٰѶ��е���Ԫ�ؼ�
����������һ���ѡ�

�����sort_heap()

void sort_heap(first_pointer,end_pointer,compare_function);

������sort_heap��[first,last)�е����н������������������������Ч�ѡ�����Ȼ
����������֮��Ͳ���һ����Ч���ˣ�
#include<algorithm>
using namespace std;
bool cmp(int a,int b) //�ȽϺ���
{
    return a>b;
}
int main()
{
    int i,number[20]={29,23,20,22,17,15,26,51,19,12,35,40};
    make_heap(&number[0],&number[12]);
    //�����:51 35 40 23 29 20 26 22 19 12 17 15
    for(i=0;i<12;i++)
        printf("%d ",number[i]);
    printf("\n");
    make_heap(&number[0],&number[12],cmp);
    //�����12 17 15 19 23 20 26 51 22 29 35 40
    for(i=0;i<12;i++)
        printf("%d ",number[i]);
    printf("\n");
    //����Ԫ��8
    number[12]=8;
    //��������
    push_heap(&number[0],&number[13],cmp);
    //�����8 17 12 19 23 15 26 51 22 35 40 20
    for(i=0;i<13;i++)
        printf("%d ",number[i]);
    printf("\n");
    //����Ԫ��8
   pop_heap(&number[0],&number[13],cmp);
    //�����12 17 15 19 23 20 26 51 22 29 35 40
    for(i=0;i<13;i++)
        printf("%d ",number[i]);
    printf("\n");
    sort_heap(&number[0],&number[12],cmp);
    //�������˵��֪����������ˣ�
    for(i=0;i<12;i++)
       printf("%d ",number[i]);
    return 0;
}
*/
//�ȽϺ���
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
	cout<<"........����Ԫ��ǰ........"<<endl; 
	for(int i=0;i<v.size();i++)
	cout<<v[i]<<"  "<<endl; 
	v.push_back(10);
	push_heap(v.begin(),v.end(),cmp);
	cout<<"........����Ԫ�غ�........"<<endl;
	for(int i=0;i<v.size();i++)
	cout<<v[i]<<"  "<<endl; 
	cout<<"........"<<endl; 
	v.push_back(9);
	/*��pop_heap֮��,��ð�vector���һ��Ԫ��ɾ��*/
	pop_heap(v.begin(),v.end(),cmp);
//	v.pop_back();
	for(int i=0;i<v.size();i++)
	cout<<v[i]<<"  "<<endl; 
	 
	return 1;
}