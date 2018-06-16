package versao.avancada.roteador;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class TabelaRoteamento {

	private ArrayList<Tupla> listaTuplas;
	private boolean wereChanged;
	private ArrayList<String> listaVizinhos;

	public TabelaRoteamento(ArrayList<String> neighborList, String localHost){
		listaTuplas = new ArrayList<>();
		listaVizinhos = neighborList;

		for(String x:neighborList) {
			listaTuplas.add(new Tupla(x+";1",x,listaVizinhos));
		}

	}

	public boolean isChanged() {
		return wereChanged;		
	}


	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void updateTabela(String receivedTable, String neighborIP){	

		String localHost = "";
		try {localHost = InetAddress.getLocalHost().getHostAddress();} catch (UnknownHostException e) {}

		long time = System.currentTimeMillis();


		//verifica se é entrada de rota
		if(receivedTable.contains("!")== false) {

			//Preenche a tabela com tudo
			if(receivedTable.contains("!")) {
				wereChanged = true;

			}else {
				String[] aux = receivedTable.split("\\*");

				for(int i = 1; i < aux.length; i++) {			
					String tuplaString = aux[i];
					listaTuplas.add(new Tupla(tuplaString, neighborIP, listaVizinhos));	
				}	
			}

			// Verifica se o destino é eu mesmo
			for(Tupla x: listaTuplas) {				
				if(x.getDestino().equalsIgnoreCase(localHost)) {
					x.setForRemove(true);					
				}				
			}

			// Verifica se o destino é eu mesmo
			for(Tupla x: listaTuplas) {				
				if(x.getDestino().equalsIgnoreCase(localHost)) {
					x.setForRemove(true);					
				}				
			}
			
			
			//verifica os destinos duplicados e marca pela metrica
			for(int i= 0; i< listaTuplas.size(); i++) {
				for(int j= i+1; j < listaTuplas.size()-1; j++) {					
					if(listaTuplas.get(i).getDestino().equalsIgnoreCase(listaTuplas.get(j).getDestino())) {
						if(listaTuplas.get(i).getMetrica() <= listaTuplas.get(j).getMetrica()) {
							listaTuplas.get(j).setForRemove(true);																					
						}												
					}
				}
			}
			
			for(Tupla x:listaTuplas) {
				if(x.getTimeStamp()+30000 < time) {			
					x.setForRemove(true);
				}				
				
				//System.out.println("TimeStamp: "+x.getTimeStamp() +"  time actual: "+time);
			}
			
			
			for(int i = 1; i < listaTuplas.size(); i++) {
				if(listaTuplas.get(i).isForRemove()) {
					listaTuplas.remove(i);					
				}				
			}
						

		}else {
			wereChanged = true;
		}














	}		

	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public String get_tabela_string(){		
		DateFormat formato = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String formattedDate = formato.format(date);

		String tableForSend ="";

		for(int i =0; i < listaTuplas.size(); i++) {
			String destino = listaTuplas.get(i).getDestino();
			int metrica = listaTuplas.get(i).getMetrica();
			tableForSend = tableForSend+"*"+destino+";"+metrica;			
		}


		if(listaVizinhos.size() == 0){
			tableForSend = "!";
			System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("Message sending for Routers Neighbors: ");	
			System.out.println(tableForSend);	
			System.out.println("\n            Routing Table ");
			System.out.println("--------------------------------------------");
			System.out.printf("%6s %12s %18s", "Destino", "Metrica", "Saida");
			System.out.println();
			System.out.println("--------------------------------------------");
			for(Tupla tupla: listaTuplas){
				System.out.format("%6s %12s %18s",tupla.getDestino(), tupla.getMetrica(), tupla.getSaida());
				System.out.println();
			}
			System.out.println("--------------------------------------------");
			System.out.println(formattedDate); 
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");

		}else {				
			System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("Message sending for Routers Neighbors: ");	
			System.out.println(tableForSend);	
			System.out.println("\n            Routing Table ");
			System.out.println("--------------------------------------------");
			System.out.printf("%6s %12s %18s", "Destino", "Metrica", "Saida");
			System.out.println();
			System.out.println("--------------------------------------------");
			for(Tupla tupla: listaTuplas){
				System.out.format("%6s %12s %18s",tupla.getDestino(), tupla.getMetrica(), tupla.getSaida());
				System.out.println();
			}
			System.out.println("--------------------------------------------");
			System.out.println(formattedDate); 
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");
		}	
		return tableForSend;
	}


	public int getSizeNeighborActive() {
		return listaVizinhos.size();
	}

}
