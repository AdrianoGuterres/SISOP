package versao.avancada.roteador;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class TuplesManager {

	private ArrayList<Tuple> tuplesList;
	
	private boolean wereChanged; 
	
	private Semaphore sem;
	
	private String tableForSend;
	

	public TuplesManager() {
		this.tableForSend = "";
		this.tuplesList = new ArrayList<>();
		this.sem = new Semaphore(1);
		this.wereChanged = false;
	} 
	
	
	public boolean isWereChanged() {
		return wereChanged;
	}


	public void setWereChanged(boolean wereChanged) {
		this.wereChanged = wereChanged;
	}


	public  void addTuple(String ipDestiny, int metric, String ipOut) {
		this.tuplesList.add(new Tuple(ipDestiny, metric, ipOut)); 
	}

	public  Tuple searchByDestiny(String ipDestiny) {
		Tuple tuple = null;

		for(int i =0;i<tuplesList.size(); i++) {
			if(tuplesList.get(i).getIpDestiny().equalsIgnoreCase(ipDestiny)) {
				tuple = tuplesList.get(i);				
			}			
		}		
		return tuple;		
	}

	public  Tuple updateByDestiny(String ipDestiny, int metric, String ipOut) {
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


	public  void removeNeigtborbyTimestamp() {
		try {
			sem.acquire();
			for(int i =0;i<tuplesList.size(); i++) {	
				if((tuplesList.get(i).getTimeStamp()) < System.currentTimeMillis() ) {
					tuplesList.get(i).setVisible(false);
				}
			}

		} catch (InterruptedException e) {}
		sem.release();		
	}

	//----------------------------------------------------------------------------------------------------------------------------------------------------------


	int count = 0;	
	public  String tableForView() {
		DateFormat formato = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String formattedDate = formato.format(date);
		
		ArrayList<Tuple> listAux = new ArrayList<>(tuplesList);
		String tableForSendTemp = "";
		

		for(Tuple t : listAux){
			if(t.isVisible()==true) {
				String aux = new String("*"+t.getIpDestiny()+";"+t.getMetric());
				tableForSendTemp = tableForSendTemp+ aux;		
				System.out.println("add: "+aux);
			}							
		}
		
		if(tableForSendTemp.equalsIgnoreCase(this.tableForSend)== false) {
			this.tableForSend = tableForSendTemp;
			this.wereChanged = true;				
		}else {
			this.wereChanged = false;
		}	

		

		if((count == 0) || (listAux.size() ==0)){
			System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("Message sending for Routers Neighbors: ");	
			System.out.println( "!");	
			System.out.println("\n            Routing Table ");
			System.out.println("------------------------------------------------------------------------------");
			System.out.printf("%10s %20s %30s", "Destino", "Metrica", "Saida");
			System.out.println();
			System.out.println("------------------------------------------------------------------------------");
			for(Tuple tupla: listAux){
				if(tupla.isVisible() == true) {
					System.out.format("%10s %20s %30s",tupla.getIpDestiny(), tupla.getMetric(), tupla.getIpOut());
					System.out.println();					
				}				
			}
			System.out.println("------------------------------------------------------------------------------");
			System.out.println(formattedDate); 
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");


		}else {		
			System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("Message sending for Routers Neighbors: ");	
			System.out.println(tableForSend);	
			System.out.println("\n            Routing Table ");
			System.out.println("------------------------------------------------------------------------------");
			System.out.printf("%10s %20s %30s", "Destino", "Metrica", "Saida");
			System.out.println();
			System.out.println("------------------------------------------------------------------------------");
			for(Tuple tupla: listAux){
				if(tupla.isVisible() == true) {
					System.out.format("%10s %20s %30s",tupla.getIpDestiny(), tupla.getMetric(), tupla.getIpOut());
					System.out.println();					
				}				
			}
			System.out.println("------------------------------------------------------------------------------");
			System.out.println(formattedDate); 
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");
		}	

		count++;

		return tableForSendTemp;
	}
	

}
