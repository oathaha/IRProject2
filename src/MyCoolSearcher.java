//Name: Komson Najard, Thanadon Bunkeard, Chanathip Pornprasit
//Section: 1 (all)
//ID: 5988020, 5988073, 5988179

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class MyCoolSearcher extends Searcher
{
	HashMap<String, Integer> DFmap = new HashMap<>();
	HashMap<String, Double> ct = new HashMap<>();
	
	public MyCoolSearcher(String docFilename)
	{
		super(docFilename);
		
		Set<String> tokset;
		int docsize = documents.size();
		
		// find document frequency (df)
		for(Document doc: documents)
		{
			tokset = new HashSet<>(doc.getTokens());
			for(String word: tokset)
			{
				if(!DFmap.containsKey(word))
				{
					DFmap.put(word, 1);
				}
				else
					DFmap.replace(word, DFmap.get(word)+1);
			}
		}
		
		// find ct of each word
		// ct = log[(docsize - df + 0.5)/(df+0.5)]
		for(String word: DFmap.keySet())
		{
			int df = DFmap.get(word);
			ct.put(word, Math.log10((docsize-df+0.5)/(df+0.5)));
		}
	}

	@Override
	public List<SearchResult> search(String queryString, int k)
	{
		// TODO Auto-generated method stub
		List<String> arr = super.tokenize(queryString);
		List<SearchResult> result  = new LinkedList<SearchResult>();
		
		SearchResult res;
		
		for(Document doc: documents)
		{
			double score = 0;
			Set<String> doctok = new HashSet<String>(doc.getTokens());
			Set<String> tokset = new HashSet<String>(arr); // set of terms in query
			tokset.retainAll(doctok);
			
			for(String tok: tokset)
			{
				score += ct.get(tok);
			}
			/*if(tokset.size() == 0)
				score = 0;
			else
			{
				for(String tok: doctok)
				{
					score *= ct.get(tok);
				}
			}*/
			
			res = new SearchResult(doc, score);
			result.add(res);
		}
		
		Collections.sort(result);		
		return result.subList(0, k);
	}

}
