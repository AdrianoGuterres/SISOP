package versao.avancada.roteador;

import java.util.ArrayList;
import java.util.HashMap;

public class TabelaRoteamentoA {

	private ArrayList<String> ipList;
	private ArrayList<String> routersNextDoor;
	private boolean changed;

	public TabelaRoteamentoA(ArrayList<String> routersNextDoor){
		this.routersNextDoor = routersNextDoor;
		this.ipList = new ArrayList<>();
		this.changed = false;
		
		for(String x : routersNextDoor) {
			ipList.add(x+";"+1);			
		}
	}

	public boolean isChanged() {
		return changed;
	}



	public void updateTabela(String receivedTable){
		
		String lastTable = get_tabela_string();		

		if(receivedTable.equalsIgnoreCase("!") == false) {
			String[] stringWithoutBlankSpace = receivedTable.split("\\*");		
			HashMap<String, Integer> mapIp = new HashMap<>();

			for(int i = 1; i< stringWithoutBlankSpace.length;i++) {
				String [] tupla = stringWithoutBlankSpace[i].split(";"); 
				String ip = tupla[0];			
				Integer metric = Integer.parseInt(tupla[1]);
				metric++;

				if(routersNextDoor.contains(ip) == false) {			
					mapIp.put(ip, metric);
				}else {
					mapIp.put(ip,1);
				}
			}

			HashMap<String, Integer> mapOf = new HashMap<>();
			
			if(ipList.size() >0) {
				for(String x:ipList) {
					String [] tupla = x.split(";"); 
					String ip = tupla[0];			
					Integer metric = Integer.parseInt(tupla[1]);				
					mapIp.put(ip, metric);						
				}				
			}			

			ipList.clear();
			for(String x:mapIp.keySet()) {
				if(mapOf.containsKey(x)) {
					if(mapIp.get(x) == mapOf.get(x)) {
						ipList.add(x+";"+mapIp.get(x));
					}else if(mapIp.get(x) < mapOf.get(x)) {
						ipList.add(x+";"+mapIp.get(x));
					} else {
						ipList.add(x+";"+mapOf.get(x));
					}										
				}else {
					ipList.add(x+";"+mapIp.get(x));					
				}				
			}			
		}	
		
		if(get_tabela_string().equalsIgnoreCase(lastTable) == false) {
			this.changed = true;
		}else {			
			this.changed = false;
			String aux = get_tabela_string();			
			String[] stringWithoutBlankSpace = aux.split("\\*");			
			String update = "";			
			for(String x:stringWithoutBlankSpace) {				
				update = update +x+"\n";
			}			
			System.out.println("\nRouting Table"+ update);			
		}		
	}

	public String get_tabela_string(){
		String tabela = "";
		if(this.ipList.size() > 0) {
			for(String x : ipList) {
				tabela = tabela+"*"+x;			
			}				
			return tabela;
		} else {
			return "!";

		}		
	}
}
