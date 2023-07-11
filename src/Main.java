import java.io.File;
import java.util.*;


public class Main {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter folder path:");
        String path = sc.nextLine();
        File folder = new File(path);
        try {
            File[] files = folder.listFiles();

            Process process = new Process(files);

            process.analyse();

            System.out.print("Search: ");
            String search = sc.nextLine();
            System.out.println(process.search(search));
        } catch (NullPointerException e) {
            System.out.println("File not found!");
        }
    }


}

