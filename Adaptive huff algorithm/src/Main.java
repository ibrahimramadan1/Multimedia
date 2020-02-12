import java.util.Scanner;

public class Main {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
while (true) {
	System.out.println("1-Compression\n2-De_Compression\n3-Exit");
	Scanner input = new Scanner(System.in);
	int choice = input.nextInt();
	if (choice == 1) {
		Scanner input2 = new Scanner(System.in);
		String data = input2.nextLine();
		adaptiveHuffman a = new adaptiveHuffman();
		System.out.println(a.compress(data));
	} else if (choice == 2) {
		Scanner input2 = new Scanner(System.in);
		String data = input2.nextLine();
		adaptiveHuffman a = new adaptiveHuffman();
		System.out.println(a.Decompress(data));

	} else {
		break;

	}
		
		
}
	


	}

}
