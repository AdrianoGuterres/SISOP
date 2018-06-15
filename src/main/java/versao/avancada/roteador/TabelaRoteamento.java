package versao.avancada.roteador;

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
	private HashMap<String, String> mapa;
	private ArrayList<String> neighborRouters ;
	private ArrayList<String> originalneighborRouters;

	boolean wereChanged;

	String localHost;
	String tabelaRoteamento;
	String tabelaRoteamentoAntiga;
	String tabelaRoteamentoVisao;

	public TabelaRoteamento(ArrayList<String> neighborList, String localHost){
		mapa = new HashMap<>();

		wereChanged = false;
		tabelaRoteamento = "";


		this.localHost = localHost;		
		this.neighborRouters = neighborList;	
		this.originalneighborRouters = neighborList;		
		start(neighborList);
	}

	// Ip destino || metrica || ip de saida || timestamp
	public void start(ArrayList<String> neighbor) {	
		long time = System.currentTimeMillis();

		for(String x: neighbor) {
			mapa.put(x, "1|"+ 1+"|"+time);
			tabelaRoteamento = tabelaRoteamento +"*"+ x+";" +1;
			tabelaRoteamentoVisao = tabelaRoteamentoVisao+"\n"+x+"|"+1+"|"+x+time;	
		}

		tabelaRoteamentoAntiga = tabelaRoteamento;
	}

	public boolean isChanged() {
		return wereChanged;		
	}

	public void updateTabela(String receivedTable, String neighborIP){		
		long time = System.currentTimeMillis();

		HashMap<String, String> mapaAux = new HashMap<>();
		mapaAux = mapa;

		if(receivedTable.contains("!")) {
			wereChanged = true;
			if(originalneighborRouters.contains(neighborIP)) {
				mapa.put(neighborIP, "1|"+ neighborIP+"|"+time);
			}			
		}else {
			String[] msgSplitedByAsterisk = receivedTable.split("\\*");
			for(int i =1; i< msgSplitedByAsterisk.length; i++) {
				String[] stringTupla = msgSplitedByAsterisk[i].split(";");
				String ipReceived = stringTupla[0];	
				int metricReceived = Integer.parseInt(stringTupla[1]);	


				if(mapa.containsKey(ipReceived)== false) {
					mapa.put(ipReceived, (metricReceived+1)+"|"+ localHost+"|"+ time);					
				}else {

					for(String x:mapaAux.keySet()) {					
						String[] tupla = mapaAux.get(x).split("|");
						String ipDestino = x;
						int metrica = Integer.parseInt(tupla[1]);
						String saida = tupla[2];
						String timestamp= tupla[3];

						if(metricReceived < metrica) {
							mapa.put(x, (metricReceived+1)+"|"+ localHost+"|"+ time);

						}else {
							mapa.put(x, metrica+"|"+ localHost+"|"+ time);
						}						
					}									
				}				
			}			
		}

		tabelaRoteamentoAntiga = tabelaRoteamento;

		tabelaRoteamento = "";

	}




	public String get_tabela_string(){		
		DateFormat formato = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String formattedDate = formato.format(date);

		HashMap< String, String> mapaAux = new HashMap<>(); 		
		mapaAux = mapa;

		String tabelaRoteamentoVisao = "";

		int count = 0;



		for(String x:mapa.keySet()) {
			String[] tupla = mapaAux.get(x).split("|");
			String ipDestino = x;
			String metrica = tupla[1];
			String saida = tupla[2];		
					
		}



		String forSend = "!";		
		if(mapa.size()==0){
			forSend = "!";			
			System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");	
			System.out.println("Message sending for Routers Neighbors: ");	
			System.out.println(forSend);	
			System.out.println("\n    Routing Table \n-------------/--/---------------");
			System.out.println(formattedDate);
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");

		}else {						
			forSend = tabelaRoteamento;			
			System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");	
			System.out.println("Message sending for Routers Neighbors: ");	
			System.out.println(forSend);	
			System.out.println("\n    Routing Table \n"+tabelaRoteamentoVisao);
			System.out.println(formattedDate);
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");

		}
		return forSend;

	}


	public int getSizeNeighborActive() {
		return neighborRouters.size();
	}

}
