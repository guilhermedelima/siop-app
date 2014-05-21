package br.unb.valuesapp.data;

import java.util.ArrayList;
import java.util.List;

import br.unb.valuesapp.service.EndpointSPARQL;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class ValuesDAO {
	

	private static final String PLOA_SPARQL = "ploa";
	private static final String LOA_SPARQL = "loa";
	private static final String LEI_MAIS_CREDITO_SPARQL = "lei_mais_credito";
	private static final String EMPENHADO_SPARQL = "empenhado";
	private static final String LIQUIDADO_SPARQL = "liquidado";
	private static final String PAGO_SPARQL = "pago";
	
	public ValuesDAO(){
		
	}
	
	public List<Double> getValues(int year){
		
		ResultSet result;
		String query;
		EndpointSPARQL endpoint;
		
		query = buildQuery(year);
		endpoint = new EndpointSPARQL();
		
		result = endpoint.execSPARQLQuery(query);
		
		return result!=null ? convertResultQuery(result) : null;
	}
	
	private String buildQuery(int year){
		
		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
				"PREFIX loa: <http://vocab.e.gov.br/2013/09/loa#>" +
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
                "SELECT sum ( ?val1 ) as ?"+PLOA_SPARQL+" sum ( ?val2 ) as ?"+LOA_SPARQL+" sum(?val3) as ?"+LEI_MAIS_CREDITO_SPARQL+" sum(?val4) as ?"+EMPENHADO_SPARQL+" sum(?val5) as ?"+LIQUIDADO_SPARQL+" sum(?val6) as ?"+PAGO_SPARQL+" WHERE {" +
                "  ?i loa:temExercicio [loa:identificador "+year+"] ."+
                "  ?i loa:valorProjetoLei ?val1 ." +
                "  ?i loa:valorDotacaoInicial ?val2 ." +
                "  ?i loa:valorLeiMaisCredito ?val3 ." +
                "  ?i loa:valorEmpenhado ?val4 ." +
                "  ?i loa:valorLiquidado ?val5 ." +
                "  ?i loa:valorPago ?val6 ." +
                "}";

		System.out.println(query);
		
		return query;
	}
	
	private List<Double> convertResultQuery(ResultSet result){
		
		List<Double> values;
		
		values = new ArrayList<Double>();
		
		while(result.hasNext()){
			
			QuerySolution qsol;
			RDFNode ploaNode, loaNode, leiMaisCreditoNode, empenhadoNode, liquidadoNode, pagoNode;
			double ploa, loa, leiMaisCredito, empenhado, liquidado, pago;
			
			qsol = result.next();
			
			ploaNode = qsol.get(PLOA_SPARQL);
			loaNode = qsol.get(LOA_SPARQL);
			leiMaisCreditoNode = qsol.get(LEI_MAIS_CREDITO_SPARQL);
			empenhadoNode = qsol.get(EMPENHADO_SPARQL);
			liquidadoNode = qsol.get(LIQUIDADO_SPARQL);
			pagoNode = qsol.get(PAGO_SPARQL);

			ploa = Double.parseDouble(((Literal)ploaNode).getLexicalForm());
			loa = Double.parseDouble(((Literal)loaNode).getLexicalForm());
			leiMaisCredito = Double.parseDouble(((Literal)leiMaisCreditoNode).getLexicalForm());
			empenhado = Double.parseDouble(((Literal)empenhadoNode).getLexicalForm());
			liquidado = Double.parseDouble(((Literal)liquidadoNode).getLexicalForm());
			pago = Double.parseDouble(((Literal)pagoNode).getLexicalForm());
			
			values.add(ploa);
			values.add(loa);
			values.add(leiMaisCredito);
			values.add(empenhado);
			values.add(liquidado);
			values.add(pago);
		}
		
		return values;
	}

}
