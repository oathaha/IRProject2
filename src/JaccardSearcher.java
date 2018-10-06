//Name: Komson Najard
//Section: 1
//ID: 5988020

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class JaccardSearcher extends Searcher{

	public JaccardSearcher(String docFilename) {
		super(docFilename);
		/************* YOUR CODE HERE ******************/
		
		/***********************************************/
	}

	@Override
	public List<SearchResult> search(String queryString, int k) {
		/************* YOUR CODE HERE ******************/
		List<String> query = Searcher.tokenize(queryString);
		List<String> term;
		
		List<SearchResult> result = new LinkedList<>();
		
		double score = 0;
		int unionsize,intersectsize;
		
		for(Document doc: documents)
		{
			Set<String> union = new HashSet<String>(query);
			Set<String> intersect = new HashSet<String>(query);
			
			term = doc.getTokens();
			
			union.addAll(term);
			intersect.retainAll(term);
			
			unionsize = union.size();
			intersectsize = intersect.size();
			
			if(unionsize == 0 || intersectsize == 0)
				score = 0;
			else
				score = intersectsize/(double)unionsize;
			result.add(new SearchResult(doc,score));
		}
		
		Collections.sort(result);
		return result.subList(0, k);
		/***********************************************/
	}

}
