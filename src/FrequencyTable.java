/**
 * The <code>FrequencyTable</code>  contains a frequency table of frequency list
 *
 * @author Menarul Alam
 * e-mail: menarul.alam@stonybrook.edu
 * Stony Brook ID: 112838926
 * Recitation: R.07
 **/
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


public class FrequencyTable {

    private ArrayList<FrequencyList> lists;

    /**
     * builds a frequency table object with the passage list
     * <dt><b>Postconditions:</b></dt>
     * <dd>A new FrequencyTable object has been constructed, containing a Collection of FrequencyLists with information from all Passages</dd>
     * </dl>
     * @param passages passage list
     * @return frequency table object
     */
    public static FrequencyTable buildTable(ArrayList<Passage> passages)
    {
        FrequencyTable ft = new FrequencyTable();
        for(int i=0; i<passages.size(); i++) {
            Iterator<Map.Entry<String, Integer>> iterator =
                    passages.get(i).entrySet().iterator();
            while(iterator.hasNext())
            {
                Map.Entry<String, Integer> element=iterator.next();
                ft.lists.add(new FrequencyList(element.getKey()));
                ft.lists.get(ft.lists.size()-1).addPassage(passages.get(i));

            }
        }
        return ft;

}

    /**
     * adds passage to table
     * <dt><b>Preconditions:</b></dt>
     * <dd>The Passage p is neither null nor empty.</dd>
     * </dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>p’s values for each of its keys have been appropriately mapped into each FrequencyList in the table.</dd>
     * </dl>
     * @param p passage
     * @throws IllegalArgumentException if passage is null or empty
     */
        public void addPassage(Passage p) throws IllegalArgumentException
        {
            if(p.isEmpty())
                throw new IllegalArgumentException();
            Iterator<Map.Entry<String, Integer>> iterator =
                p.entrySet().iterator();
            while(iterator.hasNext())
            {
                Map.Entry<String, Integer> element=iterator.next();
                lists.add(new FrequencyList(element.getKey()));
                lists.get(lists.size()-1).addPassage(p);

            }

        }

    /**
     * gets frequency of word from passage
     * @param word word
     * @param p passage
     * @return frequency of word in passage
     * @throws IllegalArgumentException if passage is null or empty
     */
        public int getFrequency(String word, Passage p) throws IllegalArgumentException
        {
            if(p.isEmpty())
                throw new IllegalArgumentException();
            for(FrequencyList fl:lists)
            {
                if(fl.getWord().equals(word))
                {
                    return fl.getFrequency(p);
                }
            }
            throw new IllegalArgumentException();

        }
}
