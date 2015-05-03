package com.chz.smartoa.common.listerner;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
	private static int sessionCount = 0;
	private static Map sessionMaps = new ConcurrentHashMap();
	
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession httpSession = event.getSession();
		String sessionId = httpSession.getId();
		sessionMaps.put(sessionId, httpSession);
		sessionCount ++;
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession httpSession = event.getSession();
		String sessionId = httpSession.getId();
		sessionMaps.remove(sessionId);
		sessionCount --;
	}

	public static HttpSession getSession(String sessionId) {
		HttpSession httpSession = (HttpSession)sessionMaps.get(sessionId);
		return httpSession;
	}
}
