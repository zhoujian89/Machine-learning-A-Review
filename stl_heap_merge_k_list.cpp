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
	 //时间复杂度 O(nlogk)  
     struct Node  
    {  
        ListNode* pNode;  
        int index;  
        Node(ListNode* _pNode = NULL, int _index = 0):pNode(_pNode), index(_index){};  
    } ;
  //函数对象要重载操作符()；使得comp的对象具有函数的功能，比较两个数的大小
  //该函数有两个参数(a,b)比较其大小
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
		//传参数时，传入匿名对象comp()，函数对象
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