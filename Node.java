
/**
 *
 * @author Aditya
 * This Node class is used to build the Huffman tree
 * In Binary Heaps, this will happen implicitly
 * Not to confuse with Node structures of Pairing and 4-way heaps
 */
public class Node implements Comparable {
    private int label = -99;
    private int value = -99; //infinity setting
    private Node leftNode;
    private Node rightNode; 
    private Node huffmanTree;

    public Node(int label, int value) {
        this.label = label;
        this.value = value;
        this.leftNode = null;
        this.rightNode = null;
    }
    
    public Node(int label, int value, Node huffmanTree) {
        this.label = label;
        this.value = value;
        this.leftNode = null;
        this.rightNode = null;
        this.huffmanTree = huffmanTree;
    }
    
    public Node(Node n){
        this.label = n.getLabel();
        this.value = n.getValue();
        this.leftNode = null;
        this.rightNode = null;
        this.huffmanTree = n.getHuffmanTree();     
    }
  
    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }

    public Node getHuffmanTree() {
        return huffmanTree;
    }

    public void setHuffmanTree(Node huffmanTree) {
        this.huffmanTree = huffmanTree;
    }
    
    @Override
    public int compareTo(Object object) {
            Node child = (Node)object; //object is the child of parent to which it is compared
           if(this.value > child.getValue()) return 1;
           else return -1;      
    }
    /*
    Method: addNode
    1. Adds a node 'a' to a given node b
    2. smaller value to left - larger value to right
    3. Label of top value -> -99/infinity
    */
   
    public Node addNode(Node a){
        Node result;
        result = new Node(-99,this.getValue()+a.getValue());
        if(this.getValue()>a.getValue()){ 
        result.setLeftNode(a);
        result.setRightNode(this);
        }
        else{
        result.setLeftNode(this);
        result.setRightNode(a);
                
        }
        return result;
    
    }
       
}
