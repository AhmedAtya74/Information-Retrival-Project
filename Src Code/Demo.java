import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
public class Demo {

	public static void main(String[] args) throws IOException {
		InvertedIndex index = new InvertedIndex();

        index.buildIndex(new String[]{
        		"C:\\Users\\dell\\eclipse-workspace\\IR Project\\src\\tmp\\100.txt", 
	            "C:\\Users\\dell\\eclipse-workspace\\IR Project\\src\\tmp\\101.txt", 
	            "C:\\Users\\dell\\eclipse-workspace\\IR Project\\src\\tmp\\102.txt", 
	            "C:\\Users\\dell\\eclipse-workspace\\IR Project\\src\\tmp\\103.txt", 
	            "C:\\Users\\dell\\eclipse-workspace\\IR Project\\src\\tmp\\104.txt", 
	            "C:\\Users\\dell\\eclipse-workspace\\IR Project\\src\\tmp\\105.txt", 
	            "C:\\Users\\dell\\eclipse-workspace\\IR Project\\src\\tmp\\106.txt", 
	            "C:\\Users\\dell\\eclipse-workspace\\IR Project\\src\\tmp\\107.txt", 
	            "C:\\Users\\dell\\eclipse-workspace\\IR Project\\src\\tmp\\108.txt", 
	            "C:\\Users\\dell\\eclipse-workspace\\IR Project\\src\\tmp\\109.txt"
        });

        System.out.println("Welcome to IR");
		String phrase = "";
		do {
			System.out.println("1) Intersect.");
			System.out.println("2) Find");
			System.out.println("3) Find_01 [2 Terms]");
			System.out.println("4) Find_02 [3 Terms]");
			System.out.println("5) Find_03 [N Terms]");
			System.out.println("6) Find_04 [N Terms] - Optimized");
			System.out.println("7) Compare");
			System.out.print("> ");
		    Scanner choice = new Scanner(System.in);  // Create a Scanner object
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			int option = choice.nextInt();
			switch (option) {
			case 1:
				HashSet<Integer> pL1 = new HashSet<Integer>();
				HashSet<Integer> pL2 = new HashSet<Integer>();
				HashSet<Integer> intersect_result = new HashSet<Integer>();
				pL1.add(1);
				pL1.add(2);
				pL1.add(3);
				pL2.add(1);
				pL2.add(3);
				intersect_result = index.intersect(pL1, pL2);
				System.out.println(intersect_result);
				phrase = "intersect";
				break;
			case 2:
				System.out.println("Type a phrase: ");
				phrase = in.readLine();
				System.out.println(index.find(phrase.toLowerCase()));
				break;
			case 3:
				System.out.println("Type a phrase with 2-terms: ");
				phrase = in.readLine();
				System.out.println(index.find_01(phrase.toLowerCase()));
				break;
			case 4:
				System.out.println("Type a phrase with 3-terms: ");
				phrase = in.readLine();
				System.out.println(index.find_02(phrase.toLowerCase()));
				break;
			case 5:
				System.out.println("Type a phrase with N-terms: ");
				phrase = in.readLine();
				System.out.println(index.find_03(phrase.toLowerCase()));
				break;
			case 6:
				System.out.println("Type a phrase with N-terms for optimized: ");
				phrase = in.readLine();
				System.out.println(index.find_04(phrase.toLowerCase()));
				break;
			case 7:
				/*
				System.out.println("Type a phrase for compare: ");
				phrase = in.readLine();
				*/
				index.compare("agile and can ehab should only");
				break;
			}
		} while (!phrase.isEmpty());
    }
	
}


