public class Node {

	int id , freq;
	Node left,right,parent;
	String huffcode; 
	char data;
	Node(){
		data='*';
		freq=0;
		left =null;
		right= null;
		parent = null;
	}
	public void  print() {
		System.out.println(data + "  freq: " + freq +"   id:"+id+"   hc: "+huffcode); 
	}
}