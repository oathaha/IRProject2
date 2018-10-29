import java.util.HashMap;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Collections;

public class RSVSearcher extends Searcher{

    HashMap<String, Double> IDFmap = new HashMap<>();
    HashMap<Document,Integer> docmap = new HashMap<>();
    HashMap<String,Integer> wordbag = new HashMap<>(); // map term and term id
    HashMap<Integer,double[]> vecmap = new HashMap<>();
    Comparator<SearchResult> docIDComparator = new Comparator<SearchResult>() {
		@Override
		public int compare(SearchResult r1,
				SearchResult r2)
		{
			// TODO Auto-generated method stub
			return r1.getDocument().getId()-r2.getDocument().getId();
		}
     };
    public RSVSearcher(String docFilename){
        super(docFilename);
        double tfidf,tf;
		int tid = 0;
		int docid = 0;
		double count;
		double size = super.documents.size();
        // create term dict and doc dict
		for(Document i: super.documents)
		{
			if(!docmap.containsKey(i))
			{
                docmap.put(i, docid);
                System.out.println(i.toString());
				docid++;
			}
			List<String> tok = i.getTokens();
			for(String str: tok)
			{
				if(!wordbag.containsKey(str))
				{
					wordbag.put(str, tid);
					tid++;
				}
			}
        }

	}

	@Override
	public List<SearchResult> search(String queryString, int k) {
		/************* YOUR CODE HERE ******************/
		List<String> arr = super.tokenize(queryString);
		List<SearchResult> result  = new LinkedList<SearchResult>();
		List<SearchResult> nanresult = new LinkedList<SearchResult>();
		PriorityQueue<SearchResult> resultqueue = new PriorityQueue<SearchResult>(docIDComparator);
		SearchResult cos;
		double tf = 0;
		double score = 0.0;
		double normd, normq , qd;
		double freq;
		double q[],d[];
		int docid,i;

		// initialize query vector
		for(Document doc: docmap.keySet())
		{
			normd = 0;
			normq = 0;
			qd = 0;
			docid = docmap.get(doc);
			d = vecmap.get(docid);
			q = new double[wordbag.size()];

			for(String str: arr)
			{
				if(wordbag.get(str) != null) // word in query is in wordbag
				{
					freq = Collections.frequency(arr, str);
					tf = 1 + Math.log10(freq);
					q[wordbag.get(str)] = tf*IDFmap.get(str);
				}
			}
			for(i=0; i<d.length; i++)
			{
				qd += d[i]*q[i];
				normd += Math.pow(d[i], 2);
				normq += Math.pow(q[i], 2);
			}
			normd = Math.sqrt(normd);
			normq = Math.sqrt(normq);
			score = qd/(normd*normq);
			cos = new SearchResult(doc, score);
			if(Double.isNaN(score))
				resultqueue.add(cos);
			else
				result.add(cos);
		}

		if(result.size() > 0) // at least k result in the list
		{
			Collections.sort(result);
			return result.subList(0, k);
		}
		else // nan result
		{
			for(i=0; i<k; i++)
				nanresult.add(resultqueue.poll());
			return nanresult.subList(0, k);
		}
		/***********************************************/
	}
}
