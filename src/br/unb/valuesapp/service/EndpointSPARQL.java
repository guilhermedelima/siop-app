package br.unb.valuesapp.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class EndpointSPARQL {
	
	private static final String ENDPOINT_URL = "http://orcamento.dados.gov.br/sparql/";
	private static final int HTTP_OK = 200;
	public static final String REQUEST_QUERY = "query";
	public static final String REQUEST_FORMAT = "format";
	private static final String SPARQL_FORMAT = "application/sparql-results+json";
	
	private HttpClient client;
	
	public EndpointSPARQL(){ 
		this.client = new DefaultHttpClient();  
	}
	
	public String execSPARQLQuery(String query){
		
		HttpPost request;
		HttpResponse response;
		HttpEntity responseEntity;
		List<NameValuePair> parameters;
		String responseText;
		int responseStatus;
		
		parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair(REQUEST_QUERY, query));
		parameters.add(new BasicNameValuePair(REQUEST_FORMAT, SPARQL_FORMAT));
		
		try{
			request = new HttpPost(ENDPOINT_URL);
			request.setEntity(new UrlEncodedFormEntity(parameters));
			
			response = client.execute(request);
			responseEntity = response.getEntity();
			responseStatus = response.getStatusLine().getStatusCode();
			
			responseText = (responseStatus==HTTP_OK)? EntityUtils.toString(responseEntity) : null;
			
		}catch(Exception e){
			e.printStackTrace();
			responseText = null;
		}
		
		return responseText;
	}

}
