//Name: 
//Section: 
//ID: 

import java.util.List;

public class JaccardSearcher extends Searcher{

	public JaccardSearcher(String docFilename) {
		super(docFilename);
		/************* YOUR CODE HERE ******************/
		
		/***********************************************/
	}

	@Override
	public List<SearchResult> search(String queryString, int k) {
		/************* YOUR CODE HERE ******************/
		/*
		 *  create term set of queryString T(q)
		 *  create list of SearchReslt object (resultlist) --> List<SearchResult> name  new ...
		 *  for each document
		 *  	create term set of document T(d)
		 *  	find size of T(q) intersect T(d) and size of T(q) union T(d)
		 *  	find score from formula 
		 *  	store score and document in SearchResult object then put in resultlist 
		 */
			      
		return null;
		/***********************************************/
	}

}
