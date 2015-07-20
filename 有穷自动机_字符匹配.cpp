#include<iostream>
#include<string>
using namespace std;
class FiniteAutomata{
	public:
	 static const int NO_OF_CHARS=256;
	 static int getNextState(string pat,int M,int state,int x){
	 	//匹配时，pat[0...state-1]x后缀与 pat[0...state]的前缀的最大匹配长度 
	 	if(state<M&&x==pat[state]){//注意当state=M时，匹配pat[0..M]x与pat[0..M] 
	 		return state+1;
	 	}
	 	//当state=M时，不需要判断x与pat[state],因为两者长度不一样，最后匹配时应拿x与pat[state-1]之前匹配 
	 	//x与pat[state]肯定不相等，因为pat[M]根本不存在 
	 	int ns,i;
	 	for(ns=state;ns>0;ns--){//反向在 pat[0...state]找 ，从pat[0..state-1] 
	 		if(pat[ns-1]==x){//反向找到相同的 ;注意 ns-1实质有ns个 
	 			for(int i=0;i<ns-1;i++){//再从头找及判断pat[0,..ns-2] 
	 				if(pat[i]!=pat[state-ns+i+1]) break;//state-y=ns-1-i
	 			}
	 			if(i==ns-1) return ns;
	 		}
	 	}
	 	return 0;
	 	
	 	
	 	
		
	}
	 static void computeTF(string pat,int M,int * TF[NO_OF_CHARS]){
		int state,x;
		for(state=0;state<=M;state++){
		for(x=0;x<NO_OF_CHARS;x++){
		TF[state][x]=getNextState(pat,M,state,x);	
		}
		}
	}
	static void search(string pat,string txt){
		int M=pat.size();
		int N=txt.size();
		int **TF;
		TF=new int *[M+1];
		for(int i=0;i<=M;i++){
			TF[i]=new int[NO_OF_CHARS];
		}
		computeTF(pat,M,TF);
		int i,state=0;
		for(i=0;i<N;i++){
			state=TF[state][txt[i]];
			if(state==M){
				cout<<"pattern found at index:"<<(i-M+1)<<endl;
			}
		}
	}
}; 
int main(){
	string T="missississi";
	string P="ssi";
	FiniteAutomata f;
	f.search(P,T);
	return 0;
}