
import java.util.ArrayList;

/**
 *
 * @author Aditya
 * This is 4-way cache optimized heap
 */
public class CacheOptimizedG {
    final private int shift = 3;
    
    final private ArrayList<Node> heap;

    public CacheOptimizedG() {
        heap = new ArrayList<>();
        //shifting root to the 3rd position
        for(int i = 0;i<shift;i++){
            heap.add(null); //0th
        }
    }
    
    public void insert(Node n){
        heap.add(n);
        
        int index = heap.size()-1;

        while(index>shift && heap.get(getParent(index)).compareTo(heap.get(index))>=1){
            //System.out.println("Debug>"+index);
            int i = getParent(index);
            //if(heap.get(index).getValue() == 8) System.out.println("Printing parent of 8"+getParent(index));
            Node temp = heap.get(index);
            heap.set(index,heap.get(i));//heap[index] = heap[i];
            heap.set(i,temp);
            
            index = i;
        
        } //while-ends
    
    }
    
    public Node removeMin(){
        if(heap.size()>(shift+1)){
        Node min = heap.get(shift);
        heap.set(shift, heap.remove(heap.size()-1));
        buildMinHeap(shift);
        return min;
        }
        else if(heap.size()==(shift+1)) return heap.remove(shift);
        else return null;
    }
    
    public void buildMinHeap(int index){
        int left1 =  getL1(index);
        int left2 =  getL2(index);
        int right1 = getR1(index);
        int right2 = getR2(index);
        
        int minIndex = index;
        int heapSize = heap.size()-1;
        
        
        if(left1<=heapSize && heap.get(left1).compareTo(heap.get(index))<0){ /*heap[left]<heap[index]*/
            minIndex = left1;
        }
        if(left2<=heapSize && heap.get(left2).compareTo(heap.get(minIndex))<0){ /*heap[left]<heap[index]*/
            minIndex = left2;
        }
        if(right1<=heapSize && heap.get(right1).compareTo(heap.get(minIndex))<0){
            minIndex = right1;
        }
        if(right2<=heapSize && heap.get(right2).compareTo(heap.get(minIndex))<0){
            minIndex = right2;
        }        
        //If the given index, is not the minIndex, we need to change something
        //Keep swapping with elements below, so that the element reaches its correct place in the heap
        
        if(minIndex!=index){
           Node temp = heap.get(index);
           heap.set(index, heap.get(minIndex));//heap[index] = heap[minIndex];
           heap.set(minIndex, temp);//heap[minIndex] = temp;
           
           //call back code
           buildMinHeap(minIndex);
        
        }
    
    }
    
    public int getParent(int index){
        if(index == shift) return index;
        else return ((index-1-shift)/4)+shift;
                
    }
    
    public int getL1(int index){
       return ((index-shift)*4)+1+shift;
    }
    public int getL2(int index){
        return ((index-shift)*4)+2+shift;
    }
    
    public int getR1(int index){
        return ((index-shift)*4)+3+shift;        
    }
    public int getR2(int index){
        return ((index-shift)*4)+4+shift;

    }
    
    /*Util functions*/
        public boolean isEmpty(){
        return !(heap.size()>shift);
    }
    
    public int getSize(){
        return heap.size()-shift; //why? heapSize in this code acts as Index
    }
          
    public int min() {
        //just tells you what the current min element is
        return heap.get(shift).getValue();
    }
           
    public void printHeap(){
    
        if(!isEmpty()){
        for(int i = 3; i<heap.size(); i++){
            System.out.print(heap.get(i).getLabel()+":"+heap.get(i).getValue()+" ");
        }
        }
    }

    
}