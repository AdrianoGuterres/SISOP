package versao.avancada.roteador;

import java.util.ArrayList;

public class TuplesManager {

	private ArrayList<Tuple> tuplasList;

	public TuplesManager(ArrayList<String> neigtborList) {
		this.tuplasList = new ArrayList<Tuple>();
	}

	public void addTuple(String destinyReveived, int metric,String ipSender) {		
		long timeStamp = System.currentTimeMillis();		

		Tuple forActualizeTimestampNeigthbor = searchTuplaForDestiny(ipSender);		

		if(forActualizeTimestampNeigthbor != null) {
			forActualizeTimestampNeigthbor.setTimeStamp(timeStamp);			
		}		

		Tuple aux = searchTuplaForDestiny(destinyReveived);		
		if(aux != null) {
			int metricTemp = getMetricForIpDestiny(destinyReveived);			
			if(metricTemp == 999) {				
			}else if(metricTemp < metric) {
				aux.setMetric(metricTemp);		
				aux.setIpOut(ipSender);
				aux.setTimeStamp(timeStamp);
			}
		}else {
			tuplasList.add(new Tuple(destinyReveived, metric, ipSender));
		}		
	}

	public Tuple searchTuplaForDestiny(String destiny) {		
		Tuple result = null;		
		for(int i = 0; i < tuplasList.size(); i++ ) {
			if(tuplasList.get(i).getIpDestiny().equals(destiny)) {
				result = tuplasList.get(i);
			}		
		}
		return result;
	}


	public boolean removeTuple(String destiny) {
		boolean result = false;	

		for(int i = 0; i < tuplasList.size(); i++) {
			if(tuplasList.get(i).getIpDestiny().equalsIgnoreCase(destiny)) {
				tuplasList.remove(i);
				result = true;
			}
		}		
		return result;		
	}

	public int cleanTuplesList() {
		ArrayList<Tuple> shadowList = new ArrayList<>(tuplasList);
		int itensRemoved = 0;
		for(int i = 1; i < shadowList.size(); i++) {
			if(tuplasList.get(i).isForRemove()) {
				tuplasList.remove(i);	
				itensRemoved++;
			}				
		}
		return itensRemoved;		
	}

	public int getMetricForIpDestiny(String ipDestiny) {
		int result = 9999;
		for(Tuple x: tuplasList) {
			if(x.getIpDestiny().equalsIgnoreCase(ipDestiny)) {
				result = x.getMetric();				
			}			
		}
		return result;
	}

	public ArrayList<Tuple> getTuplasList() {
		return tuplasList;
	}

	public void verifyTimestamp() {
		long actualTime = System.currentTimeMillis();		
		ArrayList<Tuple> shadow = new ArrayList<>(tuplasList); 		
		for(Tuple x: shadow) {
			if((x.getTimeStamp() + 30000) <= actualTime) {				
				removeTuple(x.getIpDestiny());				
			}			
		}
	}

}
