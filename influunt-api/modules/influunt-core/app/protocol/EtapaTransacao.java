package protocol;

/**
 * Created by rodrigosol on 9/14/16.
 */
public enum EtapaTransacao {
    //Pedido de uma nova transacao
    //App --> Central
    NEW,
    //Informa o usuário que a transacao terminou com sucesso
    //Central --> App
    COMPLETED,
    //Informa o usuário que a transacao foi abortada
    //Central --> App
    FAILD, //o usuário
    //Informa o dispositivo que ele deve se preparar para uma transacao
    //Central --> Device
    PREPARE_TO_COMMIT,
    //Informa a central que o dispositivo esta pronto
    //Device --> Central
    PREPARE_OK,
    //Informa a central que o dispositivo nao pode realizar a transacao nesse momento
    //Device --> Central
    PREPARE_FAIL,
    //Informa o dispositivo que e para efetivar a transacao
    //Central --> Device
    COMMIT,
    //Informa o central que a transacao foi commitada
    //Dispositvo --> Central
    COMMITED,
    //Informa o dispositivo que deve aborta a transacao
    //Central --> Device
    ABORT,
    //Informa o centak que o que dispositivo abortou
    //Device --> Central
    ABORTED;

}
