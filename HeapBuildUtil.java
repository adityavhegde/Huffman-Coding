import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Aditya
 */
public class HeapBuildUtil {
    
    public static Node buildHuffmanTree(PairingHeap h){   
       Node res = null;
        while(h.getHeapSize()>1){
            Node x = new Node(h.removeMin());
            Node y = new Node(h.removeMin());

            res = x.addNode(y);
            if(x.getHuffmanTree() == null && y.getHuffmanTree()==null)
                res.setHuffmanTree(res);
            else if(x.getHuffmanTree()!=null && y.getHuffmanTree()==null){
                res.setHuffmanTree(y.addNode(x.getHuffmanTree()));
                x.setHuffmanTree(null);
            }
            else if(x.getHuffmanTree()==null && y.getHuffmanTree()!=null){
                res.setHuffmanTree(x.addNode(y.getHuffmanTree()));
                y.setHuffmanTree(null);
            }
            else if(x.getHuffmanTree()!=null && y.getHuffmanTree()!=null){
                res.setHuffmanTree(y.getHuffmanTree().addNode(x.getHuffmanTree()));
                x.setHuffmanTree(null);
                y.setHuffmanTree(null);
            }
            h.insert(res);
        }
        return res.getHuffmanTree();
    }
    
    public static Node buildHuffmanTree(BinaryHeap h){
        while(h.getSize()>1){
            Node x = h.removeMin();
            Node y = h.removeMin();
            Node res = x.addNode(y);
            x = null;
            y = null;
            h.insert(res);   
        }
        return h.removeMin();
    
    }
    
    public static Node buildHuffmanTree(CacheOptimized h){
        while(h.getSize()>1){
            Node x = h.removeMin();
            Node y = h.removeMin();
            Node res = x.addNode(y);
            h.insert(res);   
        }
        return h.removeMin();
    
    }
    
    public static Node buildHuffmanTree(CacheOptimizedG h){
        while(h.getSize()>1){
            Node x = h.removeMin();
            Node y = h.removeMin();
            Node res = x.addNode(y);
            h.insert(res);   
        }
        return h.removeMin();
    
    }
    
 
    public static void printTree(Node n){
        if(n==null) return;
        System.out.println(n.getLabel()+" "+n.getValue());
        printTree(n.getLeftNode());
        printTree(n.getRightNode());
        
    }
    
    /*
    Method buildHuffmanCodes
    Input: Huffman Tree
    Output: 1. Huffman Codes all elements
            2. code_table.txt (huffman)
    */
    public static void buildHuffmanCodes(String code, Node n, BufferedWriter writer, HashMap<Integer, String> code_table) throws IOException{
        
        if(n==null) return;
        if(n.getLeftNode()==null && n.getRightNode()==null){
            String line = n.getLabel()+" "+code+"\r"; //writing in the code_table, NOT encoded.bin
            if(!code_table.containsKey(n.getLabel())){
                code_table.put(n.getLabel(),code);
                writer.write(line);

            }
        }
        else{
            buildHuffmanCodes(code.concat("0"),n.getLeftNode(), writer, code_table);
            buildHuffmanCodes(code.concat("1"),n.getRightNode(), writer, code_table);

        }
    
    }

    
    public static byte[] formByteArr(String s){
      int sLen = s.length();
      int size = (sLen + Byte.SIZE - 1) / Byte.SIZE;
      byte[] toReturn = new byte[(sLen + Byte.SIZE - 1) / Byte.SIZE];
        //System.out.println("Byte array size>"+(sLen + Byte.SIZE - 1) / Byte.SIZE);
      char c;
      
      for( int i = 0; i < sLen; i++ )
          if( (c = s.charAt(i)) == '1' )
              toReturn[i / Byte.SIZE] = (byte) (toReturn[i / Byte.SIZE] | (0x80 >>> (i % Byte.SIZE)));
          else if ( c != '0' )
              throw new IllegalArgumentException();
      
      return toReturn;
  }
}
