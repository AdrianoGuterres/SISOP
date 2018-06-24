package versao.avancada.roteador;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class TuplesManager {

	private ArrayList<Tuple> tuplasList;
	
	private Semaphore sem;


	public TuplesManager() {
		this.tuplasList = new ArrayList<>();
		this.sem = new Semaphore(1);
	} 


	public void addTuple(String ipDestiny, int metric, String ipOut) {
		
		try {
			sem.acquire();
			this.tuplasList.add(new Tuple(ipDestiny, metric, ipOut)); 
		} catch (InterruptedException e) {}
		
		sem.release();
		
		this.tuplasList.add(new Tuple(ipDestiny, metric, ipOut)); 
	}

	public Tuple searchByDestiny(String ipDestiny) {
		Tuple tuple = null;
		
		try {
			sem.acquire();
			for(int i =0;i<tuplasList.size(); i++) {
				if(tuplasList.get(i).getIpDestiny().equalsIgnoreCase(ipDestiny)) {
					tuple = tuplasList.get(i);				
				}			
			}		
			
		} catch (InterruptedException e) {}

				sem.release();
		return tuple;		
	}


	public boolean removeTuple(String ipDestiny) {

		boolean aux = false;
		
		try {
			sem.acquire();
			for(int i =0;i<tuplasList.size(); i++) {
				if(tuplasList.get(i).getIpDestiny().equalsIgnoreCase(ipDestiny)) {
					tuplasList.remove(i);	
					aux = true;
				}			
			}
			
		} catch (InterruptedException e) {}
		
		sem.release();		
		
		return aux;
	}

	public Tuple updateByDestiny(String ipDestiny, int metric, String ipOut) {
		Tuple tuple = null;
		long newTimestamp = System.currentTimeMillis() + 30000;
		
		try {
			sem.acquire();
			for(int i =0;i<tuplasList.size(); i++) {
				if(tuplasList.get(i).getIpDestiny().equalsIgnoreCase(ipDestiny)) {
					tuplasList.get(i).setMetric(metric);				
					tuplasList.get(i).setIpOut(ipOut);
					tuplasList.get(i).setTimeStamp(newTimestamp);

					tuple = tuplasList.get(i);				
				}			
			}		
		} catch (InterruptedException e) {}		
		return tuple;		
	}


	public boolean removeNeigtborbyTimestamp() {

		boolean aux = false;
		try {
			sem.acquire();
			for(int i =0;i<tuplasList.size(); i++) {		
				if((tuplasList.get(i).getTimeStamp()) < System.currentTimeMillis() ) {
					tuplasList.remove(i);
					aux = true;			
				}
			}
		} catch (InterruptedException e) {}		
		return aux;
	}


	public ArrayList<Tuple> getTuplesList(){
		ArrayList<Tuple> aux = null;
		
		try {
			sem.acquire();
			aux = this.tuplasList;
			sem.release();
		} catch (InterruptedException e) {}
		
		return aux;		
	}


}
