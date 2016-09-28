package jspread.core.util;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author YOOO
 */
public final class UserUtil {

    private static final String version = "V0.7";

    public static boolean isAValidUser(LinkedList<String> listOfValidUserGroup, LinkedList<String> userAccess) {
        boolean valid = false;
        if (userAccess != null) {
            //LinkedList<String> userAccess = (LinkedList<String>) PageParameters.getPatameter("roles");
            Iterator<String> it = userAccess.iterator();
            b:
            while (it.hasNext()) {
                if (listOfValidUserGroup.contains(it.next())) {
                    return true;
                }
            }
        }
        return valid;
    }
}
