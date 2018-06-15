package versao.avancada.roteador;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class TabelaRoteamento2 {

	private ArrayList<ArrayList<String>> listaCompleta;
	private HashSet<String> mapaVizinhos;
	private static long timeStamp;

	private ArrayList<String> ipDestino;
	private ArrayList<Integer> metrica;
	private ArrayList<String> ipSaida;
	private ArrayList<Long> oldTimeStamp;


	//   index 0  || index 1 || index 3  || index 4
	// ip destino || metrica || ip saida || timeStamp
	public TabelaRoteamento2(HashSet<String> neighborList){
		ipDestino = new ArrayList<>();
		metrica  = new ArrayList<>();
		ipSaida = new ArrayList<>();
		oldTimeStamp = new ArrayList<>();

		this.timeStamp = System.currentTimeMillis();
		this.listaCompleta = new ArrayList<ArrayList<String>>();
		this.mapaVizinhos = neighborList;
		for(int i = 0; i < 4; i++) {			
			listaCompleta.add(new ArrayList<String>());
		}

		for(String x: neighborList) {
			ipDestino.add(x);			
			metrica.add(1);			
			ipSaida.add(x);
			oldTimeStamp.add(timeStamp);
		}	
	}



	public void updateTabela(String receivedTable, String neighborIP){		
		String localHost = "";
		try { localHost = InetAddress.getLocalHost().getHostAddress();} catch (UnknownHostException e) {}


		if(receivedTable.contains("!")) {
			//enviar lista
		}else {
			
		}
		
	
		
		
		
		
	}



	public String get_tabela_string(){		

		return null;

	}



}
