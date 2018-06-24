package versao.avancada.roteador;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class RoutingTable {

	private TuplesManager manager;
	private ArrayList<String> neigtborsList;

	private String tableForSend;	
	private String localHost;

	private boolean wereChanged;
	private Semaphore sem; 

	public RoutingTable(ArrayList<String> neighborList) {

		this.sem = new Semaphore(1);		
		this.wereChanged = false;
		this.manager = new TuplesManager();			
		this.neigtborsList = new ArrayList<>(neighborList);
		this.localHost = "192.168.15.6";

		try {
			System.out.println(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		fillTableWithNeibors();
	}

	public void fillTableWithNeibors() {
		for(int i =0; i < this.neigtborsList.size(); i++) {	

			String newNeigtbor = neigtborsList.get(i);

			manager.addTuple(newNeigtbor, 1, newNeigtbor);


			tableForSend = tableForSend+"*"+newNeigtbor+";1";			
		}
	}



	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void updateTabela(String receivedTable, String neigtbor){	
		try { this.sem.acquire(); } catch (InterruptedException e) {}	

		manager.updateByDestiny(neigtbor, 1, neigtbor);


		if(receivedTable.trim().contains("!")) {			
			this.wereChanged = true;

		}else {

			String[] tableSplitedForAsterisk = receivedTable.trim().split("\\*");
			for(int i = 1; i < tableSplitedForAsterisk.length; i++) {
				String[] tuple = tableSplitedForAsterisk[i].split(";");
				String newDestiny = tuple[0];
				int newMetric = Integer.parseInt(tuple[1]);

				//verifica se é meu ip
				if(newDestiny.equalsIgnoreCase(this.localHost) == false) {
					
					if(neigtborsList.contains(newDestiny)== false) {
						
						Tuple tupleAux = manager.searchByDestiny(newDestiny);

						if(tupleAux != null) {

							if(tupleAux.getMetric() > newMetric) {
								manager.updateByDestiny(newDestiny, (newMetric + 1), neigtbor);							
							}

						}else {
							manager.addTuple(newDestiny, (newMetric + 1), neigtbor);
						}						
					}					
				}	
			}				
		}

		sem.release();
	}	

	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private int count = 0;

	public String get_tabela_string(){		
		try { sem.acquire(); } catch (InterruptedException e) { }

		String tableForSendTemp = "";


		for(int i = 0; i<manager.getTuplesList().size(); i++){
			String destinyTemp = manager.getTuplesList().get(i).getIpDestiny();
			int metricTemp = manager.getTuplesList().get(i).getMetric();

			tableForSendTemp = tableForSendTemp+"*"+ destinyTemp+";"+metricTemp;					
		}

		if(tableForSendTemp.equalsIgnoreCase(this.tableForSend)== false) {
			this.tableForSend = tableForSendTemp;
			this.wereChanged = true;				
		}else {
			this.wereChanged = false;
		}			


		DateFormat formato = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String formattedDate = formato.format(date);

		if((count == 0) || (tableForSend.length()==0)){
			System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("Message sending for Routers Neighbors: ");	
			System.out.println( "!");	
			System.out.println("\n            Routing Table ");
			System.out.println("------------------------------------------------------------------------------");
			System.out.printf("%10s %20s %30s", "Destino", "Metrica", "Saida");
			System.out.println();
			System.out.println("------------------------------------------------------------------------------");
			for(Tuple tupla: this.manager.getTuplesList()){
				System.out.format("%10s %20s %30s",tupla.getIpDestiny(), tupla.getMetric(), tupla.getIpOut());
				System.out.println();
			}
			System.out.println("------------------------------------------------------------------------------");
			System.out.println(formattedDate); 
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");
			count++;

		}else {		
			System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("Message sending for Routers Neighbors: ");	
			System.out.println(tableForSend);	
			System.out.println("\n            Routing Table ");
			System.out.println("------------------------------------------------------------------------------");
			System.out.printf("%10s %20s %30s", "Destino", "Metrica", "Saida");
			System.out.println();
			System.out.println("------------------------------------------------------------------------------");
			for(Tuple tupla:  this.manager.getTuplesList()){
				System.out.format("%10s %20s %30s",tupla.getIpDestiny(), tupla.getMetric(), tupla.getIpOut());
				System.out.println();
			}
			System.out.println("------------------------------------------------------------------------------");
			System.out.println(formattedDate); 
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");
		}			


		this.manager.removeNeigtborbyTimestamp();

		sem.release();

		return this.tableForSend;
	}

	public boolean isWereChanged() {
		return wereChanged;
	}
}
