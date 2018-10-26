public class RSVSearcher extends Searcher{

    HashMap<String, Double> IDFmap = new HashMap<>();
	HashMap<Document,Integer> docmap = new HashMap<>();
	HashMap<String,Integer> wordbag = new HashMap<>(); // map term and term id
    HashMap<Integer,double[]> vecmap = new HashMap<>();
    
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
				docid++;
			}
			tok = i.getTokens();
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
}