package protocol;

/**
 * Created by rodrigosol on 9/14/16.
 */
public enum EtapaTransacao {
    //Pedido de uma nova transacao
    //App --> Central
    NEW,

    //Informa o dispositivo que ele deve se preparar para uma transacao
    //Central --> Device
    PREPARE_TO_COMMIT,

    //Informa o dispositivo que deve aborta a transacao
    //Central --> Device
    ABORT,

    //Informa o dispositivo que e para efetivar a transacao
    //Central --> Device
    COMMIT,

    //Informa o usuário que a transacao terminou com sucesso
    //Central --> App
    COMPLETED,

    //Informa a central que o que dispositivo abortou
    //Device --> Central
    ABORTED,

    //Informa a central que o dispositivo esta pronto
    //Device --> Central
    PREPARE_OK,

    //Informa a central que o dispositivo nao pode realizar a transacao nesse momento
    //Device --> Central
    PREPARE_FAIL,

    //Informa o central que a transacao foi commitada
    //Device --> Central
    COMMITED
}
