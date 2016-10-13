package jspread.core.models;


import java.util.LinkedList;

/**
 *
 * @author desarrollowe
 */
public class Transporter {

    private int code;
    private String msg;
    private String observaciones;
    private LinkedList objectList;

    public Transporter(int cod, String msg) {
        this.code = cod;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int cod) {
        this.code = cod;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LinkedList getObjectList() {
        return objectList;
    }

    public void setObjectList(LinkedList objectList) {
        this.objectList = objectList;
    }
}