package versao.avancada.roteador;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class TuplesManager {

	private Semaphore sem;

	String localHost = "";

	private ArrayList<Tuple> tuplasList;

	public TuplesManager(ArrayList<String> neigtborList) {

		try {localHost = InetAddress.getLocalHost().getHostAddress();} catch (UnknownHostException e) {}

		this.sem = new Semaphore(1);
		this.tuplasList = new ArrayList<Tuple>();
	}

	public void addTuple(String destinyReveived, int metric, String ipSender) {		


		if(destinyReveived.equalsIgnoreCase(localHost)==false) {
			if(updateTupla(destinyReveived, metric, ipSender) == false) {	
				tuplasList.add(new Tuple(destinyReveived, metric, ipSender));			
			}

		}
		updateTimestampNeibor(ipSender);	
	}		

	public ArrayList<Tuple> getTuplasList() {
		return tuplasList;
	}	


	public void verifyTimestamp() {
		try {
			sem.acquire();
			long actualTime = System.currentTimeMillis();			
			for(int i = 0; i< tuplasList.size(); i++) {
				if((tuplasList.get(i).getTimeStamp()+30000) < actualTime) {
					tuplasList.get(i).isForRemove();								
				}			
			}
		} catch (InterruptedException e) {}
		sem.release();
	}	

	private void updateTimestampNeibor(String ipSender) {
		try {
			sem.acquire();
			long timeStamp = System.currentTimeMillis();
			for(int i = 0; i< tuplasList.size(); i++) {
				if(tuplasList.get(i).getIpDestiny().equalsIgnoreCase(ipSender)) {
					tuplasList.get(i).setTimeStamp(timeStamp);
				}
			}
			sem.release();
		} catch (InterruptedException e) {}		
	}	

	private boolean updateTupla(String newDestiny, int metric, String ipSender) {
		boolean aux = false;
		try {
			long newTimestamp = System.currentTimeMillis();
			for(int i = 0; i< tuplasList.size(); i++) {
				if(tuplasList.get(i).getIpDestiny().equalsIgnoreCase(newDestiny)) {
					if(tuplasList.get(i).getMetric()> metric) {
						sem.acquire();
						tuplasList.get(i).setIpOut(ipSender);
						tuplasList.get(i).setTimeStamp(newTimestamp);
						tuplasList.get(i).setMetric(metric);							
					}			
					aux = true;	
					tuplasList.get(i).setTimeStamp(newTimestamp);
				}
			}
			sem.release();
		} catch (InterruptedException e) {}
		return aux;
	}

}
