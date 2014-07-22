package br.gov.planejamento.siop_app.model;

public class Query {
	
	private String name;
	private String uo;
	private String ptCod;
	private Integer year;
	
	public Query(){ }
	
	public Query(String name, String uo, String ptCod, Integer year) {
		this.name = name;
		this.uo = uo;
		this.ptCod = ptCod;
		this.year = year;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUo() {
		return uo;
	}

	public void setUo(String uo) {
		this.uo = uo;
	}

	public String getPtCod() {
		return ptCod;
	}
	
	public void setPtCod(String ptCod) {
		this.ptCod = ptCod;
	}
	
	public Integer getYear() {
		return year;
	}
	
	public void setYear(Integer year) {
		this.year = year;
	}
}
