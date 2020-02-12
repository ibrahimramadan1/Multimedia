/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpeg;


import java.io.*;
import java.util.*;
/**
 *
 * @author LENOVO
 */
class node{
    int frequency;
    String data;
    node left;
    node right;
}
class huffman{
    
    public static void huffman_encoding(   ArrayList<String> Category_descriptor
            , ArrayList<Integer> probabilities,ArrayList<String>Symbols , ArrayList<String>codes  ){
       node root=null; 
       ArrayList<node> arr=new ArrayList<>();
 
        for(int i=0;i<probabilities.size();i++){
            node temp= new node();
            temp.data=Category_descriptor.get(i);
            temp.frequency=probabilities.get(i);
            temp.left=null;
            temp.right=null;
            arr.add(temp);
            Collections.sort(arr,Comparator.comparing(node->node.frequency));
        }
       while(arr.size()>1){
           node a=arr.remove(0);
           node b=arr.remove(0);
           
            node sum=new node();
            sum.data=a.data+b.data;
            sum.frequency=a.frequency+b.frequency;
            sum.left=a;
            sum.right=b;
            
            arr.add(sum);
            Collections.sort(arr,Comparator.comparing(node->node.frequency));
            root=sum;
       }
       huffman_code(Symbols,codes,root,"");
    }
    public static void huffman_code(ArrayList<String>Symbols ,ArrayList<String>codes,node root,String x){
        if(root.left==null&&root.right==null){
            Symbols.add(root.data);
            codes.add(x);
            return;
            
        }
        huffman_code(Symbols,codes,root.left,x+"0");
        huffman_code(Symbols,codes,root.right,x+"1");
            
        
    }
       
}
public class JPEG {
    
    public static void get_descriptor(String[] arr, ArrayList<String> descriptor) {
        int count = 0;
        String temp = "";
        for(int i = 0; i < arr.length; i++) {
            if (arr[i].charAt(0) == '0') {
                count++;
            } else {
                temp += Integer.toString(count) + "/" + arr[i];
                descriptor.add(temp);
                count = 0;
                temp = "";

            }
        }
        if (count != 0) {
            descriptor.add("EOB");
        }

    }

    public static void get_category_descriptor(ArrayList<String> descriptor,
        ArrayList<String> Category_descriptor) {
        String temp2 = "";
        for (int i = 0; i < descriptor.size(); i++) {
            temp2 = descriptor.get(i);
            String arr2[] = temp2.split("/");
            if (arr2.length == 1) {
                Category_descriptor.add("EOB");
            } else {
                String temp3 = "";
                int category = category(Integer.parseInt(arr2[1]));
                temp3 += arr2[0] + "/" + Integer.toString(category);
                Category_descriptor.add(temp3);
            }

        }

    }
    public static void get_probabilities(ArrayList<Integer> probabilities
            ,ArrayList<String> Category_descriptor){
        for(int i=0;i<Category_descriptor.size();i++){
            int count=1;
            for(int j=i+1;j<Category_descriptor.size();j++){
                if(Category_descriptor.get(i).equals(Category_descriptor.get(j))){
                   Category_descriptor.remove(j--);
                   count++;
                }
            }
    

            probabilities.add(count);
            
        }
        
    }

    public static int category(int value) {
        if (value >= -1 && value <= 1) {
            return 1;
        } else if (value >= -3 && value <= 3) {
            return 2;
        } else if (value >= -7 && value <= 7) {
            return 3;
        } else if (value >= -15 && value <= 15) {
            return 4;
        } else if (value >= -31 && value <= 31) {
            return 5;
        } else if (value >= -63 && value <= 63) {
            return 6;
        } else if (value >= -127 && value <= 127) {
            return 7;
        } else if (value >= -255 && value <= 255) {
            return 8;
        } else if (value >= -511 && value <= 511) {
            return 9;
        } else if (value >= -1023 && value <= 1023) {
            return 10;
        }
        return -1;
    }
    
    public static String get_binary(String x){
        String temp = "";
        if (x.charAt(0) != '-') {
            int value = Integer.parseInt(x);
            temp = Integer.toBinaryString(value);

        } else {
            String temp2 = "";
            x = x.substring(1);
            int value = Integer.parseInt(x);
            temp = Integer.toBinaryString(value);
            for (int i = 0; i < temp.length(); i++) {
                if (temp.charAt(i) == '0') {
                    temp2 += '1';
                } else {
                    temp2 += '0';
                }
            }
            temp = temp2;

        }
        return temp;
        
    }
    
    public static String JPEG_Encoding(String data){
        
        String[] arr = data.split(",");

        ArrayList<String> descriptor = new ArrayList<>();
        get_descriptor(arr, descriptor);

        ArrayList<String> Category_descriptor = new ArrayList<>();
        get_category_descriptor(descriptor, Category_descriptor);
        
        for(int i=0;i<descriptor.size();i++){
            if(descriptor.get(i)=="EOB")
                break;
            String []arr2=descriptor.get(i).split("/");
            descriptor.set(i, arr2[1]);
        }

      
        ArrayList<String> temp_Category_descriptor = new ArrayList<>();
        for(int i=0;i<Category_descriptor.size();i++){
            temp_Category_descriptor.add(Category_descriptor.get(i));
        }

        ArrayList<Integer> probabilities =new ArrayList<>();
        get_probabilities(probabilities,temp_Category_descriptor);
        
        ArrayList<String>Symbols=new ArrayList<>();
        ArrayList<String>codes=new ArrayList<>();
        
        
        huffman h=new huffman(); 
        h.huffman_encoding(temp_Category_descriptor,probabilities,Symbols,codes);

       
       String Compressed_data="";
       
       for(int i=0;i<descriptor.size();i++){
           int index=Symbols.indexOf(Category_descriptor.get(i));
           Compressed_data+=codes.get(index);
          
           if(!"EOB".equals(descriptor.get(i))){
                 Compressed_data+=",";
                 Compressed_data+=get_binary(descriptor.get(i));
           }
            Compressed_data+=" ";

       }
       writeCompressedFile(Compressed_data,Symbols,codes);
       return Compressed_data;
    }
    public static void writeCodes(String comp,ArrayList<String>codes){
        try {
        FileWriter file =new FileWriter("codes.txt");
        file.write(comp+'\n');
        for (int i=0;i<codes.size();++i) {
        	file.write(codes.get(i)+'\n');
        }
        file.close();
    	}
    	catch(IOException e)
    	{
    		System.out.println("Error happen");
    		
    	}
    }
    public static void writeSymbols(ArrayList<String>Symbols){
        try {
        FileWriter file =new FileWriter("Symbols.txt");
        for (int i=0;i<Symbols.size();++i) {
        	file.write(Symbols.get(i)+'\n');
        }
        file.close();
    	}
    	catch(IOException e)
    	{
    		System.out.println("Error happen");
    		
    	}
    }
    public static void writeCompressedFile(String comp,ArrayList<String>Symbols,ArrayList<String>codes){
        writeCodes(comp, codes);
        writeSymbols(Symbols);
    }
    
    public static ArrayList<String> readCodes() {
    	try {
    	FileReader file=new FileReader("codes.txt");
    	Scanner myreader = new Scanner (file);
        ArrayList<String>codes=new ArrayList<>();
    	while (myreader.hasNextLine()) {
    		String a = myreader.nextLine();
    		codes.add(a);	
    	}
    	file.close();
    	myreader.close();
        return codes;
    	}
    	catch(IOException e)
    	{
    		System.out.println("Error happen");
    		
    	}
        return null;
    }
    
    public static ArrayList<String> readSymbols() {
    	try {
    	FileReader file=new FileReader("Symbols.txt");
    	Scanner myreader = new Scanner (file);
        ArrayList<String>Symbols=new ArrayList<>();
    	while (myreader.hasNextLine()) {
    		String a = myreader.nextLine();
    		Symbols.add(a);	
    	}
    	file.close();
    	myreader.close();
        return Symbols;
    	}
    	catch(IOException e)
    	{
    		System.out.println("Error happen");
    		
    	}
        return null;
    }
    public static String readCompressedFile(){
        ArrayList<String>Symbols=readSymbols();
        ArrayList<String>codes=readCodes();
        String comp=codes.get(0);
        codes.remove(0);
        String a= JPEG_decoding(comp,Symbols,codes);
        return a;
    }
    
    public static void writeDecompress(String decomp){
        try{
        FileWriter file =new FileWriter("decompressed.txt");
        file.write(decomp+"\n");
        file.close();
        }catch(IOException e){
            
        }
    }
    public static String JPEG_decoding(String Compressed_data,ArrayList<String>Symbols,ArrayList<String>codes){
        
         String data="";
        String[] arr = Compressed_data.split(" ");
        for (int i = 0; i < arr.length; i++) {
            String[] arr2 = arr[i].split(",");
            if (arr2.length == 1) {
                data += Symbols.get(codes.indexOf(arr2[0]));
                continue;
            }
            String temp = Symbols.get(codes.indexOf(arr2[0]));
            String[] arr3 = temp.split("/");
            int length_zero = Integer.parseInt(arr3[0]);
            for (int j = 0; j < length_zero; j++) {
                data += "0" + ",";
            }
            
            temp = arr2[1];
            if("0".equals(temp)){
                data+="-1"+",";
                continue;
            }
            else if("1".equals(temp)){
                data+="1"+",";
                continue;
            }
            else{
                        
            int decimal = Integer.parseInt(temp, 2);
            String test = Integer.toBinaryString(decimal);
            if (temp.length()==test.length()) {

                data += Integer.toHexString(decimal) + ",";
            } else {
                String temp2 = "";
                for (int k = 0; k < temp.length(); k++) {
                    if (temp.charAt(k) == '0') {
                        temp2 += '1';
                    } else {
                        temp2 += '0';
                    }
                }
                int decimal_2 = Integer.parseInt(temp2, 2);
                data += "-" + Integer.toHexString(decimal_2) + ",";

            }

        }
        }
        writeDecompress(data);
        return data;
        
        
    }
    
     
    }
      

