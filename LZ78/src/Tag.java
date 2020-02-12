
public class Tag {
	int num;
    char newchar;
    public Tag(){

    }
    public Tag(int x , char z){
    	num=x;
    	newchar=z;
    }
    public void Print() {
    	System.out.println("<"+ num +" , "+ newchar +">");


    }
}
