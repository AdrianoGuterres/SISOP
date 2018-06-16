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

	}

	public boolean isChanged() {
		return wereChanged;		
	}


	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void updateTabela(String receivedTable, String neighborIP){		
		String localHost = "";
		try {
			localHost = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long time = System.currentTimeMillis();

		if(receivedTable.contains("!")) {
			wereChanged = true;

		}else {
			String[] aux = receivedTable.split("\\*");

			for(int i = 1; i < aux.length; i++) {			
				String tuplaString = aux[i];
				listaTuplas.add(new Tupla(tuplaString, neighborIP, listaVizinhos));	
			}	
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
