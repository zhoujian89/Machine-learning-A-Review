#include<iostream>
#include<string>
using namespace std;
class FiniteAutomata{
	public:
	 static const int NO_OF_CHARS=256;
	 static int getNextState(string pat,int M,int state,int x){
	 	//ƥ��ʱ��pat[0...state-1]x��׺�� pat[0...state]��ǰ׺�����ƥ�䳤�� 
	 	if(state<M&&x==pat[state]){//ע�⵱state=Mʱ��ƥ��pat[0..M]x��pat[0..M] 
	 		return state+1;
	 	}
	 	//��state=Mʱ������Ҫ�ж�x��pat[state],��Ϊ���߳��Ȳ�һ�������ƥ��ʱӦ��x��pat[state-1]֮ǰƥ�� 
	 	//x��pat[state]�϶�����ȣ���Ϊpat[M]���������� 
	 	int ns,i;
	 	for(ns=state;ns>0;ns--){//������ pat[0...state]�� ����pat[0..state-1] 
	 		if(pat[ns-1]==x){//�����ҵ���ͬ�� ;ע�� ns-1ʵ����ns�� 
	 			for(int i=0;i<ns-1;i++){//�ٴ�ͷ�Ҽ��ж�pat[0,..ns-2] 
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