
import java.util.ArrayList;

/**
 *
 * @author Aditya
 */
/*
1. Creating new NodeStructure Objects -> results in loss of prev/left and next/right
2. Terms Prev and Next are use in place of Left and Right to avoid confusion with class Node
3. Node -> NodeStructure export/import -> results in loss of L/R
   This loss of L/R poses a problem while creating the Huffman tree
   Solved: This problem is solved by making use of a Node pointer in each NodeStructure
   This pointer references -> the huffman tree created till now
*/
public class PairingHeap {
    private NodeStructure root =null;
    private int heapSize;
     
    
    public PairingHeap() {
        heapSize = 0;
    }
    
    public void insert(Node n){
    //Since this class uses the NodeStructure, we need to typecast this
        NodeStructure newNode;
        if(n.getHuffmanTree() == null) 
            newNode = new NodeStructure(n.getLabel(), n.getValue());
        else
            newNode = new NodeStructure(n.getLabel(), n.getValue(),n.getHuffmanTree());
        //for first insertion, set the element as root
        if(heapSize==0) root = newNode;
        else{
            meld(root,newNode);
        }
        heapSize++;
    }
    
    /*
    1. removeMin() removes the min. But the heapSize is not updated till the end of the function
    2. A zero heapSize: returns -9999 or infiniti. This case, though never arises. If a -9999 is received 
       in the calling code, the heap is empty.
    3. heapSize == 1 : before removeMin, the size of heap was 1. This removal will empty the heap
    4. heapSize == 2 : needs to be handled, because we process the children in the meld using 2 pass. 
                       The code steps below this line will, therefore, work only if you have at-least 2 children.
                       When you remove from a heapSize ==2 -> 1 element remains, which can't be melded. So just set 
                       the one remaining element as the root
    */

    public Node removeMin(){
       if(heapSize==0) return new Node(-9999,-9999);

       //return this to the calling function
       Node toReturn = new Node(root.getLabel(),root.getValue(),root.getHuffmanTreeNS()); 
       if(heapSize==1){
        heapSize = heapSize-1;
        return toReturn;
       }
       
       //Since we have more than 1 element, getting child won't break the code
       NodeStructure temp = root.getChild();
       if(heapSize == 2){
        root = null;
        root = temp;
       }/*
       else if(heapSize == 3){
           NodeStructure a = new NodeStructure(root.getChild());
           NodeStructure b = new NodeStructure(root.get)
       }*/
       else{
       
       int size = 1;
       //get the count of total number of children of root
       //why? To process only 2 elements -> 2-Pass merge
       while(true){
           if(temp.getNext()==null) break;
           else{
           size++;
           temp = temp.getNext();
           }
       }
        
       //bring temp back to first element
       temp = root.getChild();
       root = null;
       
       ArrayList<NodeStructure> passList = new ArrayList<>();
       NodeStructure tempNext;
       
       int iter = 0;
       while(iter<=size-2){
          /*Boxing the a and b pointers with a new object was not needed. Done as a part of debugging
           Don't break what works 
           Caution: If you want to remove the new node creation, make sure to set the prev and next to null in
           the meld function
           */
           tempNext = temp.getNext();
           NodeStructure a = new NodeStructure(temp); 
           temp = tempNext.getNext();
           NodeStructure b = new NodeStructure(tempNext);
           /* Debug commented:
             System.out.println("Debug point 3>checking val of a>"+a.getValue());
             System.out.println("Debug point 4>checking val of b>"+b.getValue()); */
           
           /*if(temp.getNext()!=null) removed since we use iter*/
           //temp = temp.getNext().getNext(); //preserving next which will be lost after meld
           NodeStructure result = meld(a,b);
           /* Debug commented:
           System.out.println("Debug point 2>Successful meld of>"+result.getLabel()+result.getValue()
            +" Added child>"+result.getChild().getValue());
            */
           passList.add(result);
           if(temp == null) break;
           iter = iter + 2;
       }
       
       /*
        Meld the unprocessed odd element, if any with the last element from above pass
       */
       if(temp!=null){
           iter = passList.size()-1;
           NodeStructure x = passList.get(iter);//passList.get((iter-2)/2); //divide by 2: since 2 elements become 1 in passList
           /*Debug commented: System.out.println("Debug point 5>"+a.getValue());*/
           NodeStructure result = meld(x,new NodeStructure(temp)); 
           /*Debug commented:System.out.println("Debug point 6>"+result.getValue()+" child added is>"+result.getChild().getValue());*/
           passList.set(iter, result);//passList.set((iter-2)/2, result);
       }
       
       /*
       Meld from right to left
       */
       iter = passList.size()-1;
       NodeStructure result = new NodeStructure(passList.remove(iter));
       iter = iter - 1;
       while(iter>=0){
           if(heapSize == 4) System.out.println("Iter values "+iter);
           result = meld(result,passList.remove(iter));
           iter = iter - 1;           
       } 
       root = result;
       }//end of if-else
       heapSize = heapSize - 1;
       return toReturn;
    } 
    
    public NodeStructure meld(NodeStructure a, NodeStructure b){
        
        if(a.getValue()<=b.getValue()){
            if(a.getChild() == null){
                a.setChild(b); 
                b.setPrev(a);
                if(b==root) root = a;
            }
            if(a.getChild()!=null){
              NodeStructure temp = a.getChild();
              
              temp.setPrev(b); 
              b.setNext(temp);
              
              a.setChild(b);
              b.setPrev(a);
              
              /*
              This boolean check is to detect the self loop
              Why? Weirdly, the last element (when heapSize==2) updates its next/right pointer 
              to itself, causing an infinite loop for a further removeMin.
              Check and remove this loop
              */         
              boolean x = temp==temp.getNext();
              if(x) temp.setNext(null);
              //temp.setChild(null);
              /* Debug Commented:
                if(heapSize == 4) 
                if(temp.getValue()==12) 
                System.out.println("Checking self loop>"+x);
                */
              if(b==root) root = a;               
            }
            return a;
        }
        else if(b.getValue()<a.getValue()){
            if(b.getChild() == null){
                b.setChild(a);
                a.setPrev(b);          
               if(a==root) root = b;   
            }
            else{
                NodeStructure temp = b.getChild();
                temp.setPrev(a); 
                a.setNext(temp);
              
                b.setChild(a);
                a.setPrev(b);
                
                boolean x = temp==temp.getNext();
                if(x) temp.setNext(null);
                
                if(a==root) root = b;
            }
            return b;
        }   
        return null;
    }
    
    public int getHeapSize(){
        return heapSize;
    }
}

/*
 *Each element in the Pairing heap is a NodeStructure -> anologos to an array element in Binary heap
 *The scope of this NodeStructure does not extend beyond this Class
*/
class NodeStructure{
    private int value;
    private int label;
    private NodeStructure prev;
    private NodeStructure next;
    private NodeStructure child;
    private Node huffmanTreeNS;

    public Node getHuffmanTreeNS() {
        return huffmanTreeNS;
    }

    public void setHuffmanTreeNS(Node huffmanTree) {
        this.huffmanTreeNS = huffmanTree;
    }
    
    public NodeStructure(int label, int value) {
        this.label = label;
        this.value = value;
        this.prev = null;
        this.next = null;
        this.child = null;
        this.huffmanTreeNS = null;
    }
    
    public NodeStructure(int label, int value, Node huffmanTree) {
        this.label = label;
        this.value = value;
        this.prev = null;
        this.next = null;
        this.child = null;
        this.huffmanTreeNS = huffmanTree;
    }

    public NodeStructure(NodeStructure node) {
        this.value = node.getValue();
        this.label = node.getLabel();
        this.prev = null; this.next = null;
        this.child = node.getChild();
        this.huffmanTreeNS = node.getHuffmanTreeNS();
    }

    
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public NodeStructure getPrev() {
        return prev;
    }

    public void setPrev(NodeStructure prev) {
        this.prev = prev;
    }

    public NodeStructure getNext() {
        return next;
    }

    public void setNext(NodeStructure next) {
        this.next = next;
    }

    public NodeStructure getChild() {
        return child;
    }

    public void setChild(NodeStructure child) {
        this.child = child;
    }

}
