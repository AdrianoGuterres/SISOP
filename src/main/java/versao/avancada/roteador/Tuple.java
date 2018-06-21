package versao.avancada.roteador;

public class Tuple {
	private String ipDestiny;
	private int metric;
	private String ipOut;
	private long timeStamp;

	public Tuple(String ipDestiny, int metric, String ipOut) {
		setIpDestiny(ipDestiny);
		setIpOut(ipOut);			
		setMetric(metric);
		setTimeStamp(System.currentTimeMillis()+30000);	
	}

	public String getIpDestiny() {
		return ipDestiny;
	}

	public void setIpDestiny(String ipDestiny) {
		this.ipDestiny = ipDestiny;
	}

	public int getMetric() {
		return metric;
	}

	public void setMetric(int metric) {
		this.metric = metric;
	}

	public String getIpOut() {
		return ipOut;
	}

	public void setIpOut(String ipOut) {
		this.ipOut = ipOut;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

}
