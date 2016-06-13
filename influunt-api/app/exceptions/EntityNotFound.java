package exceptions;

public class EntityNotFound extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -7110098594699986939L;
    
    public EntityNotFound(String msg) {
        super(msg);
    }
}
