package utils;

import com.avaje.ebean.Ebean;
import play.Logger;

/**
 * Database helper
 * Created by lesiopinheiro on 8/19/16.
 */
public class DBUtils {

    /**
     * Executa um bloco de código dentro de uma transação
     *
     * @param function - trecho de código a ser executado
     */
    public static void executeWithTransaction(Runnable function) {
        Ebean.beginTransaction();
        try {
            function.run();
            Ebean.commitTransaction();
        } catch (Exception exc) {
            Ebean.rollbackTransaction();
            Logger.error(exc.getMessage(), exc);
        } finally {
            Ebean.endTransaction();
        }
    }
}
