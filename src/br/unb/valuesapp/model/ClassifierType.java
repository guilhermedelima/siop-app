package br.unb.valuesapp.model;

public enum ClassifierType {
	ESFERA("Esfera", "temEsfera", "esfera"),
	ORGAO("Órgão", "temOrgao", "orgao"),
	UO("Unidade Orçamentária", "temUnidadeOrcamentaria", "uo"),
	FUNCAO("Função", "temFuncao", "funcao"),
	SUBFUNCAO("Subfunção", "temSubfuncao", "subfuncao"),
	PROGRAMA("Programa", "temPrograma", "programa"),
	ACAO("Ação", "temAcao", "acao"),
	LOCALIZADOR("Localizador", "temLocalizador", "localizador"),
	PO("Plano Orçamentário", "temPlanoOrcamentario", "po"),
	CE("Categoria Econômica", "temCategoriaEconomica", "catEconomica"),
	GND("Grupo de Natureza da Despesa", "temGND", "gnd"),
	MODALIDADE("Modalidade da Aplicação", "temModalidadeAplicacao", "modalidade"),
	ELEMENTO("Elemento de Despesa", "temElementoDespesa", "elemento"),
	FONTE("Fonte de Recursos", "temFonteRecursos", "fonte"),
	IDUSO("Indicador de Uso", "temIndicadorUso", "idUso"),
	RP("Resultado Primário", "temResultadoPrimario", "rp")
	;

	private final String name;
	private final String property;
	private final String id;

	private ClassifierType(final String name, final String property, final String id){
		this.name = name;
		this.property = property;
		this.id = id;
	}

	public String getName(){
		return name;
	}

	public String getProperty(){
		return property;
	}

	public String getId(){
		return id;
	}

	@Override
	public String toString(){
		return id;
	}
}