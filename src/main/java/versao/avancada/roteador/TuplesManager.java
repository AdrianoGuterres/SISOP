package versao.avancada.roteador;

import java.util.ArrayList;

public class TuplesManager {

	private ArrayList<Tuple> tuplasList;


	public TuplesManager() {
		this.tuplasList = new ArrayList<>();
	} 


	public void addTuple(String ipDestiny, int metric, String ipOut) {
		this.tuplasList.add(new Tuple(ipDestiny, metric, ipOut)); 
	}

	public Tuple searchByDestiny(String ipDestiny) {
		Tuple tuple = null;

		for(int i =0;i<tuplasList.size(); i++) {
			if(tuplasList.get(i).getIpDestiny().equalsIgnoreCase(ipDestiny)) {
				tuple = tuplasList.get(i);				
			}			
		}		
		return tuple;		
	}


	public boolean removeTuple(String ipDestiny) {
		boolean aux = false;
		for(int i =0;i<tuplasList.size(); i++) {
			if(tuplasList.get(i).getIpDestiny().equalsIgnoreCase(ipDestiny)) {
				tuplasList.remove(i);	
				aux = true;
			}			
		}		
		return aux;
	}

	public Tuple updateByDestiny(String ipDestiny, int metric, String ipOut) {
		Tuple tuple = null;
		long newTimestamp = System.currentTimeMillis() + 30000;

		for(int i =0;i<tuplasList.size(); i++) {
			if(tuplasList.get(i).getIpDestiny().equalsIgnoreCase(ipDestiny)) {
				tuplasList.get(i).setMetric(metric);				
				tuplasList.get(i).setIpOut(ipOut);
				tuplasList.get(i).setTimeStamp(newTimestamp);

				tuple = tuplasList.get(i);				
			}			
		}		
		return tuple;		
	}


	public boolean removeNeigtborbyTimestamp() {
		boolean aux = false;
		for(int i =0;i<tuplasList.size(); i++) {		
			if((tuplasList.get(i).getTimeStamp()) < System.currentTimeMillis() ) {
				tuplasList.remove(i);
				aux = true;			
			}
		}
		return aux;
	}


	public ArrayList<Tuple> getTuplesList(){
		return this.tuplasList;
	}


}
