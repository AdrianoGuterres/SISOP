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


	//-------------------------------------------------------------------------------------
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
				listaTuplas.add(new Tupla(tuplaString, neighborIP));	
			}	
		}

		for(int i =0; i < listaTuplas.size(); i++) {			
			if(listaVizinhos.contains(listaTuplas.get(i).getDestino())) {
				listaTuplas.remove(i);								
			}			
		}

		for(int i =0; i < listaTuplas.size(); i++) {			
			if(listaTuplas.get(i).getDestino().contentEquals(localHost)) {
				listaTuplas.remove(i);								
			}			
		}

		for(int i =0; i < listaTuplas.size(); i++) {
			String destino = listaTuplas.get(i).getDestino();
			int metrica = listaTuplas.get(i).getMetrica();
			String saida = listaTuplas.get(i).getSaida();
			long timeStamp = listaTuplas.get(i).getTimeStamp();

			for(int j = i ; j < listaTuplas.size(); j++) {

				if(listaTuplas.get(j).getMetrica() > metrica) {
					listaTuplas.remove(j);					
				}				
			}						
		}

		for(int i =0; i < listaTuplas.size(); i++) {
			String destino = listaTuplas.get(i).getDestino();
			int metrica = listaTuplas.get(i).getMetrica();
			String saida = listaTuplas.get(i).getSaida();
			long timeStamp = listaTuplas.get(i).getTimeStamp();

			for(int j = i ; j < listaTuplas.size(); j++) {

				if(listaTuplas.get(j).getMetrica() > metrica) {
					listaTuplas.remove(j);					
				}				
			}						
		}

		for(int i =0; i < listaTuplas.size(); i++) {
			String destino = listaTuplas.get(i).getDestino();
			int metrica = listaTuplas.get(i).getMetrica();
			String saida = listaTuplas.get(i).getSaida();
			long timeStamp = listaTuplas.get(i).getTimeStamp();

			for(int j = i ; j < listaTuplas.size(); j++) {

				if(listaTuplas.get(j).getTimeStamp()+30000 < time) {
					listaTuplas.remove(j);					
				}				
			}						
		}

		for(int i =0; i < listaTuplas.size(); i++) {
			String destino = listaTuplas.get(i).getDestino();
			int metrica = listaTuplas.get(i).getMetrica();
			String saida = listaTuplas.get(i).getSaida();
			long timeStamp = listaTuplas.get(i).getTimeStamp();

			for(int j = i ; j < listaTuplas.size(); j++) {

				if(listaTuplas.get(j).getDestino().contentEquals(destino)) {
					listaTuplas.remove(j);					
				}				
			}						
		}





	}		



	public String get_tabela_string(){		
		DateFormat formato = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String formattedDate = formato.format(date);

		String aux ="";
		String aux2 = "";

		for(int i =0; i < listaTuplas.size(); i++) {
			String destino = listaTuplas.get(i).getDestino();
			int metrica = listaTuplas.get(i).getMetrica();
			String saida = listaTuplas.get(i).getSaida();
			long timeStamp = listaTuplas.get(i).getTimeStamp();

			aux = aux+"|"+destino+"|"+metrica+"|"+saida+"\n";
			aux2 = "*"+destino+";"+metrica;

		}


		String send= "";


		String forSend = "!";		
		if(listaTuplas.size()==0){
			forSend = "!";			
			System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");	
			System.out.println("Message sending for Routers Neighbors: ");	
			System.out.println(forSend);	
			System.out.println("\n    Routing Table \n-------------/--/---------------");
			System.out.println(aux);
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");
			forSend = "!";

		}else {				
			System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");	
			System.out.println("Message sending for Routers Neighbors: ");	
			System.out.println(forSend);	
			System.out.println("\n    Routing Table \n"+aux2);
			System.out.println(aux);
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");

			forSend = aux2;
		}
		return forSend;

	}


	public int getSizeNeighborActive() {
		return 0;//neighborRouters.size();
	}

}
