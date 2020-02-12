import java.util.HashMap;

public class adaptiveHuffman {
	 HashMap<Character,String> dic = new HashMap<Character, String>();    //Short code
	HashMap<String,Character> dic2 = new HashMap<String, Character>();

	Tree t;
	adaptiveHuffman(){
		t= new Tree();
		for ( int i = 0 ; i < 128 ; i++){
            String code=Integer.toBinaryString(i);
            while(code.length()<8)
                code='0'+code;
            dic.put( ((char)i ) ,code);
			dic2.put(code ,((char)i ) );
        }
	}
	public String compress(String x) {
		String result ="";
		Node se;
		String temp; 
		result=result+dic.get(x.charAt(0));
		t.addNode(x.charAt(0));
		for (int i=1;i<x.length();i++) {
			se=t.search(x.charAt(i));
			if (se==null) {
				temp=t.addNode(x.charAt(i));
				String[] arr = temp.split(" ");
				result=result+arr[0];
				result=result+dic.get(x.charAt(i));
			}
			else {
				temp=t.addNode(x.charAt(i));
				String[] arr1 = temp.split(" ");
				result=result+arr1[1];
			}
		}
		return result;
	}
	
	public String Decompress(String x) {
		String result ="";
		int i=0;
		while (i<x.length())
		{
			if(i==0)
			{
				String temp=x.substring(0,8);
				t.addNode(dic2.get(temp));
				result=result+dic2.get(temp);
				i=i+8;
				continue;
			}
			else
			{
				Node n=t.get_nyt_code(t.root);
				String nyt_code=n.huffcode;


				int j=nyt_code.length();
				String temp="";
				if(i+j>=x.length())
				{
				temp=x.substring(i,x.length());
				}
				else
				{ temp=x.substring(i,i+j);}

				Node s=t.search_huff(t.root,temp);

				if(temp.equals(nyt_code))
				{
					i=i+j;
					temp=x.substring(i,i+8);
					t.addNode(dic2.get(temp));
					result=result+dic2.get(temp);
					i=i+8;
					continue;
				}

				else if (s!=null)
				{
					result=result+s.data;
					t.addNode(s.data);
					i=i+j;
					continue;
				}
				else {
					Node y=new Node();
					while (true)
					{
						temp=temp.substring(0,temp.length()-1);
						 y=	t.search_huff(t.root,temp);
						if(y!=null)
							break;
					}
					result=result+y.data;
					t.addNode(y.data);
					i=i+temp.length();
					continue;
				}
			}
		}
		return  result;

	}
	public void printHM() {
		for (HashMap.Entry<Character,String> entry : dic.entrySet()) {
            System.out.println("Key = " + entry.getKey() + 
                             ", Value = " + entry.getValue()); 
    } 
	}
}
