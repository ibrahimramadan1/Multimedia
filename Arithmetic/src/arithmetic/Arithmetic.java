package arithmetic;

import java.io.*;
import java.util.*;

public class Arithmetic {
	///coding
	public static void calculate_Probability( String data, ArrayList<Character> symbols,
	           ArrayList<Double> probability){
	       
	        for(int i=0;i<data.length();i++)
	            symbols.add(data.charAt(i));
	        Collections.sort(symbols);
	        for(int i=0;i<symbols.size();i++){
	           int count=1;
	           for(int j=i+1;j<symbols.size();j++){
	               if(symbols.get(i)==symbols.get(j)){
	                   symbols.remove(j--);
	                   count++;
	               }
	           }  
	           double symbol_probability=(double)count/(double)data.length();
	           probability.add(symbol_probability);
	           
	       }
	          
	   }
	   public static void calculate_Ranges( ArrayList<Double> probability, ArrayList<Double> ranges){
	        for(int i=0;i<probability.size();i++){
	           if(i==0){
	           ranges.add(0.0);
	           ranges.add(probability.get(i));
	           }
	           else{
	               int previous=ranges.size()-1;
	              double pro=probability.get(i)+ranges.get(previous);
	              ranges.add(pro);
	           }   
	       }    
	   }
	   public static double compression(String data){
	       
	        ArrayList<Character> symbols=new ArrayList<>();
	        ArrayList<Double> probability=new ArrayList<>();
	        ArrayList<Double> ranges=new ArrayList<>();
	        
	        calculate_Probability(data,symbols,probability);       
	        calculate_Ranges(probability,ranges);
	        
	        System.out.println(symbols);
	        System.out.println(probability);
	        System.out.println(ranges);
	        
	        double lower=0.0;
	        double upper=0.0;
	        
	       for (int i = 0; i < data.length(); i++) {
	           int index = symbols.indexOf(data.charAt(i));

	           if (i == 0) {
	               lower += ranges.get(index);
	               upper += ranges.get(index + 1);
	           } else {
	               double temp_lower = lower;
	               double temp_upper = upper;
	               lower = temp_lower + (temp_upper - temp_lower) * ranges.get(index);
	               upper = temp_lower + (temp_upper - temp_lower) * ranges.get(index + 1);
	           }
	}
	       saveCompressedFile(data.length(),symbols.size(),symbols,probability,((lower+upper)/2));
	       return (lower+upper)/2;
	              
	   }
	   
	   
	   public static String ReadDeCompressedFile() {
		   
		   String a="";
		   try {
			   FileReader f= new FileReader ("decompressed.txt");
			   Scanner input = new Scanner(f);
			   a=input.nextLine();
			   input.close();
			   f.close();
			   return a;

		   }
		   catch (Exception e) {
			   
		   }
		return a;
	   }
	   
	   public static void saveCompressedFile(int x,int y,ArrayList<Character>symbols,ArrayList<Double>probability,double code) {
		   try {
			   FileOutputStream  f= new FileOutputStream  ("compressed.txt");
			   DataOutputStream d = new DataOutputStream(f);
			   d.writeInt(x);
			   d.writeInt(y);
			   
			   for (int i=0;i<y;i++) {
				   d.writeChar(symbols.get(i));
			   }
			   for (int i=0;i<y;i++) {
				   d.writeDouble(probability.get(i));
			   }
			   d.writeDouble(code);
			   f.close();
		   }
		   catch(Exception e) {
			   
		   }
	   }
	   
	   
	   
	   ///decoding
	public static String Decoding (int x,int n,double code, ArrayList<Character>chars,
                ArrayList<Double>freq) {
		ArrayList<Double>lower=new ArrayList<Double>();
		ArrayList<Double>upper=new ArrayList<Double>();
		ArrayList<Double>Newlower=new ArrayList<Double>();
		ArrayList<Double>Newupper=new ArrayList<Double>();
		
		double temp=0.0;
		for (int i =0;i<n;i++) {
			lower.add(temp);
			upper.add(temp+freq.get(i));
			Newlower.add(temp);
			Newupper.add(temp+freq.get(i));
			temp=temp+freq.get(i);
		}
		String decoded="";
		for (int i=0;i<x;++i) {
			double temp1 = 0,temp2=0;
			for(int j=0;j<n;++j) {
				
				if (code<upper.get(j)&&code>lower.get(j)) {
					decoded+=chars.get(j);
					temp1=lower.get(j);
					temp2=upper.get(j);
				}
			}
				
				
				
				for (int k=0;k<n;++k) {
					
					Newlower.set(k, (temp1+(temp2-temp1)*lower.get(k)));
					Newupper.set(k,(temp1+(temp2-temp1)*lower.get(k)));
					//System.out.println(chars.get(k)+"  "+lower.get(k)+"   "+upper.get(k));
					
				}
				code=(code-temp1)/(temp2-temp1);
				
			
			
		}
		saveDecompressedFile(decoded);
                System.out.println(decoded);

                return decoded;
		
		
		
	}
	
	
	
	public static void saveDecompressedFile(String a) {
		try{
			FileWriter f= new FileWriter ("decompressed.txt");
			f.write(a+"\n");
			f.close();
		}
		catch (Exception e) {
			
		}
	}
	public static void ReadCompressedFile() {
		try {
			ArrayList<Character>symbols=new ArrayList<Character>() ;
			ArrayList<Double>prob= new ArrayList<Double>();
			FileInputStream f = new FileInputStream("compressed.txt");
			DataInputStream d=new DataInputStream(f);

			
			int x= d.readInt();
			int y=d.readInt();			
			for (int i=0;i<y;i++) {
				char a='*';
				a=d.readChar();
				symbols.add(a);
				
			}
			for (int i=0;i<y;i++) {
				double a =0;
					a=	d.readDouble();
					prob.add(a);
				
			}
			double code=0;
			code=d.readDouble();
			Decoding(x,y,code,symbols,prob);
			f.close();
			d.close();
			}
		
		catch(Exception e) {
			
		}
	}
	
	
	
	
	public static void main(String[]args) {
             gui g=new gui();
             gui2 g2=new gui2();
            g.setVisible(true);
            g2.setVisible(true);
		/*double result=compression("BILL GATES");
                System.out.println(result);*/
		
	}
}
