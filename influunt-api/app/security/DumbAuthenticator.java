package security;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import be.objectify.deadbolt.java.models.Subject;
import models.Usuario;


public class DumbAuthenticator implements Authenticator{

	private Map<String,UserSession> sessions = new HashMap<String,UserSession>();
	
	@Override
	public Subject getSubjectByCredentials(String user, String password) {
		if("admin".equals(user) && "1234".equals(password)){
			Usuario u = new Usuario();
			u.setLogin("admin");
			u.setNome("Administrator");
			return u;
		}
		return null;
	}

	@Override
	public Subject getSubjectByToken(String token) {

		if("1234".equals(token)){
			Usuario u = new Usuario();
			u.setLogin("admin");
			u.setNome("Administrator");
			return u;
		}
		return null;
	}

	@Override
	public String createSession(Subject subject) {
		UserSession newSession = new UserSession(subject);
		sessions.put(newSession.getToken(),newSession);
		return newSession.getToken();
	}

	@Override
	public void destroySession(Subject subject) {
		sessions.entrySet().removeIf(entry -> entry.getValue().getSubject().equals(subject));
	}

	@Override
	public void destroySession(String token) {
		sessions.entrySet().removeIf(entry -> entry.getKey().equals(token));
	}

	@Override
	public Collection<UserSession> listSessions() {
		return sessions.values();
	}

	@Override
	public Collection<UserSession> listSessions(Subject subject) {
		return sessions.values()
				       .stream()
				       .filter(entry -> entry.getSubject().equals(subject))
				       .collect(Collectors.toList());
	}

}
