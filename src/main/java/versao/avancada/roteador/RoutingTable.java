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
	private String localHost;

	private boolean wereChanged;

	public RoutingTable(ArrayList<String> neighborList) {
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
		}
	}



	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public synchronized void updateTabela(String receivedTable, String neigtbor){	

		manager.updateByDestiny(neigtbor, 1, neigtbor);

		if(receivedTable.trim().contains("!")) {			
			this.wereChanged = true;

		}else {

			String[] tableSplitedForAsterisk = receivedTable.split("\\*");
			ArrayList<String> aux = new ArrayList<>();

			for(int i = 0; i < tableSplitedForAsterisk.length; i++) {
				aux.add(tableSplitedForAsterisk[i]);								
			}


			for(int i = 1; i < aux.size(); i++) {
				System.out.println(receivedTable);

				String allAux = new String(aux.get(i));

				String[] tuple = allAux.split("\\;");

				System.out.println("Tupla completa: "+tableSplitedForAsterisk[i] + "                   Destino: "+tuple[0]+"     metrica: "+ tuple[1] +"\n");

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
				newDestiny = "";
				newMetric = 0;				
			}					
		}
		
	}	

	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


	public synchronized String get_tabela_string(){			
		
		return 	manager.tableForView();
	}
	


	public boolean isWereChanged() {
		return wereChanged;
	}
}
