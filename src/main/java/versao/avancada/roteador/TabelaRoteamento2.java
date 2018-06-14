package versao.avancada.roteador;

import java.util.ArrayList;
import java.util.HashSet;


public class TabelaRoteamento2 {
	
	private ArrayList<ArrayList<String>> listaCompleta;
	private static long timeStamp;
	

	//   index 0  || index 1 || index 3  || index 4
	// ip destino || metrica || ip saida || timeStamp
	public TabelaRoteamento2(HashSet<String> neighborList){
		
		this.timeStamp = System.currentTimeMillis();
		this.listaCompleta = new ArrayList<ArrayList<String>>();
		for(int i = 0; i < 4; i++) {
			listaCompleta.add(new ArrayList<String>());
		}
		
		
		for(String x: neighborList) {
			listaCompleta.get(0).add(x);
			listaCompleta.get(1).add("1");
			listaCompleta.get(3).add(x);
			listaCompleta.get(4).add("timeStamp");
		}		
	}
	
	
	
	
	
	public void start(HashSet<String> neighbor) {	
		
	}

	
	public void updateTabela(String receivedTable, String neighborIP){		
	
	}



	public String get_tabela_string(){		
		
		return null;
		
	}
	
	

}
