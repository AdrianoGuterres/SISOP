package versao.avancada.roteador;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class RoutingTable {

	private TuplesManager manager;

	private ArrayList<String> neigtborList;
	private ArrayList<String> neigtborListAux;

	private String lastTableSended;
	private String localHost;

	private boolean wereChanged;	

	private Semaphore sem;

	public RoutingTable(ArrayList<String> neighborList, String localHost) throws IOException{
		this.neigtborList = new ArrayList<>(neighborList);
		this.neigtborListAux = new ArrayList<>(neigtborList);		

		this.manager = new TuplesManager(neighborList);				

		this.localHost = InetAddress.getLocalHost().getHostAddress();

		lastTableSended = "";

		this.sem = new Semaphore(1);

		for(String x:neighborList) {
			manager.addTuple(x, 1, x);
			neigtborList.add(x);
			neigtborListAux.add(x);
		}
	}

	public boolean isChanged() {
		return wereChanged;		
	}

	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void updateTabela(String receivedTable, String neighborIP){	
		

		try {
			this.sem.acquire();

			//verifica se é entrada de rede
			if(receivedTable.contains("!")) {
				wereChanged = true;
				if(neigtborList.contains(neighborIP)) {
					neigtborListAux.add(neighborIP);			
					manager.addTuple(neighborIP, 1, neighborIP);
				}

			}else {
				String[] aux = receivedTable.split("\\*");

				for(int i = 1; i < aux.length; i++) {			
					String tuplaString[] = aux[i].split(";");

					String newDestiny = tuplaString[0];

					int newMetric = Integer.parseInt(tuplaString[1]);
					if(newDestiny.equalsIgnoreCase(localHost)==false) {

						for(int j = 0; j < neigtborList.size(); j++) {

							if(neigtborList.get(j).equalsIgnoreCase(newDestiny)==false) {
								this.manager.addTuple(newDestiny, newMetric, neighborIP);								
							}
						}
					}

				}		

			}

		} catch (InterruptedException e) {}
		this.sem.release();
	}	

	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	int count = 0;
	public String get_tabela_string(){		
		String lastTableSendedTemp ="";	
		

		try {			
			this.sem.acquire();
			

			this.manager.verifyTimestamp();		

			for(Tuple x:manager.getTuplasList()) {
				lastTableSendedTemp = lastTableSendedTemp+"*"+x.getIpDestiny()+";"+x.getMetric();				
			}

			if(lastTableSendedTemp.equalsIgnoreCase(lastTableSended)) {
				wereChanged = false;				
			}else {
				wereChanged = true;
				this.lastTableSended = lastTableSendedTemp;
			}			
			
			
			

			DateFormat formato = new SimpleDateFormat("HH:mm:ss");
			Date date = new Date();
			String formattedDate = formato.format(date);

			if((neigtborList.size() == 0) || (count ==0)){
				System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				System.out.println("Message sending for Routers Neighbors: ");	
				System.out.println( "!");	
				System.out.println("\n            Routing Table ");
				System.out.println("------------------------------------------------------------------------------");
				System.out.printf("%10s %20s %30s", "Destino", "Metrica", "Saida");
				System.out.println();
				System.out.println("------------------------------------------------------------------------------");
				for(Tuple tupla: manager.getTuplasList()){
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
				System.out.println(lastTableSended);	
				System.out.println("\n            Routing Table ");
				System.out.println("------------------------------------------------------------------------------");
				System.out.printf("%10s %20s %30s", "Destino", "Metrica", "Saida");
				System.out.println();
				System.out.println("------------------------------------------------------------------------------");
				for(Tuple tupla:  manager.getTuplasList()){
					System.out.format("%10s %20s %30s",tupla.getIpDestiny(), tupla.getMetric(), tupla.getIpOut());
					System.out.println();
				}
				System.out.println("------------------------------------------------------------------------------");
				System.out.println(formattedDate); 
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");
			}	

		} catch (InterruptedException e) {}
		sem.release();

		return lastTableSended;
	}
}
