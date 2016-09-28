package jspread.core.util;

import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpSession;

/**
 *
 * @author v10x
 */
public final class SessionUtil {

    private static SessionUtil si;
    private static ConcurrentHashMap<String, HttpSession> sessions = new ConcurrentHashMap();
    private final static String version = "V.1";

    private SessionUtil() {
        //SessionManager.getSingleInstance();
    }

    public static SessionUtil getSingleInstance() {
        // si will be null the first time this is called.
        //hay que generar el constructor especializado
        if (null == si) {
            si = new SessionUtil();
        }
        return si;
    }

    public static String getVersion() {
        return version;
    }

    public static void addSession(String id, HttpSession session) {
        sessions.put(id, session);
    }

    public static void addSession(HttpSession session) {
        sessions.put(session.getId(), session);
    }

    public static HttpSession getSession(String id) {
        return sessions.get(id);
    }

    public static void clearSessions() {
        sessions = null;
    }

    public static void viewSessions() {
        Collection<?> keys = sessions.keySet();
        for (Object key : keys) {
            System.out.println("Key " + key);
            System.out.println("Value " + sessions.get(key.toString()));
        }
    }

    public static ConcurrentHashMap<String, HttpSession> getAllSessions() {
        return sessions;
    }

    public static void destroyAllSessions() throws Exception {
        Collection<?> keys = sessions.keySet();
        for (Object key : keys) {
            HttpSession tempSession = sessions.get(key.toString());
            SessionUtil.clearNCloseSession(tempSession);
        }
    }

    public static void destroyAllSessions(String idException) throws Exception {
        Collection<?> keys = sessions.keySet();
        for (Object key : keys) {
            if (!key.toString().equalsIgnoreCase(idException)) {
                HttpSession tempSession = sessions.get(key.toString());
                SessionUtil.clearNCloseSession(tempSession);
            }
        }
    }

    public static int destroyAllSessionsIdleTime(String idException, int min) throws Exception {
        int sesionesCerradas=0;
        Collection<?> keys = sessions.keySet();
        for (Object key : keys) {
            if (!key.toString().equalsIgnoreCase(idException)) {
                HttpSession tempSession = sessions.get(key.toString());
                if (UTime.getDiferenciaEnMinutos(Calendar.getInstance().getTimeInMillis(), tempSession.getLastAccessedTime()) >= min) {
                    SessionUtil.clearNCloseSession(tempSession);
                    sesionesCerradas+=1;
                }
            }
        }
        return sesionesCerradas;
    }

    public static void clearNCloseSession(HttpSession session) throws Exception {
        String id = session.getId();
        String param = "";
        try {
            if (sessions.containsKey(session.getId())) {
                sessions.remove(id);
            }
        } catch (Exception ex) {
        }
        try {

            Enumeration enu = session.getAttributeNames();
            while (enu.hasMoreElements()) {
                param = enu.nextElement().toString();
                session.setAttribute(param, null);
                session.removeAttribute(param);
            }
        } catch (Exception ex) {
        }
        try {
            session.invalidate();
        } catch (Exception ex) {
            throw new UnsupportedOperationException("Error al cerrar una sesión metodo=session.invalidate()");
        }
        session = null;
    }

    public static void clearNCloseSession(String id) throws Exception {
        SessionUtil.clearNCloseSession(sessions.get(id));
    }

    public static void addIfNotExistSession(HttpSession session) {
        if (session != null) {
            if (!sessions.containsKey(session.getId())) {
                SessionUtil.addSession(session);
            }
        }
    }
}
