//Name: 
//Section: 
//ID: 

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

public class SearcherEvaluator {
	private List<Document> queries = null;				//List of test queries. Each query can be treated as a Document object.
	private  Map<Integer, Set<Integer>> answers = null;	//Mapping between query ID and a set of relevant document IDs
	
	public List<Document> getQueries() {
		return queries;
	}

	public Map<Integer, Set<Integer>> getAnswers() {
		return answers;
	}

	/**
	 * Load queries into "queries"
	 * Load corresponding documents into "answers"
	 * Other initialization, depending on your design.
	 * @param corpus
	 */
	public SearcherEvaluator(String corpus)
	{
		String queryFilename = corpus+"/queries.txt";
		String answerFilename = corpus+"/relevance.txt";
		
		//load queries. Treat each query as a document. 
		this.queries = Searcher.parseDocumentFromFile(queryFilename);
		this.answers = new HashMap<Integer, Set<Integer>>();
		//load answers
		try {
			List<String> lines = FileUtils.readLines(new File(answerFilename), "UTF-8");
			for(String line: lines)
			{
				line = line.trim();
				if(line.isEmpty()) continue;
				String[] parts = line.split("\\t");
				Integer qid = Integer.parseInt(parts[0]);
				String[] docIDs = parts[1].trim().split("\\s+");
				Set<Integer> relDocIDs = new HashSet<Integer>();
				for(String docID: docIDs)
				{
					relDocIDs.add(Integer.parseInt(docID));
				}
				this.answers.put(qid, relDocIDs);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Returns an array of 3 numbers: precision, recall, F1, computed from the top *k* search results 
	 * returned from *searcher* for *query*
	 * @param query
	 * @param searcher
	 * @param k
	 * @return
	 */
	public double[] getQueryPRF(Document query, Searcher searcher, int k)
	{
		/*********************** YOUR CODE HERE *************************/
		double prf[] = new double[3];
		List<SearchResult> result = searcher.search(query.getRawText(), k);
		Set<Integer> ground_truth = answers.get(query.getId());
		Set<Integer> retrieve = new HashSet<Integer>();
		
		for(SearchResult sr: result)
			retrieve.add(sr.getDocument().getId());
		
		Set<Integer> intersect = new HashSet<Integer>(retrieve);
		intersect.retainAll(ground_truth);
		
		prf[0] = intersect.size()/(double)retrieve.size();
		prf[1] = intersect.size()/(double)ground_truth.size();
		prf[2] = (2*prf[0]*prf[1])/(prf[0]+prf[1]);
		
		return prf;
		/****************************************************************/
	}
	
	/**
	 * Test all the queries in *queries*, from the top *k* search results returned by *searcher*
	 * and take the average of the precision, recall, and F1. 
	 * @param searcher
	 * @param k
	 * @return
	 */
	public double[] getAveragePRF(Searcher searcher, int k)
	{
		/*********************** YOUR CODE HERE *************************/
		double avgprf[] = new double[3];
		double tmp[];
		int size = queries.size();
		//double pr,rec,f1;
		
		for(Document doc: queries)
		{
			tmp = getQueryPRF(doc, searcher, k);
			avgprf[0] += tmp[0];
			avgprf[1] += tmp[1];
			avgprf[2] += tmp[2];
		}
		
		avgprf[0]/=(double)size;
		avgprf[1]/=(double)size;
		avgprf[2]/=(double)size;
		
		return avgprf;
		/****************************************************************/
	}
}
