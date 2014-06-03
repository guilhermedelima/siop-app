package br.gov.planejamento.siop_app.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import br.gov.planejamento.siop_app.model.Classifier;
import br.gov.planejamento.siop_app.model.ClassifierType;
import br.gov.planejamento.siop_app.model.Item;
import br.gov.planejamento.siop_app.service.EndpointSPARQL;
import br.gov.planejamento.siop_app.util.json.JsonParser;
import br.gov.planejamento.siop_app.util.json.JsonProgramaTrabalhoParser;

public class ProgramaTrabalhoDAO {

	public ProgramaTrabalhoDAO(){
	}

	public Item getProgramaTrabalho(int exercicio, String unidade, String pt) {
		String query, response;
		EndpointSPARQL endpoint;
		JsonParser<HashMap<String, Object>> parser;
		HashMap<String, Object> values;
		String codFuncao, codSubfuncao, codUnidade, codPrograma, codAcao;
		Item item;

		String[] ptSplit = pt.split("\\.");
		codFuncao = ptSplit[0];
		codSubfuncao = ptSplit[1];
		codUnidade = unidade;
		codPrograma = ptSplit[2];
		codAcao = ptSplit[3];

		endpoint = new EndpointSPARQL();
		parser = new JsonProgramaTrabalhoParser();

		query = buildQuery(exercicio, codFuncao, codSubfuncao, codUnidade, codPrograma, codAcao);
		response = endpoint.execSPARQLQuery(query);
		values = parser.convertJsonToObject(response);

		item = values!=null && !values.isEmpty() ? convertMapToItem(values, exercicio, codFuncao, 
				codSubfuncao, codUnidade, codPrograma, codAcao) : null; 

		return item;
	}


	private Item convertMapToItem(HashMap<String, Object> values, int exercicio, 
			String codFuncao, String codSubfuncao, String codUnidade, String codPrograma, String codAcao){
		
		List<Classifier> classifiers;
		String cod, label;
		ClassifierType type;
		Double value;
		Item item;

		classifiers = new ArrayList<Classifier>();
		item = new Item();
		
		for(Entry<String, Object> entry : values.entrySet()){
			
			cod = null;
			type = null;
			
			if(entry.getKey().equals(ClassifierType.FUNCAO.getId())){
				cod = codFuncao;
				type = ClassifierType.FUNCAO;

			}else if(entry.getKey().equals(ClassifierType.SUBFUNCAO.getId())){
				cod = codSubfuncao;
				type = ClassifierType.SUBFUNCAO;

			}else if(entry.getKey().equals(ClassifierType.UO.getId())){
				cod = codUnidade;
				type = ClassifierType.UO;

			}else if(entry.getKey().equals(ClassifierType.PROGRAMA.getId())){
				cod = codPrograma;
				type = ClassifierType.PROGRAMA;

			}else if(entry.getKey().equals(ClassifierType.ACAO.getId())){
				cod = codAcao;
				type = ClassifierType.ACAO;

			}else{
				value = Double.valueOf((String)entry.getValue()); 
				
				if(entry.getKey().equals(Item.ValuesType.PLOA.toString())){
					item.setValueProjetoLei( value );

				}else if(entry.getKey().equals(Item.ValuesType.LOA.toString())){
					item.setValueDotacaoInicial( value );
	
				}else if(entry.getKey().equals(Item.ValuesType.LEI_MAIS_CREDITOS.toString())){
					item.setValueDotacaoInicial( value );
	
				}else if(entry.getKey().equals(Item.ValuesType.EMPENHADO.toString())){
					item.setValueEmpenhado( value );
	
				}else if(entry.getKey().equals(Item.ValuesType.LIQUIDADO.toString())){
					item.setValueLiquidado( value );
	
				}else if(entry.getKey().equals(Item.ValuesType.PAGO.toString())){
					item.setValuePago( value );
				}
			}
			
			if( cod != null && type != null){
				label = (String)entry.getValue();
				classifiers.add(new Classifier(label, cod, exercicio, type));
			}
		}

		item.setClassifierList(classifiers);
		item.setYear(exercicio);

		return item;
	}


	private String buildQuery(int exercicio, String codFuncao, String codSubfuncao, String codUnidade, String codPrograma, String codAcao){

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

}
