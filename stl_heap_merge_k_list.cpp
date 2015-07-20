/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */

 
class Solution {
public:
	 //ʱ�临�Ӷ� O(nlogk)  
     struct Node  
    {  
        ListNode* pNode;  
        int index;  
        Node(ListNode* _pNode = NULL, int _index = 0):pNode(_pNode), index(_index){};  
    } ;
  //��������Ҫ���ز�����()��ʹ��comp�Ķ�����к����Ĺ��ܣ��Ƚ��������Ĵ�С
  //�ú�������������(a,b)�Ƚ����С
   class comp {  
   public:  
    bool operator() (Node  a,Node  b) const {  
        return  a.pNode->val > b.pNode->val;  
    }  
};
 
    
    ListNode *mergeKLists(vector<ListNode *> &lists) {
        vector<ListNode *> m;
        ListNode dummy(-1);  
        ListNode* prev = &dummy; 
        vector<Node> heap;

       for(int i=0;i<lists.size();i++){
           if(lists[i]!=NULL) m.push_back(lists[i]);
       }
       if(lists.size()<1||m.size()<1) return NULL;
        
        for(int i=0;i<lists.size();i++){
           if(lists[i]!=NULL) {
            heap.push_back(Node(lists[i],i));
            lists[i]=lists[i]->next;
           }
        }
		//������ʱ��������������comp()����������
        make_heap(heap.begin(),heap.end(),comp());
        while(1){
            int r=heap[0].index;
            prev->next=heap[0].pNode;
            prev=prev->next;
            	if(lists[r]==NULL){
            	    pop_heap(heap.begin(),heap.end(),comp());
			        heap.pop_back();
            	    if(heap.size()==0) break;
            	}
            	else{
                    heap.push_back(Node(lists[r],r)); 
			        lists[r]=lists[r]->next;
                    pop_heap(heap.begin(),heap.end(),comp());
			        heap.pop_back();
            	}
        }
        prev->next = NULL;  
        return dummy.next;  
    }
};