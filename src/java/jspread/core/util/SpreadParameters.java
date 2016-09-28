/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jspread.core.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author emmanuel
 */
public class SpreadParameters {

    private static SpreadParameters si;
    private static ConcurrentHashMap ISParameters = new ConcurrentHashMap();
    private final static String version = "V.1";

    public static SpreadParameters getSingleInstance() {
        // si will be null the first time this is called.
        //hay que generar el constructor especializado
        if (null == si) {
            si = new SpreadParameters();
        }
        return si;
    }

    private SpreadParameters() {
    }

    public static String getVersion() {
        return version;
    }

    public static void addParameter(String id, Object parameter) {
        ISParameters.put(id, parameter);
    }

    public static Object getPatameter(String id) {
        return ISParameters.get(id);
    }

    public static void clearParameters() {
        ISParameters = null;
    }

}
