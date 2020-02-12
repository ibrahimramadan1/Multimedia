import java.io.IOException;   

public class main {

	public static void main(String[] args) throws IOException {
		/*TagList a= new TagList();
		a.decompress();*/
		/*dictionary a= new dictionary();
		a.addToDic("aaaaaa");
		a.addToDic("aaaaaa");
		a.addToDic("aaaaaa");
		a.saveInFile();
		dictionary b= new dictionary();
		b.readFile();
		b.print();*/
		TagList a= new TagList();
		a.addTag(0, 'a');
		a.addTag(0, 'b');
		a.addTag(1, 'a');
		a.saveInFile();
		TagList b= new TagList();
		b.readFile();
		b.print();
	
	}
	
}