package versao.avancada.roteador;

import java.util.ArrayList;
import java.util.HashMap;

public class TabelaRoteamento {

	private ArrayList<String> completeTable;	
	private ArrayList<String> routersNextDoor;
	private boolean changed;
	private String localHost;
	private String datagramHost;
	private String lastTable="";

	public TabelaRoteamento(ArrayList<String> routersNextDoor, String localHost){

		this.routersNextDoor = routersNextDoor;
		this.changed = false;
		this.completeTable = new ArrayList<>();
		this.localHost = localHost;
		firstUpdateTable();

	}

	private void firstUpdateTable() {
		for(String x : routersNextDoor) {
			completeTable.add(x+";"+1+";"+x);			
		}

	}

	public boolean isChanged() {
		return changed;
	}














	public void updateTabela(String receivedTable, String datagramHost){
		this.datagramHost = datagramHost;


		if(receivedTable.equalsIgnoreCase("!")) {
			this.changed = true;
		}else {

			String[] splitedByAsterisk = receivedTable.split("\\*");	
			for(int i = 1 ; i<splitedByAsterisk.length;i++) {
				String[] splitedByComaDot = splitedByAsterisk[i].split(";");

				String ip = splitedByComaDot[0];
				int metric = Integer.parseInt(splitedByComaDot[1])+1;

				if(ip.equalsIgnoreCase(this.localHost)) {

				}else if(routersNextDoor.contains(ip)) {

				}else {
					completeTable.add(ip+";"+metric+";"+datagramHost);
				}						
			}
			
			int aux = completeTable.size();
			ArrayList<String > listAux = new ArrayList<>();
			
			int i;
			int j;
			
			for( i = 0; i < aux; i++) {
				String[] tuplaA = completeTable.get(i).split(";");

				String ipA = tuplaA[0];
				int meticA = Integer.parseInt(tuplaA[1]);
				for( j = i+1; j < aux-1; j++) {
					String[] tuplaB = completeTable.get(j).split(";"); 
					String ipB = tuplaB[0]; 
					int meticB = Integer.parseInt(tuplaB[1]);

					if(ipA.equalsIgnoreCase(ipB)) {								
						if(meticA < meticB) {									
						}else if(meticA > meticB) {
												
						}else {
							
						}
					}
				}					
			}
		}
	}


















	public String get_tabela_string(){		

		String tabela = "";

		String tableSysout = "";

		for(String x: completeTable) {
			tableSysout = tableSysout+x+"\n";			
		}

		this.lastTable="  Routing Table\n"+tableSysout;

		System.out.println(lastTable);


		if(this.completeTable.size() > 0) {		
			for(String x : completeTable) {
				String[] ipWithMetric = x.split(";");			
				String ip = ipWithMetric[0];
				String metric = ipWithMetric[1];
				tabela = tabela+"*"+ip+";"+metric;			
			}	

			return tabela;
		} else {
			return "!";
		}		

	}

}
