/**
 * The <code>Pasasge</code>  contains a hashmap vector of all the words in a
 * passage
 *
 * @author Menarul Alam
 * e-mail: menarul.alam@stonybrook.edu
 * Stony Brook ID: 112838926
 * Recitation: R.07
 **/
import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.util.*;

public class Passage extends HashMap<String, Integer> {
    /**
     * updates stopwords from text file
     * @param directory directory path
     */
    public static void updateStopWords(String directory) {
        File[] directoryOfFiles = new File(directory).listFiles();
        try {
            for (File f : directoryOfFiles) {
                if (f.getName().equals("StopWords.txt")) {
                    BufferedReader br = new BufferedReader(new FileReader(f));
                    String s;
                    STOP_WORDS = new HashSet<String>();
                    while((s=br.readLine())!=null)
                    {
                        STOP_WORDS.add(s);
                    }

                }
            }
        }
        catch (FileNotFoundException ignored)
        {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * removes spaces from array
     * @param input input array
     * @return array with no spaces
     */
        private static String[] arrayTrim (String[]input){
            ArrayList<String> out = new ArrayList<>();
            for (String s : input) {
                if (s != null && !s.isEmpty() && !s.isBlank()) {
                    out.add(s);
                }
            }
            String[] output = new String[out.size()];
            for (int i = 0; i < output.length; i++) {
                output[i] = out.get(i);
            }
            return output;
        }
        public static String[] values = new String[]{"a", "about", "above",
                "after", "again", "against", "all", "am", "an", "and", "any", "are", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "could", "did", "do", "does", "doing", "down", "during", "each", "few", "for", "from", "further", "had", "has", "have", "having", "he", "hed", "hell", "hes", "her", "here", "heres", "hers", "herself", "him", "himself", "his", "how", "hows", "i", "id", "ill", "im", "ive", "if", "in", "into", "is", "it", "its", "itself", "lets", "me", "more", "most", "my", "myself", "nor", "of", "on", "once", "only", "or", "other", "ought", "our", "ours", "ourselves", "out", "over", "own", "same", "she", "shed", "shell", "shes", "should", "so", "some", "such", "than", "that", "thats", "the", "their", "theirs", "them", "themselves", "then", "there", "theres", "these", "they", "theyd", "theyll", "theyre", "theyve", "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "we", "wed", "well", "were", "weve", "what", "whats", "when", "whens", "where", "wheres", "which", "while", "who", "whos", "whom", "why", "whys", "with", "would", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves"};
        public static HashSet<String> STOP_WORDS =
                new HashSet<String>(Arrays.asList(values));
        private Hashtable<String, Double> similarTitles;
        private String title;
        private int wordCount;
        private Hashtable<String, Double> sameAuthor;
    public Passage()
        {
            title = "";
            similarTitles = new Hashtable<String, Double>();
            sameAuthor = new Hashtable<String, Double>();
        }

    /**
     * gets title
     * @return title
     */
        public String getTitle () {
            return title;
        }

    /**
     * sets title
     * @param title title
     */
        public void setTitle (String title){
            this.title = title;
        }

    /**
     * gets wordcount
     * @return wordcount
     */
        public int getWordCount () {
            return wordCount;
        }

    /**
     * adds passage to comparison list
     * @param p passage to be added
     */
    public void addPassageComparison (Passage p)
        {
            String title = p.getTitle();
            double cosineSimilarity = cosineSimilarity(p, this);
            similarTitles.put(title, cosineSimilarity);
            if (cosineSimilarity >= .6) {
                sameAuthor.put(title, cosineSimilarity);
            }
        }

    /**
     * sets word count
     * @param wordCount sets word count
     */
        public void setWordCount ( int wordCount){
            this.wordCount = wordCount;
        }

    /**
     * returns similarTitles
     * @return similarTitles
     */
        public Hashtable<String, Double> getSimilarTitles () {
            return similarTitles;
        }

    /**
     * sets similarTitles
     * @param similarTitles similartitles
     */
        public void setSimilarTitles (Hashtable < String, Double > similarTitles)
        {
            this.similarTitles = similarTitles;
        }

    /**
     * constructs a passage object
     * @param title title
     * @param file file object
     */
    public Passage(String title, File file)
        {
            this.title = title;
            parseFile(file);
        }

    /**
     * reads file and puts it into the passage
     * @param file file to be read
     */
        public void parseFile (File file)
        {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                Scanner sc = new Scanner(file);
                String s;
                while ((s = br.readLine())!=null) {
                    s = s.trim().replaceAll("[^a-zA-Z\\s]", "").toLowerCase();
                    //non alphabetic characters, spaces at beginning or end and puts
                    // to lowercase
                    String[] stringArray = arrayTrim(s.split("\\ ", 0));
                    for (String word : stringArray) { //adds if stop_words does not contain
                        if (!STOP_WORDS.contains(word)) {
                            wordCount++;
                            //add if not contained or iterate
                            if (super.containsKey(word)) {
                                super.put(word, super.get(word) + 1);
                            } else {
                                super.put(word, 1);
                            }
                        } else {
                        }
                    }

                }
            } catch (Exception ignored) {

            }

        }

    /**
     * computes cosine of angle between passage vectors
     * @param passage1 passasge1
     * @param passage2 passage2
     * @return cos(a)
     */
        public static double cosineSimilarity (Passage passage1, Passage
        passage2)
        {
            Iterator<Map.Entry<String, Integer>> iterator1 =
                    passage1.entrySet().iterator();
            Iterator<Map.Entry<String, Integer>> iterator2 =
                    passage2.entrySet().iterator();
            double dotProduct = 0;
            double length1 = 0;
            double length2 = 0;
            while (iterator1.hasNext()) {
                //iterate through and add to dot product if it exists in both,
                // divide by 10 to avoid potential overflow (10 cancels out in
                // the final answer) also compute the scalar length return the
                // cosine of the angle
                Map.Entry<String, Integer> element =
                        (Map.Entry<String, Integer>) iterator1.next();
                String word1 = element.getKey();
                int key1 = element.getValue();
                dotProduct += passage1.getWordFrequency(word1) * passage2.getWordFrequency(word1);
                length1 += Math.pow(passage1.getWordFrequency(word1), 2);
            }
            while (iterator2.hasNext()) {
                Map.Entry<String, Integer> element =
                        (Map.Entry<String, Integer>) iterator2.next();
                length2 += Math.pow(passage2.getWordFrequency(element.getKey()), 2);
            }
            return dotProduct / (Math.sqrt(length1 * length2));
        }

    /**
     * Gets word frequency/wordcount
     * @param word word
     * @return fraction of times a word appears
     */
    public double getWordFrequency (String word)
        {
            if (containsKey(word))
                return get(word) * 1.0 / (double) wordCount;
            return 0;
        }

    /**
     * gets set of words
     * @return set of words
     */
    public Set<String> getWords ()
        {
            return this.keySet();
        }

    /**
     * returns string representation of object
     * @return string representation of object
     */
    public String toString () {
            Iterator<Map.Entry<String, Double>> iterator =
                    similarTitles.entrySet().iterator();
            String firstLine = String.format("%-25s", getTitle());
            String spaces = "                         ";
            int i = 0;
            while (iterator.hasNext()) {
                Map.Entry<String, Double> element =
                        (Map.Entry<String, Double>) iterator.next();

                if (i == 0) {
                    firstLine = firstLine + "\n" + spaces + "| ";
                    firstLine =
                            firstLine + element.getKey() + " (" + Math.round(element.getValue() * 100) + "%)";

                } else {
                    firstLine =
                            firstLine + ", " + element.getKey() + " (" + Math.round(element.getValue() * 100) + "%)";

                }
                i = (i + 1) % 2;
            }
            return firstLine;
        }

    /**
     * returns a string representation of all the similar authors
     * @return string representation of the similar authors
     */
    public String sameAuthors ()
        {
            String ans = "Similar texts for: " + title + "\n";
            for (Map.Entry<String, Double> m : sameAuthor.entrySet()) {
                ans += " " + m.getKey() + " (" + Math.round(m.getValue() * 100) + "%),";
            }
            if (ans.charAt(ans.length() - 1) == ',')
                return ans.substring(0, ans.length() - 1) + "\n";
            return ans;
        }

    }
