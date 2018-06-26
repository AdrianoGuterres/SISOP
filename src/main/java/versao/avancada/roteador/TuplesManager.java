package versao.avancada.roteador;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class TuplesManager {

	private ArrayList<Tuple> tuplesList;

	private Semaphore sem;


	public TuplesManager() {
		this.tuplesList = new ArrayList<>();
		sem = new Semaphore(1);
	} 


	public synchronized void addTuple(String ipDestiny, int metric, String ipOut) {
		this.tuplesList.add(new Tuple(ipDestiny, metric, ipOut)); 
	}

	public synchronized Tuple searchByDestiny(String ipDestiny) {
		Tuple tuple = null;

		for(int i =0;i<tuplesList.size(); i++) {
			if(tuplesList.get(i).getIpDestiny().equalsIgnoreCase(ipDestiny)) {
				tuple = tuplesList.get(i);				
			}			
		}		
		return tuple;		
	}


	public synchronized  boolean removeTuple(String ipDestiny) {
		boolean aux = false;


			for(int i =0;i<tuplesList.size(); i++) {
				if(tuplesList.get(i).getIpDestiny().equalsIgnoreCase(ipDestiny)) {
					tuplesList.remove(i);	
					aux = true;
				}			
			}		

		return aux;
	}

	public synchronized Tuple updateByDestiny(String ipDestiny, int metric, String ipOut) {
		Tuple tuple = null;
		long newTimestamp = System.currentTimeMillis() + 30000;
		
			for(int i =0;i<tuplesList.size(); i++) {
				if(tuplesList.get(i).getIpDestiny().equalsIgnoreCase(ipDestiny)) {
					tuplesList.get(i).setMetric(metric);				
					tuplesList.get(i).setIpOut(ipOut);
					tuplesList.get(i).setTimeStamp(newTimestamp);

					tuple = tuplesList.get(i);
				}			
			}

		return tuple;		
	}


	public synchronized boolean removeNeigtborbyTimestamp() {
		
		boolean aux = false;
		for(int i =0;i<tuplesList.size(); i++) {	
			if((tuplesList.get(i).getTimeStamp()) < System.currentTimeMillis() ) {
				tuplesList.remove(i);
				aux = true;		
			}
		}
		return aux;
	}


	public synchronized ArrayList<Tuple> getTuplesList(){
		return this.tuplesList;
	}


	public synchronized String getTableForSend() {
		String tableForSendTemp = "";

		for(Tuple t : tuplesList){

			String aux = new String("*"+t.getIpDestiny()+";"+t.getMetric());

			System.out.println("add: "+aux);

			tableForSendTemp = tableForSendTemp+ aux;					
		}		

		return tableForSendTemp;
	}

	int count = 0;
	public synchronized void tableForView() {
		DateFormat formato = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String formattedDate = formato.format(date);

		String tuplesForSendS = getTableForSend();

		if((count == 0) || (tuplesList.size() ==0)){
			System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("Message sending for Routers Neighbors: ");	
			System.out.println( "!");	
			System.out.println("\n            Routing Table ");
			System.out.println("------------------------------------------------------------------------------");
			System.out.printf("%10s %20s %30s", "Destino", "Metrica", "Saida");
			System.out.println();
			System.out.println("------------------------------------------------------------------------------");
			for(Tuple tupla: tuplesList){
				System.out.format("%10s %20s %30s",tupla.getIpDestiny(), tupla.getMetric(), tupla.getIpOut());
				System.out.println();
			}
			System.out.println("------------------------------------------------------------------------------");
			System.out.println(formattedDate); 
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");


		}else {		
			System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("Message sending for Routers Neighbors: ");	
			System.out.println(tuplesForSendS);	
			System.out.println("\n            Routing Table ");
			System.out.println("------------------------------------------------------------------------------");
			System.out.printf("%10s %20s %30s", "Destino", "Metrica", "Saida");
			System.out.println();
			System.out.println("------------------------------------------------------------------------------");
			for(Tuple tupla:  tuplesList){
				System.out.format("%10s %20s %30s",tupla.getIpDestiny(), tupla.getMetric(), tupla.getIpOut());
				System.out.println();
			}
			System.out.println("------------------------------------------------------------------------------");
			System.out.println(formattedDate); 
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");
		}	

		count++;
	}

}
