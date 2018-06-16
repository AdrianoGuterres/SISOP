package versao.avancada.roteador;

public class Tupla {
	private String destino;
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
		this.metrica = metrica;
	}

	public String getSaida() {
		return saida;
	}

	public void setSaida(String saida) {
		this.saida = saida;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	private int metrica;
	private String saida;
	private long timeStamp;
	
	public Tupla(String reveived, String ipSender) {
		String [] temp = reveived.split(";");		
		
		setDestino(temp[0]);
		setMetrica(Integer.parseInt(temp[1])+1);
		setSaida(ipSender);
		setTimeStamp(System.currentTimeMillis());				
	}

}
