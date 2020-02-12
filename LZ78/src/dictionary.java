import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;   
import java.util.Scanner;

public class dictionary {
	ArrayList<String> dic = new ArrayList<String>();
	public dictionary() {
		dic.add("null");
	}
    public int searchInDic (String key) {
    	if (dic.contains(key))
    		return dic.indexOf(key);
    	return -1;
    }
    
    public void addToDic(String key) {
    	dic.add(key);

    }
    
    public void print() {
    	for (int i=0;i<dic.size();i++) {
    		System.out.println(i+"   "+dic.get(i));
    	}
    }
    
    public void saveInFile()
    {
    	try {
        FileWriter file =new FileWriter("dictionary.txt");
        for (int i=1;i<dic.size();++i) {
        	file.write(dic.get(i)+'\n');
        }
        file.close();
    	}
    	catch(IOException e)
    	{
    		System.out.println("Error happen");
    		
    	}
    }
    public void readFile() {
    	try {
    	FileReader file=new FileReader("dictionary.txt");
    	Scanner myreader = new Scanner (file);
    	while (myreader.hasNextLine()) {
    		String a = myreader.nextLine();
    		dic.add(a);
    		
    	}
    	file.close();
    	myreader.close();
    	}
    	catch(IOException e)
    	{
    		System.out.println("Error happen");
    		
    	}
    }
    
}
