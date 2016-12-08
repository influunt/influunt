package status;

/**
 * Created by rodrigosol on 12/8/16.
 */
public enum StatusPacoteTransacao {
    //Nova Transaçao
    NEW,
    //Transacao completou
    DONE,
    //Transacao abortou
    ABORTED,

    //Pendente ação do usuario
    PENDING,

    //Usuario solicitou continuar
    CONTINUE,

    // Usuario solicitou cancelar
    CANCEL,

    //Cancelada pelo usuário
    CANCELED
}
