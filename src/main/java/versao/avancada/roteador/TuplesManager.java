package versao.avancada.roteador;

import java.util.ArrayList;

public class TuplesManager {

	private ArrayList<Tuple> tuplasList;

	public TuplesManager(ArrayList<String> neigtborList) {
		this.tuplasList = new ArrayList<Tuple>();
	}

	public void addTuple(String destinyReveived, int metric,String ipSender) {	

		updateTimestampNeibor(ipSender);	

		if(updateTupla(destinyReveived, metric, ipSender) == false) {	
			tuplasList.add(new Tuple(destinyReveived, metric, ipSender));			
		}	
	}		
	
	public ArrayList<Tuple> getTuplasList() {
		return tuplasList;
	}	

	public void verifyTimestamp() {
		long actualTime = System.currentTimeMillis();			
		for(int i = 0; i< tuplasList.size(); i++) {
			if((tuplasList.get(i).getTimeStamp()+30000) < actualTime) {
				tuplasList.get(i).isForRemove();								
			}			
		}
	}	

	private void updateTimestampNeibor(String ipSender) {
		long timeStamp = System.currentTimeMillis();

		for(int i = 0; i< tuplasList.size(); i++) {
			if(tuplasList.get(i).getIpDestiny().equalsIgnoreCase(ipSender)) {
				tuplasList.get(i).setTimeStamp(timeStamp);								
			}			
		}
	}	

	private boolean updateTupla(String destiny, int metric, String ipSender) {
		long newTimestamp = System.currentTimeMillis();
		boolean aux = false;

		for(int i = 0; i< tuplasList.size(); i++) {
			if(tuplasList.get(i).getIpDestiny().equalsIgnoreCase(destiny)) {
				if(tuplasList.get(i).getMetric()> metric) {
					tuplasList.get(i).setIpOut(ipSender);
					tuplasList.get(i).setTimeStamp(newTimestamp);
					tuplasList.get(i).setMetric(metric);
					aux = true;
				}				
			}
		}
		return aux;
	}

}
