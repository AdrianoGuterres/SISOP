package versao.avancada.roteador;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RoutingTable {

	private TuplesManager manager;
	private ArrayList<String> neigtborsList;
	
	private String tableForSend;	
	private String localHost;
	
	private boolean wereChanged;

	public RoutingTable(ArrayList<String> neighborList, String localHost) {
		
		this.wereChanged = false;
		this.manager = new TuplesManager();			
		this.neigtborsList = new ArrayList<>(neighborList);
		this.localHost = "";
		try {this.localHost = InetAddress.getLocalHost().getHostAddress();} catch (Exception e) {}
		
		System.out.println(localHost);
		
		fillTableWithNeibors();
	}
	
	public void fillTableWithNeibors() {
		for(int i =0; i < this.neigtborsList.size(); i++) {			
			String aux = this.neigtborsList.get(i);
			manager.addNeigtbor(aux);
			tableForSend = tableForSend+"*"+aux+";1";			
		}
	}

	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void updateTabela(String receivedTable, String neigtbor){			
		if(receivedTable.trim().contains("!")) {
			
			if(manager.updateTuple(neigtbor, 1, neigtbor) == false) {
				manager.addNeigtbor(neigtbor);
			}
			
			this.wereChanged = true;
			
		}else {
			
			String[] tableSplitedForAsterisk = receivedTable.split("\\*");
			for(int i = 1; i < tableSplitedForAsterisk.length; i++) {
				String[] tuple = tableSplitedForAsterisk[i].split(";");
				String newDestiny = tuple[0];
				int newMetric = Integer.parseInt(tuple[1]);
				
				if(newDestiny.equalsIgnoreCase(this.localHost)) {
					this.manager.updateTuple(neigtbor, 1, neigtbor);					
				}else {
					this.manager.updateTuple(neigtbor, 1, neigtbor);		
					
					if(manager.updateTuple(newDestiny, newMetric+1, neigtbor)==false) {
						this.manager.addTuple(newDestiny, newMetric+1, neigtbor);						
					}
				}				
			}					
		}
	}	

	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private int count = 0;

	public String get_tabela_string(){		
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
			
			
			this.manager.removeByTimestamp();
		
		return this.tableForSend;
	}

	public boolean isWereChanged() {
		return wereChanged;
	}
}
