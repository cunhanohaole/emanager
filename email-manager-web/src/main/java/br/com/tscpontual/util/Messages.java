package br.com.tscpontual.util;

public interface Messages {

	static String CONFIGURATION_EXCEPTION = "Erro de configuracao...";

	static String CONFIG_FILE_NOT_FOUND = "Arquivo de configuracao nao encontrado no caminho: '"
			+ Configurations.FILE_PATH + "'";

	static String CONFIG_FILE_LOAD_ERROR = "Erro ao carregar o arquivo de configuracao '"
			+ Configurations.FILE_NAME
			+ "' no caminho '"
			+ Configurations.FILE_PATH + "'";
	
	String ERROR_WHILE_SENDING_TO_SOMEONE = "Ocorreu um erro ao enviar seu email para algum destinatario!";

}
