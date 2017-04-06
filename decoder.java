
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Aditya
 */
public class decoder {
    public static void main(String[] args) throws IOException {
            String encoded_file = args[0];
            String code_table_file = args[1];
        
            //HUFFMAN TREE -> FROM CODES
            Node decodeRoot = new Node(-99,-99);
            
            /*
            FileReaders -> read code table
            */
            BufferedReader code_table_reader = new BufferedReader(new FileReader(new File(code_table_file)));
            String line = "";
            while((line = code_table_reader.readLine())!=null){
                String[] temp = line.split(" ");
                addLine(temp[0],temp[1],decodeRoot);
            }
            
          
           //HUFFMAN TREE + ENCODED.BIN -> DECODED.txt
           
           //Writer for decoder 
           BufferedWriter writer_for_decoder = new BufferedWriter(new FileWriter(new File("decoded.txt")));
           
           //Reader for Encoded.bin
           FileInputStream binReader = new FileInputStream(encoded_file);
           
           byte[] b;
           String result=""; //stores the entire binary structure of ENCODED.bin
           while (binReader.available() > 1) {
                int n = binReader.available();  
                b = new byte[n];
                binReader.read(b);
                result = toBinaryString(b);
            }    
            binReader.close();
            //write DECODED.txt
            decodedWriter(writer_for_decoder, decodeRoot, result);
        
            writer_for_decoder.flush();
            writer_for_decoder.close();
        
        
    }
    
    public static void addLine(String label, String code, Node root){
        Node temp = root;
        
        for(int i = 0; i<code.length();i++){
            if(0 == (int)(code.charAt(i)-48)){
                if(temp.getLeftNode() == null){
                    if(i==code.length()-1){
                        temp.setLeftNode(new Node(Integer.parseInt(label),-99));
                    }
                    else{
                    temp.setLeftNode(new Node(-99,-99));
                    temp = temp.getLeftNode();
                    }
                   
                }            
                else{
                    temp = temp.getLeftNode();
                }
            
            }
            else{
                if(temp.getRightNode()==null){
                    if(i == code.length()-1){
                        temp.setRightNode(new Node(Integer.parseInt(label),-99));
                    
                    }else{
                        temp.setRightNode(new Node(-99,-99));
                        temp = temp.getRightNode();
                    
                    }
                
                }
                else temp = temp.getRightNode();
            }
        
        }
    
    }
    
    public static String toBinaryString( byte[] bytes ){
        StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
        
        for( int i = 0; i < Byte.SIZE * bytes.length; i++ )
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        
         return sb.toString();
    }
    
    /*Method below: ENCODED.bin -> DECODED.txt*/
    public static void decodedWriter(BufferedWriter writer, Node root, String code) throws IOException{
        Node temp = root; 
        
        for(int i = 0;i<code.length();i++){
            if((int)(code.charAt(i)-48)==0 && temp.getLeftNode()==null){
                writer.write(temp.getLabel()+"\n");
                temp = root.getLeftNode();
            }
            else if((int)(code.charAt(i)-48)==1 && temp.getLeftNode()==null){
                writer.write(temp.getLabel()+"\n");
                temp = root.getRightNode();                
            
            }
            else if((int)(code.charAt(i)-48)==0){
                temp = temp.getLeftNode();
            }
            else temp = temp.getRightNode();
        
        }
		if(temp !=null) writer.write(temp.getLabel()+"\n");

    
    
    }
    
    
}
