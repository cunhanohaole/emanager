package br.com.tscpontual.contacts.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import br.com.tscpontual.exception.BusinessException;
import br.com.tscpontual.exception.TechnicalException;
import br.com.tscpontual.util.AppMessages;

/**
 * Classe responsavel por interpretar o arquivo de emails a serem organizados
 * 
 * @author Fernando da Cunha Alves
 * 
 */
public class ImportedFileReader {

	private Map<String, HashSet<String>> groups = new HashMap<String, HashSet<String>>();

	private BufferedReader emailListFile;

	private String groupMarker;

	/**
	 * Construtor que recebe um marcador de grupo e um BufferedReader contendo
	 * as informacoes do arquivo Um marcador de grupo, e uma String utilizada
	 * para informar o interpretador que aquela linha do arquivo de origem
	 * trata-se de um grupo e que as linhas abaixo a ela sao os emails a serem
	 * organizados Exemplo:
	 * 
	 * Suponha que o marcador de grupo seja este: -->
	 * 
	 * No arquivo teriamos:
	 * 
	 * --> Grupo 1
	 * 
	 * exemplo@teste.com.br; exemplo2@teste.com.br exemplo3@teste.com.br
	 * 
	 * --> Grupo 2
	 * 
	 * exemplo@teste.com.br; exemplo2@teste.com.br exemplo3@teste.com.br
	 * 
	 * Com isso, o interpretador ira separar ose emails abaixo do grupo 1 dos
	 * emails abaixo do grupo 2
	 * 
	 * @param groupMarker
	 *            O marcador de grupo
	 * @param emailListFile
	 *            O BuffredReader que contem as informacoes do arquivo de email
	 */
	public ImportedFileReader(String groupMarker, BufferedReader emailListFile) {
		this.groupMarker = groupMarker;
		this.emailListFile = emailListFile;
	}
	
	public ImportedFileReader(MultipartFile file) throws TechnicalException {
		try {
			this.groupMarker = "==>";
			this.emailListFile = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
		} catch (Exception e) {
			throw new TechnicalException("Error while reading the imported file: ", e);
		}
	}

	/**
	 * Realiza a logica de leitura do arquivo, separando os emails em grupos.
	 * 
	 * @return Um Map contendo como chave uma String, que representa o nome do
	 *         grupo e um HashSet com N emails(Foi utilizado o HashSet para
	 *         garantir que um email nao se repita no mesmo grupo).
	 * @throws BusinessException
	 *             No casos em que deve ser exibida uma mensagem amigavel ao
	 *             usuario
	 *         TechnicalException
	 */
	public Map<String, HashSet<String>> getEmailListAsGroupsMap()
			throws TechnicalException, BusinessException {
		HashSet<String> emails = null;
		try {
			int lineCount = 0;
			while (emailListFile.ready()) {
				ImportedFileLine line = new ImportedFileLine(emailListFile.readLine());
				if (line.isValid()) {
					if (lineCount++ == 0) {
						if (!line.isGroup(groupMarker)) {
							throw new BusinessException(
									AppMessages.MARKER_NOT_FOUND_TITLE,
									AppMessages.MARKER_NOT_FOUND_MESSAGE
											+ groupMarker);
						}
					}
					if (line.isGroup(groupMarker)) {
						String groupName = line.getGroupName(groupMarker).trim();
						emails = new HashSet<String>();
						groups.put(groupName, emails);
					} else {
						line.removeUnwantedChars();
						if (line.hasMoreThanOneEmail()) {
							List<ImportedEmailAddress> emailList = line.breakIntoEmails();
							for (ImportedEmailAddress email : emailList) {
								if (email.isValid()) {
									emails.add(email.format());
								}
							}
						} else {
							if (line.isValid()) {
								ImportedEmailAddress email = new ImportedEmailAddress(line.getText());
								if(email.isValid()){
									emails.add(email.format());
								}
							}
						}
					}
				}
			}
			emailListFile.close();
		} catch (IOException e) {
			throw new TechnicalException("Error while reading the imported file: ", e);
		}
		return groups;
	}

}
