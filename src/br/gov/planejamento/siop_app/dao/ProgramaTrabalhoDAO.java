package br.gov.planejamento.siop_app.dao;

import java.util.List;

import android.util.Log;
import br.gov.planejamento.siop_app.model.ClassifierType;
import br.gov.planejamento.siop_app.model.Item;
import br.gov.planejamento.siop_app.service.EndpointSPARQL;
import br.gov.planejamento.siop_app.util.json.JsonParser;
import br.gov.planejamento.siop_app.util.json.JsonValuesParser;

public class ProgramaTrabalhoDAO {
	
	public ProgramaTrabalhoDAO(){
		
	}
	
	private String buildQuery(int exercicio, String unidade, String pt){
		String codFuncao, codSubfuncao, codUnidade, codPrograma, codAcao;
		
		String[] ptSplit = pt.split("\\.");
		
		codFuncao = ptSplit[0];
		codSubfuncao = ptSplit[1];
		codUnidade = unidade;
		codPrograma = ptSplit[2];
		codAcao = ptSplit[3];
		
		String query = "SELECT "+
				"?"+ClassifierType.FUNCAO.getId()+" "+
				"?"+ClassifierType.SUBFUNCAO.getId()+" "+
				"?"+ClassifierType.UO.getId()+" "+
				"?"+ClassifierType.PROGRAMA.getId()+""+
				"?"+ClassifierType.ACAO.getId()+""+
				"(SUM(?ploa) AS ?"+Item.ValuesType.PLOA+")"+
				"(SUM(?loa) AS ?"+Item.ValuesType.LOA+")"+
				"(SUM(?atual) AS ?"+Item.ValuesType.LEI_MAIS_CREDITOS+")"+
				"(SUM(?emp) AS ?"+Item.ValuesType.EMPENHADO+")"+
				"(SUM(?liq) AS ?"+Item.ValuesType.LIQUIDADO+")"+
				"(SUM(?pago) AS ?"+Item.ValuesType.PAGO+")"+
				"WHERE {"+
				"[] loa:temExercicio [loa:identificador "+exercicio+"];"+
				"loa:temFuncao [loa:codigo \""+codFuncao+"\"; rdf:label ?"+ClassifierType.FUNCAO.getId()+"];"+
				"loa:temSubfuncao [loa:codigo \""+codSubfuncao+"\"; rdf:label ?"+ClassifierType.SUBFUNCAO.getId()+"];"+
				"loa:temUnidadeOrcamentaria [loa:codigo \""+codUnidade+"\"; rdf:label ?"+ClassifierType.UO.getId()+"];"+
				"loa:temPrograma [loa:codigo \""+codPrograma+"\"; rdf:label ?"+ClassifierType.PROGRAMA.getId()+"];"+
				"loa:temAcao [loa:codigo \""+codAcao+"\"; rdf:label ?"+ClassifierType.ACAO.getId()+"];"+
				"loa:valorProjetoLei ?ploa ;"+
				"loa:valorDotacaoInicial ?loa ;"+
				"loa:valorLeiMaisCredito ?atual ;"+
				"loa:valorEmpenhado ?emp ;"+
				"loa:valorLiquidado ?liq ;"+
				"loa:valorPago ?pago "+
				"} GROUP BY ?"+ClassifierType.FUNCAO.getId()+" ?"+ClassifierType.SUBFUNCAO.getId()+
				" ?"+ClassifierType.UO.getId()+" ?"+ClassifierType.PROGRAMA.getId()+" ?"+ClassifierType.ACAO.getId();
			
		
		return query;
	}

	public Item getProgramaTrabalho(int exercicio, String unidade, String pt) {
		String query, response;
		EndpointSPARQL endpoint;
		JsonParser<Double> parser;
		
		query = buildQuery(exercicio, unidade, pt);
		endpoint = new EndpointSPARQL();
		
		response = endpoint.execSPARQLQuery(query);
		
		// TODO Implementar parsing
		parser = new JsonValuesParser();
		//return response!=null ? parser.convertJsonToList(response) : null;
		return null;
	}

}
