import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;   
import java.util.Scanner;
public class TagList {
	
	ArrayList<Tag> list = new ArrayList<Tag>();
	public TagList() {
		
	}
	public void addTag(int x , char y) {
		Tag a= new Tag(x,y);
		list.add(a);
	}
	public void print() {
		for (Tag i : list) {
			i.Print();
		}
	}
	public void compress() throws IOException {
		String a,m="";
		int x=0;
		Scanner b= new Scanner(System.in);
		dictionary c= new dictionary();
		
		
		System.out.println("Enter A sentence to compress");
		a=b.nextLine();
		b.close();	
		
		for (int i=0 ; i<a.length();++i) {
			
			m=m+a.charAt(i);
			x=c.searchInDic(m);
			if (i==a.length()-1) {
				if (x!=-1)
					this.addTag(0,a.charAt(i));
				else {
					c.addToDic(m);
					this.addTag(0,a.charAt(i));
				}
				m="";
				continue;
			}
			int y=-1;
			while (x!=-1) {
				y=x;
				if (i==a.length()-1)
					break;
				if (i<a.length())
					i++;
				m=m+a.charAt(i);
				x=c.searchInDic(m);
	
			}
			c.addToDic(m);
			if(y>=0) 
				this.addTag(y,a.charAt(i));
			else
				this.addTag(0,a.charAt(i));
			m="";		
		}

		
		System.out.println("tags");
		this.print();
		System.out.println("dictionary");
		c.print();
	}
	/////////////ABAABABAABABBBBBBBBBBA
	
	
	
	
	public void decompress() {
		
		
		int num ;
		char c;
		
		dictionary d= new dictionary();
		String str="";
		Scanner inp = new Scanner (System.in);

		Scanner inp1 = new Scanner (System.in);
		while (true) {
			
			System.out.println("Enter index, -1 if u wanna stop: ");
			num=inp.nextInt();
			if (num<=-1)
				break;
			System.out.println("Enter char: ");
			str=inp1.nextLine();
			c=str.charAt(0);
			this.addTag(num, c);
		}
		
		inp.close();
		inp1.close();
		
		str="";
		for (Tag i : list) {
			if (i.num==0) {
				str=str+i.newchar;
				d.addToDic(str);
				str="";
			}
			else {
				str=d.dic.get(i.num)+i.newchar;
				d.addToDic(str);
				str="";
			}
		}
		
		System.out.println("tags");
		this.print();
		System.out.println("dictionary");
		d.print();
		System.out.print("sentence: ");
		for (int i=1;i<d.dic.size();++i)
			System.out.print(d.dic.get(i));
		
		
	}
	
	public void saveInFile()
    {
    	try {
        FileWriter file =new FileWriter("listag.txt");
        String m="";
        for (int i=0;i<list.size();++i) {
        	m=m+(list.get(i).num);
        	m=m+list.get(i).newchar;
        	file.write(m+'\n');
        	m="";
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
    	FileReader file=new FileReader("listag.txt");
    	Scanner myreader = new Scanner (file);
    	
    	while (myreader.hasNextLine()) {
    		
        		String s=myreader.nextLine();
        		int x= s.charAt(0)-'0';
        		char y= s.charAt(1);
        		this.addTag(x, y);
    		
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
