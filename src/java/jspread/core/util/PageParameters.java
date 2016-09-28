package jspread.core.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author VIOX
 */
public final class PageParameters {

    private static PageParameters si;
    private static ConcurrentHashMap PagesParameters = new ConcurrentHashMap();
    private final static String version = "V.1";

    public static PageParameters getSingleInstance() {
        
        if (null == si) {
            si = new PageParameters();
        }
        return si;
    }

    private PageParameters() {
    }

    public static String getVersion() {
        return version;
    }

    public static void addParameter(String id, Object parameter) {
        PagesParameters.put(id, parameter);
    }

    public static String getParameter(String id) {
        String valor=PagesParameters.get(id).toString();
        return valor;
    }
    
    public static Object getOParameter(String id) {
        return PagesParameters.get(id);
    }

    public static void clearParameters() {
        PagesParameters = null;
    }
}
