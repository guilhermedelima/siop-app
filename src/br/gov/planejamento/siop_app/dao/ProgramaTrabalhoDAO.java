package br.gov.planejamento.siop_app.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.gov.planejamento.siop_app.model.Classifier;
import br.gov.planejamento.siop_app.model.ClassifierType;
import br.gov.planejamento.siop_app.model.Item;
import br.gov.planejamento.siop_app.service.EndpointSPARQL;
import br.gov.planejamento.siop_app.util.json.JsonParser;

public class ProgramaTrabalhoDAO {

	private EndpointSPARQL endpoint;
	private JsonParser<HashMap<String, Object>> parser;
	
	public ProgramaTrabalhoDAO(EndpointSPARQL endpoint, JsonParser<HashMap<String, Object>> parser){
		this.endpoint = endpoint;
		this.parser = parser;
	}

	public Item getProgramaTrabalho(int year, String unidade, String pt) {
		String query, response;
		HashMap<String, Object> values;
		String codFuncao, codSubfuncao, codUnidade, codPrograma, codAcao, codLocalizador;
		Item item;

		String[] ptSplit = pt.split("\\.");
		codFuncao = ptSplit[0];
		codSubfuncao = ptSplit[1];
		codUnidade = unidade;
		codPrograma = ptSplit[2];
		codAcao = ptSplit[3];
		codLocalizador = ptSplit[4];

		query = buildQuery(year, codFuncao, codSubfuncao, codUnidade, codPrograma, codAcao, codLocalizador);
		response = endpoint.execSPARQLQuery(query);
		values = response != null ? parser.convertJsonToObject(response) : null;

		item = values!=null && !values.isEmpty() ? convertMapToItem(values, year, codFuncao, 
				codSubfuncao, codUnidade, codPrograma, codAcao, codLocalizador) : null; 

		return item;
	}


	private Item convertMapToItem(HashMap<String, Object> values, int year, String codFuncao, 
			String codSubfuncao, String codUnidade, String codPrograma, String codAcao, String codLocalizador){

		List<Classifier> classifiers;
		Item item;

		String labelFuncao, labelSubFuncao, labelUnidade; 
		String labelPrograma, labelAcao, labelLocalizador;
		double ploa, loa, leiMaisCredito, empenhado, liquidado, pago;
		
		labelFuncao = (String) values.get(ClassifierType.FUNCAO.getId());
		labelSubFuncao = (String) values.get(ClassifierType.SUBFUNCAO.getId());
		labelUnidade = (String) values.get(ClassifierType.UO.getId());
		labelPrograma = (String) values.get(ClassifierType.PROGRAMA.getId());
		labelAcao = (String) values.get(ClassifierType.ACAO.getId());
		labelLocalizador = (String) values.get(ClassifierType.SUBTITULO.getId());
		
		classifiers = new ArrayList<Classifier>();
		
		classifiers.add(new Classifier(labelFuncao, codFuncao, year, ClassifierType.FUNCAO));
		classifiers.add(new Classifier(labelSubFuncao, codSubfuncao, year, ClassifierType.SUBFUNCAO));
		classifiers.add(new Classifier(labelUnidade, codUnidade, year, ClassifierType.UO));
		classifiers.add(new Classifier(labelPrograma, codPrograma, year, ClassifierType.PROGRAMA));
		classifiers.add(new Classifier(labelAcao, codAcao, year, ClassifierType.ACAO));
		classifiers.add(new Classifier(labelLocalizador, codLocalizador, year, ClassifierType.SUBTITULO));
		
		ploa = Double.valueOf( (String)values.get(Item.ValuesType.PLOA.toString()) );
		loa = Double.valueOf( (String)values.get(Item.ValuesType.LOA.toString()) );
		leiMaisCredito = Double.valueOf( (String)values.get(Item.ValuesType.LEI_MAIS_CREDITOS.toString()) );
		empenhado= Double.valueOf( (String)values.get(Item.ValuesType.EMPENHADO.toString()) );
		liquidado = Double.valueOf( (String)values.get(Item.ValuesType.LIQUIDADO.toString()) );
		pago = Double.valueOf( (String)values.get(Item.ValuesType.PAGO.toString()) );
		
		item = new Item(classifiers, year, ploa, loa, leiMaisCredito, liquidado, empenhado, pago);
		
		return item;
	}


	private String buildQuery(int year, String codFuncao, String codSubfuncao, 
			String codUnidade, String codPrograma, String codAcao, String codLocalizador){

		String query = "SELECT "+
				"?"+ClassifierType.FUNCAO.getId()+" "+
				"?"+ClassifierType.SUBFUNCAO.getId()+" "+
				"?"+ClassifierType.UO.getId()+" "+
				"?"+ClassifierType.PROGRAMA.getId()+" "+
				"?"+ClassifierType.ACAO.getId()+" "+
				"?"+ClassifierType.SUBTITULO.getId()+" "+
				"(SUM(?ploa) AS ?"+Item.ValuesType.PLOA+")"+
				"(SUM(?loa) AS ?"+Item.ValuesType.LOA+")"+
				"(SUM(?atual) AS ?"+Item.ValuesType.LEI_MAIS_CREDITOS+")"+
				"(SUM(?emp) AS ?"+Item.ValuesType.EMPENHADO+")"+
				"(SUM(?liq) AS ?"+Item.ValuesType.LIQUIDADO+")"+
				"(SUM(?pago) AS ?"+Item.ValuesType.PAGO+")"+
				"WHERE {"+
				"[] loa:temExercicio [loa:identificador "+year+"];"+
				"loa:"+ClassifierType.FUNCAO.getProperty()+" [loa:codigo \""+codFuncao+"\"; rdfs:label ?"+ClassifierType.FUNCAO.getId()+"];"+
				"loa:"+ClassifierType.SUBFUNCAO.getProperty()+" [loa:codigo \""+codSubfuncao+"\"; rdfs:label ?"+ClassifierType.SUBFUNCAO.getId()+"];"+
				"loa:"+ClassifierType.UO.getProperty()+" [loa:codigo \""+codUnidade+"\"; rdfs:label ?"+ClassifierType.UO.getId()+"];"+
				"loa:"+ClassifierType.PROGRAMA.getProperty()+" [loa:codigo \""+codPrograma+"\"; rdfs:label ?"+ClassifierType.PROGRAMA.getId()+"];"+
				"loa:"+ClassifierType.ACAO.getProperty()+" [loa:codigo \""+codAcao+"\"; rdfs:label ?"+ClassifierType.ACAO.getId()+"];"+
				"loa:"+ClassifierType.SUBTITULO.getProperty()+" [loa:codigo \""+codLocalizador+"\"; rdfs:label ?"+ClassifierType.SUBTITULO.getId()+"];"+
				"loa:valorProjetoLei ?ploa ;"+
				"loa:valorDotacaoInicial ?loa ;"+
				"loa:valorLeiMaisCredito ?atual ;"+
				"loa:valorEmpenhado ?emp ;"+
				"loa:valorLiquidado ?liq ;"+
				"loa:valorPago ?pago "+
				"} GROUP BY ?"+ClassifierType.FUNCAO.getId()+" ?"+ClassifierType.SUBFUNCAO.getId()+
				" ?"+ClassifierType.UO.getId()+" ?"+ClassifierType.PROGRAMA.getId()+
				" ?"+ClassifierType.ACAO.getId()+" ?"+ClassifierType.SUBTITULO.getId();


		return query;
	}

}
