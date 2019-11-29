/**
 * The <code>FrequencyList</code>  contains a frequency list
 *
 * @author Menarul Alam
 * e-mail: menarul.alam@stonybrook.edu
 * Stony Brook ID: 112838926
 * Recitation: R.07
 **/
import java.util.HashMap;
import java.util.ArrayList;
import java.util.*;

public class FrequencyList extends HashMap {
    private String word;
    private ArrayList<Integer> frequencies;
    private HashMap<String, Integer> passageIndices;

    /**
     * gets word
     * @return word
     */
    public String getWord() {
        return word;
    }

    /**
     * sets word
     * @param word word
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * gets freqs
     * @return freqs
     */
    public ArrayList<Integer> getFrequencies() {
        return frequencies;
    }

    /**
     * sets freqs
     * @param frequencies freqs
     */
    public void setFrequencies(ArrayList<Integer> frequencies) {
        this.frequencies = frequencies;
    }

    /**
     * gets passage indices
     * @return passage indices
     */
    public HashMap<String, Integer> getPassageIndices() {
        return passageIndices;
    }

    /**
     * sets passage indices
     * @param passageIndices set passageIndices
     */

    public void setPassageIndices(HashMap<String, Integer> passageIndices) {
        this.passageIndices = passageIndices;
    }

    /**
     * constructs frequency list
     * @param word word
     */
    public FrequencyList(String word)
    {
        this.word=word;
    }

    /**
     * constructs frequency list with word and passages
     * @param word word
     * @param passages ArrayList of passages
     */
    public FrequencyList(String word, ArrayList<Passage> passages)
    {
        this.word=word;
        for(int i=0; i<passages.size(); i++)
        {
            if(passages.get(i).get(word)!=0) {
                frequencies.set(i, passages.get(i).get(word));
                passageIndices.put(passages.get(i).getTitle(), i);
            }

        }

    }

    /**
     * adds passage
     * <dt><b>Postconditions:</b></dt>
     * <dd>passageIndices now contains p’s title which maps to the next available index in this ArrayList
     * The ArrayList now contains an additional index containing the frequency of “word” in the Passag</dd>
     * </dl>
     * @param p passage
     */
    public void addPassage(Passage p)
    {
        frequencies.add(p.get(word));
        passageIndices.put(p.getTitle(), frequencies.size()-1);

    }

    /**
     * gets frequency of a passage
     * @param p passage
     * @return frequency of a passage
     */
    public int getFrequency(Passage p)
    {
        if (passageIndices.containsKey(p.getTitle()))
        return frequencies.get(passageIndices.get(p.getTitle()));
        return 0;
    }
}
