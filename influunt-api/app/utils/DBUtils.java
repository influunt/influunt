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
     * do banco de dados
     *
     * @param function - Runnable com o trecho de código a ser executado
     * @return <code> True </code>
     */
    public static boolean executeWithTransaction(Runnable function) {
        Ebean.beginTransaction();
        try {
            function.run();
            Ebean.commitTransaction();
            return true;
        } catch (Exception exc) {
            Ebean.rollbackTransaction();
            Logger.error(exc.getMessage(), exc);
            return false;
        } finally {
            Ebean.endTransaction();
        }
    }
}
