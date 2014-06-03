package br.gov.planejamento.siop_app.model;

import java.io.Serializable;
import java.util.List;

public class Item implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<Classifier> classifierList;
	private int year;
	private double valueProjetoLei;
	private double valueDotacaoInicial;
	private double valueLeiMaisCredito;
	private double valueLiquidado;
	private double valueEmpenhado;
	private double valuePago;
	
	public enum ValuesType {
		PLOA { 
			public String toString() {
		        return "ploa";
		    }
		},
		LOA { 
		    public String toString() {
		        return "loa";
		    }
		},
		LEI_MAIS_CREDITOS { 
		    public String toString() {
		        return "atual";
		    }
		},
		EMPENHADO { 
		    public String toString() {
		        return "empenhado";
		    }
		},
		LIQUIDADO { 
		    public String toString() {
		        return "liquidado";
		    }
		},
		PAGO { 
		    public String toString() {
		        return "pago";
		    }
		}
	};
	
	public Item(){	}
	
	public Item(List<Classifier> classifierList, int year,
			double valueProjetoLei, double valueDotacaoInicial,
			double valueLeiMaisCredito, double valueLiquidado,
			double valueEmpenhado, double valuePago) {
		this.classifierList = classifierList;
		this.year = year;
		this.valueProjetoLei = valueProjetoLei;
		this.valueDotacaoInicial = valueDotacaoInicial;
		this.valueLeiMaisCredito = valueLeiMaisCredito;
		this.valueLiquidado = valueLiquidado;
		this.valueEmpenhado = valueEmpenhado;
		this.valuePago = valuePago;
	}

	public List<Classifier> getClassifierList() {
		return classifierList;
	}

	public void setClassifierList(List<Classifier> classifierList) {
		this.classifierList = classifierList;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getValueProjetoLei() {
		return valueProjetoLei;
	}

	public void setValueProjetoLei(double valueProjetoLei) {
		this.valueProjetoLei = valueProjetoLei;
	}

	public double getValueDotacaoInicial() {
		return valueDotacaoInicial;
	}

	public void setValueDotacaoInicial(double valueDotacaoInicial) {
		this.valueDotacaoInicial = valueDotacaoInicial;
	}

	public double getValueLeiMaisCredito() {
		return valueLeiMaisCredito;
	}

	public void setValueLeiMaisCredito(double valueLeiMaisCredito) {
		this.valueLeiMaisCredito = valueLeiMaisCredito;
	}

	public double getValueLiquidado() {
		return valueLiquidado;
	}

	public void setValueLiquidado(double valueLiquidado) {
		this.valueLiquidado = valueLiquidado;
	}

	public double getValueEmpenhado() {
		return valueEmpenhado;
	}

	public void setValueEmpenhado(double valueEmpenhado) {
		this.valueEmpenhado = valueEmpenhado;
	}

	public double getValuePago() {
		return valuePago;
	}

	public void setValuePago(double valuePago) {
		this.valuePago = valuePago;
	}
}