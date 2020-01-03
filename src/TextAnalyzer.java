/**
 * The <code>TextAnalyzer</code> TextAnalyzer class runs the passage class
 **/
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
public class TextAnalyzer {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the directory path: ");
        String directory = sc.nextLine();
        Passage.updateStopWords(directory);
        File[] directoryOfFiles = new File(directory).listFiles();
        ArrayList<Passage> passages = new ArrayList<>();
        while (true) {
            try {
                for (File f : directoryOfFiles) {
                    String name=f.getName();
                    if(name.length()>=4&&name.substring(name.length()-4,
                            name.length()).equals("java"))
                        continue;
                    if(!f.getName().equals("StopWords.txt")) {
                        Passage p = new Passage();
                        p.setTitle(f.getName());
                        p.parseFile(f);
                        passages.add(p);
                    }
                }
                break;
            } catch (NullPointerException e) {
                System.out.println("Wrong directory printed try again.");
                return;
            }
        }
        for (int i = 0; i < passages.size(); i++) {
            for (int j = 0; j < passages.size(); j++) {
                if (i != j)
                    passages.get(i).addPassageComparison(passages.get(j));
            }
        }
        System.out.println("Title:                   Passages:");
        for (Passage passage : passages) {
            System.out.println(passage.toString());
        }
        System.out.println("Similar Titles: ");
        for (Passage passage : passages) {
            System.out.println(passage.sameAuthors());
        }

    }
}
