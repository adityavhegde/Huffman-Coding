

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.net.URL;

/**
 *
 * @author Aditya
 */
public class encoder {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String filePath = args[0];
        URL path = encoder.class.getResource(filePath);
		System.out.println("File path test " + path);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path.getFile()));
        //BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        
        HashMap<Integer, Integer> frequency_map = new HashMap<>();
        HashMap<Integer, String> code_table = new HashMap<>();
        
        //Writer: CODE_TABLE.txt
        File f = new File("code_table.txt");
        f.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));

              
        //Frequency computation
        String eachLine;
        int count = 0;
            while((eachLine = bufferedReader.readLine()) != null) {
                int line = Integer.parseInt(eachLine);
                if(frequency_map.containsKey(line)){
                    count = frequency_map.get(line);
                    count = count + 1;
                    frequency_map.put(line, count);
                }
                else frequency_map.put(line, 1);
            }   
            bufferedReader.close();
         
         //Huffman codes creation
         /*s1> Insert all nodes in tree*/
         CacheOptimized h = new CacheOptimized();
          for (Map.Entry<Integer, Integer> entry : frequency_map.entrySet()){   
                    h.insert(new Node((int)entry.getKey(),(int)entry.getValue()));
           }
          
          /*s2> Build Huffman Tree from table */
          Node root = HeapBuildUtil.buildHuffmanTree(h);
          
          /*s3> Get the Huffman Codes from s2 Tree*/
          HeapBuildUtil.buildHuffmanCodes("", root, writer, code_table);
          //^adds huffCodes to hashMap and also writes them to a file
          
          writer.flush();
          writer.close();
          //Huffman-codes: ends
          
          
          //ENCODED.BIN
          
          //File Writer for the encoded
          File encoded = new File("encoded.bin");
          encoded.createNewFile();
          FileOutputStream writeEnc = new FileOutputStream(encoded);
          
          bufferedReader = new BufferedReader(new FileReader(filePath));
                    
          eachLine ="";
          count = 0;
          int offset = 0;
          StringBuilder stemp = new StringBuilder("");
            while((eachLine = bufferedReader.readLine()) != null) {
                int line = Integer.parseInt(eachLine);
                    if(code_table.containsKey(line)){                        
                        stemp.append(code_table.get(line));
                        count++;
                }
               
            }  
            System.out.println("Number of lines encoded> "+count);
            bufferedReader.close();
            
            byte[] temp = HeapBuildUtil.formByteArr(stemp.toString());
            writeEnc.write(temp);
            writeEnc.flush();
            writeEnc.close();
        
        
        
        
    }
}
