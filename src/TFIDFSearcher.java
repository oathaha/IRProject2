//Name: Thanadon Bunkeard
//Section: 1
//ID: 5988073

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class TFIDFSearcher extends Searcher
{	
	HashMap<String, Double> TFmap = new HashMap<>(); // for 1  document
	HashMap<String, Double> IDFmap = new HashMap<>();
	HashMap<String, Double> TFIDFmap = new HashMap<>();
	HashMap<String, Double> testmap = new HashMap<>();
	

	HashMap<String, Double> QTFmap = new HashMap<>();
	HashMap<String, Double> QTFIDFmap = new HashMap<>();
	
	HashMap<Document,HashMap<String, Double> > doctfidf = new HashMap<Document,HashMap<String, Double> >();
	HashSet<String> wordbag = new HashSet<String>();
	
	public TFIDFSearcher(String docFilename) {
		super(docFilename);
		
		/************* YOUR CODE HERE ******************/
		double tfidf,tf;
		for(Document i: super.documents) {
			wordbag.addAll(i.getTokens());
		}
		//System.out.println(wordbag);
		
		// Find idf
		double count;
		double size = super.documents.size();
		for(String word: wordbag) {
			count = 0.0;
			for(Document i: super.documents) {
				if(i.getTokens().contains(word)) {
					++count;
				}
				
			}
			count = Math.log10( 1 + (size/count) );
			IDFmap.put(word, count);
			TFmap.put(word,0.0);
			TFIDFmap.put(word, 0.0);
			
		}
	
		
		/*for(int docid: doctfidf.keySet())
		{
			for(String term: doctfidf.get(docid).keySet())
			{
				System.out.println("doc id: " + docid + " term: " + term +" idf: " + doctfidf.get(term));
			}
		}*/
		/*for(Map.Entry<String, Double> entry: TFmap.entrySet()) {
			System.out.println("Key: "+entry.getKey()+", Freq: "+entry.getValue());//+", Size:"+super.documents.get(0).getTokens().size());
		}*/
		//System.out.println(TFmap.get("council"));
		
		
		
		/***********************************************/
	}
	
	// not work here T-T
	// error: heap space error
	public HashMap<String,Double> clonemap(Map<String,Double> map)
	{
		HashMap<String,Double> clone = new HashMap<String,Double>();
		clone.putAll(map);
		return clone;
	}
	@Override
	public List<SearchResult> search(String queryString, int k) {
		/************* YOUR CODE HERE ******************/
		List<String> arr = super.tokenize(queryString);
		List<SearchResult> result  = new LinkedList<SearchResult>();
		SearchResult cos;
		double count = 0.0;
		double tf = 0, tfidf;
		double score = 0.0;
		double normd = 0.0, normq = 0.0, qd = 0.0, d;
		double newFreq;
		/*
		 * wordbag = union all token in all document and arr
		 * for each document
		 * 		for each token
		 * 			find tf, idf
		 * 			tfidf = tf*idf
		 * 		find cos similarity
		 */
		
		// initialize query vector
		
		for(String word: wordbag)
		{
			QTFmap.put(word, 0.0);
			QTFIDFmap.put(word, 0.0);
		}
		
		// TF of each document
		for(Document i: super.documents) {
			newFreq= 0.0;
			//System.out.println(i.getTokens().toString());
			for(String j: i.getTokens()) { // change something...
				newFreq = Collections.frequency(i.getTokens(), j);
				//System.out.println("Word: "+",Freq: "+newFreq);
				//if(i.getId() == 1)
				//{
				//	System.out.println("word: " + j + ", tf: " + newFreq + " idf: " + IDFmap.get(j));
				//}
				//System.out.println("String: "+j+", Freq: "+newFreq);
				if(newFreq > 0.0) {
					//TFmap.put(j, 1+Math.log10(newFreq));
					tf = 1+Math.log10(newFreq);
					//TFmap.put(j, tf);
				}
				else {
					TFmap.put(j, 0.0);
				}
				tfidf = tf*IDFmap.get(j);
				TFIDFmap.put(j, tfidf);
				//System.out.println("Doc id: " + i.getId()+"term: " + j+", TF: " + TFmap.get(j) + " IDF: " + IDFmap.get(j));
			}
			
			for(String word: arr) {
				count = Collections.frequency(arr, i);
				if(count > 0.0)
				{
					// find tf*idf
					tf = 1 + Math.log10(count);
					QTFmap.put(word, tf);
				}
				tfidf = QTFmap.get(word)*IDFmap.get(word);
				QTFIDFmap.put(word, tfidf);
			}
			
			for(String t: QTFIDFmap.keySet())
			{
				normq += Math.pow(QTFIDFmap.get(t), 2);
			}
			// |q|
			normq = Math.sqrt(normq);
			
			normd = Math.sqrt(normd);
			score = qd/(normq*normd);
			cos = new SearchResult(i, score);
			result.add(cos);
			// |d|
			//System.out.println(doctfidf.size());
		}

		Collections.sort(result);		
		return result.subList(0, k);
		/***********************************************/
	}
}
