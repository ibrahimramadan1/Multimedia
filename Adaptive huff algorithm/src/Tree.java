public class Tree{
	Node root ,nyt;
	Tree(){

		root=new Node();
		root.id=128;
		nyt=new Node();
		root.huffcode="";
	}
	public Node search(char data) {
		
		return search (root,data);
	}
	public Node search( Node node, char data){
	    if(node != null){
	        if(node.data==data){
	           return node;
	        } else {
	            Node foundNode = search( node.left,data);
	            if(foundNode == null) {
	                foundNode = search( node.right,data);
	            }
	            return foundNode;
	         }
	    } else {
	        return null;
	    }
	}
	
	public Node search( Node node, int data){
	    if(node != null){
	        if(node.id==data){
	           return node;
	        } else {
	            Node foundNode = search( node.left,data);
	            if(foundNode == null) {
	                foundNode = search( node.right,data);
	            }
	            return foundNode;
	         }
	    } else {
	        return null;
	    }
	}

	public Node get_nyt_code( Node node){
		if(node != null){
			if(node.data=='*'&&node.left==null&&node.right==null){
				return node;
			} else {
				Node foundNode = get_nyt_code( node.left);
				if(foundNode == null) {
					foundNode = get_nyt_code( node.right);
				}
				return foundNode;
			}
		} else {
			return null;
		}
	}
	public Node search_huff( Node node, String data){
		if(node != null){
			if(node.huffcode.equals(data)){
				return node;
			} else {
				Node foundNode = search_huff( node.left,data);
				if(foundNode == null) {
					foundNode = search_huff( node.right,data);
				}
				return foundNode;
			}
		} else {
			return null;
		}
	}


	public String addNode(char data ) {
		String result = ""; 
		Node d =new Node ();
		Node f= new Node ();
		Node temp=new Node ();
		if(root.left==null && root.right==null) {
			
			root.left=nyt;
			nyt.huffcode="0";
			nyt.parent=root;
			nyt.id=root.id-2;
			d.data=data;
			d.parent=root;
			d.id=root.id-1;
			d.freq=1;
			d.huffcode="1";
			root.right=d;
			root.freq=1;			
		}
		else {
			d= search(data);
			if (d==null) {
				d=new Node();
				d.data=data;
				d.parent=nyt;
				d.huffcode=d.parent.huffcode+"1";
				d.freq=d.freq+1;
				d.id=d.parent.id-1;
				temp=nyt;
				while (temp.parent!=null) {
					temp.freq=temp.freq+1;
					temp=temp.parent;
				}
				root.freq=root.freq+1;
				nyt.right=d;
				nyt.left=f;
				f.parent=nyt;
				f.id=nyt.id-2;
				nyt=f;
				nyt.huffcode=nyt.parent.huffcode+"0";
				result= d.parent.huffcode+" "+d.huffcode;

		}
			else {
				d.freq++;
				Node b=new Node();
				int fr;
				char data1;
				boolean flag=false;
				result= d.parent.huffcode+" "+d.huffcode;
				for (int i=127;i>d.id;i--) {
					 flag=false;
					b=search(root,i);
					if (b.freq<d.freq&&b.right==null&&b.left==null) {						
						data1=b.data;
						fr=b.freq;
						b.data=d.data;
						b.freq=d.freq;
						d.data=data1;
						d.freq=fr;
						flag=true;
							break;
					}
				}
				if (flag) {
					Node mytemp=b;
				
				while (true) {
					mytemp=mytemp.parent;
					if (mytemp==null)
						break;
					mytemp.freq++;
					
					
				}
		
				}
				else {
					
				Node mytemp=d;
					
					while (true) {
						
						mytemp=mytemp.parent;
						if (mytemp==null)
							break;
						mytemp.freq++;
						
						
					}
				}
		
				
			}
			update();					
		}
		return result;
	}

	  

	public void swap(Node  a,Node b) {
		
			
		Node temp=new Node();
		Node temp1,temp2;
		temp.data=a.data;
		temp.freq=a.freq;
		temp.left=a.left;
		temp.right=a.right;
		temp.parent=a.parent;
	
		a.data=b.data;
		a.freq=b.freq;
		a.left=b.left;
		a.right=b.right;
		a.parent=b.parent;

	
		temp1=a;
		
		while (temp1.right!=null&&temp1.left!=null) {
			temp2=temp1.right;
			temp2.parent=temp1;
			temp1.left.parent=temp1;
			temp1=temp1.left;
		}

		b.data=temp.data;
		b.freq=temp.freq;
		b.left=temp.left;
		b.right=temp.right;
		b.parent=temp.parent;

		temp1=b;
		while (temp1.right!=null&&temp1.left!=null) {
			temp2=temp1.right;
			temp2.parent=temp1;
			temp1.left.parent=temp1;
			temp1=temp1.left;
		}
	
		
	}
	
	public void update() {
		if (root.left.freq>root.right.freq)
			swap(root.left,root.right);
	
		Node temp1=root.right,temp2=root.left;
		temp1.huffcode="1";
		temp2.huffcode="0";
		while (temp2.left!=null&&temp2.right!=null) {
			temp2.right.huffcode=temp2.huffcode+'1';
			temp2.left.huffcode=temp2.huffcode+'0';
			temp2=temp2.left;
			
		}
		while (temp1.left!=null&&temp1.right!=null) {
			temp1.right.huffcode=temp1.huffcode+'1';
			temp1.left.huffcode=temp1.huffcode+'0';
			temp1=temp1.left;
		}
	}
	
	
	public void printPostorder(Node node) 
    { 
        if (node == null) 
            return; 
          printPostorder(node.left); 
        printPostorder(node.right); 
  
        node.print();
    } 
}