//Name: komson najard
//Section: 1
//ID: 5988020

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JaccardSearcher extends Searcher{

	HashMap<Document,Set<String>> wordset = new HashMap<Document,Set<String>>(); //T(d)
	public JaccardSearcher(String docFilename) {
		super(docFilename); 
		/************* YOUR CODE HERE ******************/
		//for each doucument
		for (Document d : documents) {
			List<String> aword = d.getTokens();
			Set<String> term = new HashSet<String>(aword);
			wordset.put(d, term); //we got set of token for doc
		}
		// termlist <- term of document
		// termsrt <- remove duplicate term in termlist
		// put <document, termset> in the map 
		/***********************************************/
	}

	@Override
	public List<SearchResult> search(String queryString, int k) {
		/************* YOUR CODE HERE ******************/
		/*
		 *  create term set of queryString T(q)  --> List<String> tokenstr = Search.tokenize(queryString);
		 *  create list of SearchReslt object (resultlist) --> List<SearchResult> name  new ...
		 *  for each document
		 *  	find size of T(q) intersect T(d) and size of T(q) union T(d)
		 *  	find score from formula 
		 *  	store score and document in SearchResult object then put in resultlist 
		 */
		List<String> tokenstr = Searcher.tokenize(queryString);
		Set<String> queryset = new HashSet<String>(tokenstr);
		System.out.println(queryset);
			for(Document p : wordset.keySet()) 
			{
				Set<String> intersect = new HashSet<String>(queryset);
				Set<String> union = new HashSet<String>(queryset);
				intersect.retainAll(wordset.get(p));
				System.out.println("intersect: " + intersect);
				
				union.addAll(wordset.get(p));
				System.out.println("union: " + union+"\n");
				
				//K
			}
		return null;
		/***********************************************/
	}
		
}
