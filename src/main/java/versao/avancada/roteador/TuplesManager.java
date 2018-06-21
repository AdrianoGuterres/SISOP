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






	public boolean updateTuple(String ipDestiny, int newMetric, String newIpOut) {
		boolean aux = false;

		long timestamp = new Long (System.currentTimeMillis());	

		for(int i = 0; i < this.tuplasList.size(); i++) {
			if(this.tuplasList.get(i).getIpDestiny().equalsIgnoreCase(ipDestiny)) {
				if(this.tuplasList.get(i).getMetric()>(newMetric + 1)) {
					this.tuplasList.get(i).setMetric(newMetric+1);		
					this.tuplasList.get(i).setIpOut(newIpOut);
					this.tuplasList.get(i).setTimeStamp(timestamp+30000);	
					
					
				}else {

					System.out.println(this.tuplasList.get(i).getTimeStamp());
					this.tuplasList.get(i).setTimeStamp(timestamp+30000);	
					System.out.println(this.tuplasList.get(i).getTimeStamp());
					System.out.println(this.tuplasList.get(i).getTimeStamp() - System.currentTimeMillis());
				}				
				aux = true;				
			}			
		}		
		return aux;		
	}




	public boolean removeTuple(String ipDestiny) {
		boolean aux = false;

		for(int i = 0; i < this.tuplasList.size(); i++) {
			if(this.tuplasList.get(i).getIpDestiny().equalsIgnoreCase(ipDestiny)) {
				this.tuplasList.remove(i);
				aux = true;
			}			
		} 	
		return aux;
	}





	public int removeByTimestamp() {
		long timestamp = new Long (System.currentTimeMillis());	

		int aux = 0;

		for(int i = 0; i < this.tuplasList.size(); i++) {
			if(this.tuplasList.get(i).getTimeStamp()  <  timestamp) {
				this.tuplasList.remove(i);
				aux++;
			}			
		} 	
		return aux;
	}





	public Tuple searchByDestiny(String ipDestiny) {
		Tuple tuple = null;
		for(int i = 0; i < this.tuplasList.size(); i++) {
			if(this.tuplasList.get(i).getIpDestiny().equalsIgnoreCase(ipDestiny)) {
				tuple = this.tuplasList.get(i);					
			}			
		} 	
		return tuple;		
	}






	public ArrayList<Tuple> getTuplesList(){
		return this.tuplasList;
	}

	public void addNeigtbor(String neihtbor) {
		tuplasList.add(new Tuple(neihtbor, 1, neihtbor));		
	}
}
