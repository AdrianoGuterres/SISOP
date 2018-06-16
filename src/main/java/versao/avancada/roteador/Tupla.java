package versao.avancada.roteador;

import java.util.ArrayList;

public class Tupla {
	private String destino;
	private int metrica;
	private String saida;
	private long timeStamp;
	private boolean forRemove;
	private ArrayList<String> neigtborList;

	public Tupla(String reveived, String ipSender, ArrayList<String> neigtborList) {
		
		this.neigtborList = neigtborList;		
		
		String [] temp = reveived.split(";");				
		setDestino(temp[0]);
		setMetrica(Integer.parseInt(temp[1]));
		
		setSaida(ipSender);
		setTimeStamp(System.currentTimeMillis());		
		
		setForRemove(false);
	}	
	
	
	
	
	public boolean isForRemove() {
		return forRemove;
	}

	public void setForRemove(boolean forRemove) {
		this.forRemove = forRemove;
	}
	
	public String getDestino() {		
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public int getMetrica() {
		return metrica;
	}

	public void setMetrica(int metrica) {
		if(neigtborList.contains(destino)){
			this.metrica = 1;						
		} else {
			this.metrica = metrica + 1;
		}		
		
	}

	public String getSaida() {
		return saida;
	}

	public void setSaida(String saida) {
		
		if(neigtborList.contains(saida)) {
			this.saida = destino;
		}else {
			this.saida = saida;
		}		
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}	
	
	

}
