package security;

import java.util.Collection;

import be.objectify.deadbolt.java.models.Subject;


public interface Authenticator {
	/**
	 * Verifica se as credenciais informadas são validas
	 * @param login 
	 * @param password
	 * @return retorna o usuário caso as crendenciais sejam verdadeiras ou NULL caso contrário
	 */
	public Subject getSubjectByCredentials(String login, String password);
	/**
	 * Retorna um usuário pelo token de acesso
	 * @param token
	 * @return O usuário caso o token sejam válido
	 */
	public Subject getSubjectByToken(String token);
	
	/**
	 * Cria um novo token de acesso para o usuário
	 * @param subject
	 * @return token de acesso
	 */
	public String createSession(Subject subject);
	
	/**
	 * Finaliza todas as sessoes do usuario informado 
	 * @param subject
	 */
	public void destroySession(Subject subject);
	
	/**
	 * Finaliza a seção especificada pelo token
	 * @param token
	 */
	public void destroySession(String token);
	
	public Collection<UserSession> listSessions();
	
	public Collection<UserSession> listSessions(Subject subject);
}
