
/**
 *
 * @author Aditya
 */
public class BinaryHeap{
    private int heapSize = -1; //maintains the current size of the heap
    private int capacity = 0;
    
    final private Node[] heap;

    public BinaryHeap(int n) {
        heap = new Node[n];
        capacity = n;
    }

    public Node removeMin() {
        //removes the min and returns it
        Node min = heap[0];
        heap[0] = heap[heapSize];
        heap[heapSize] = null; //to prevent having dangling trees
        heapSize--;
        buildMinHeap(0);
        return min;
    }
    
    //Run when you removeMin. removeMin results in the last element to be added to the top
    public void buildMinHeap(int index){
        int left = getLeft(index);
        int right = getRight(index);
        
        int minIndex = index;
        
        if(left<=heapSize && heap[left].compareTo(heap[index])<0){ /*heap[left]<heap[index]*/
            minIndex = left;
        }
        if(right<=heapSize && heap[right].compareTo(heap[minIndex])<0){
            minIndex = right;
        }
        //If the given index, is not the minIndex, we need to change something
        //Keep swapping with elements below, so that the element reaches its correct place in the heap
        
        if(minIndex!=index){
           Node temp = heap[index];
           heap[index] = heap[minIndex];
           heap[minIndex] = temp;
           
           //call back code
           buildMinHeap(minIndex);
        
        }
    
    }
    
    //class for priority queue
    public void insert(Node n) {
        if(heapSize == capacity) System.out.println("Heap Reached Capacity, Cannot Add");
        else{
        heapSize = heapSize + 1;
        heap[heapSize] = n;
        int index = heapSize;
        
        //insertion may result in breaking of the heap property
        //check parents and fix any errors
        //a new element is always added to the end of a Binary Heap
        while(index!=0 && heap[getParent(index)].compareTo(heap[index])>=1){
            int i = getParent(index);
          
            Node temp = heap[index];
            heap[index] = heap[i];
            heap[i] = temp;
            
            index = i;
        
        } //while-ends
        
        
        }
           
    }
    /*
    Following methods return the indices of the queried items
    Change_1: index returning -1 for child being not present, replaced with capacity logic
    */  
    public int getLeft(int index){
        //returns index of left child
        //if(index == heap.length-1 || index == heap.length-2) return -1; //-1 indicates leaf nodes with no children
        return (index*2)+1;
    
    }
    
    public int getRight(int index){
        //returns index of right child
        //if(index == heapSize-1 || index == heapSize-2) return -1;
        return (index*2)+2;
    
    }
    
    public int getParent(int index){
       //given the index of the element, return the Parent
        if(index == 0) return index;
        else return (index-1)/2;
    }
    
    //to-do
    public boolean isEmpty(){
        return heapSize >= 0;
    }
    
    public int getSize(){
        return heapSize+1; //why? heapSize in this code acts as Index
    }
          
    public int min() {
        //just tells you what the current min element is
        return heap[0].getValue();
    }
           
    public void printHeap(){
    
        for(int i = 0; i<=heapSize; i++){
            System.out.print(heap[i].getLabel()+":"+heap[i].getValue()+" ");
        }
        
    }
}
