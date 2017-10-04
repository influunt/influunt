package protocol;

/**
 * Created by rodrigosol on 9/14/16.
 */
public enum EtapaTransacao {
    //Pedido de uma nova transacao
    //App --> Central
    NEW("NOVA", "Aplicação solicitou a central uma alteração no controlador"),

    //Informa o dispositivo que ele deve se preparar para uma transacao
    //Central --> Device
    PREPARE_TO_COMMIT("PREPARAR", "Central solicitou que o controlador se prepare para uma alteração"),

    //Informa o dispositivo que deve aborta a transacao
    //Central --> Device
    ABORT("ABORTAR", "Central solicitou que o controlador aborte o último pedido de alteração"),

    //Informa o dispositivo que e para efetivar a transacao
    //Central --> Device
    COMMIT("CONFIRMAR", "Central solicitou que o controlador confirme a última alteração"),

    //Informa o usuário que a transacao terminou com sucesso
    //Central --> App
    COMPLETED("COMPLETADO", "Central avisou o usuário que a alteração terminou com sucesso"),

    //Informa a central que o que dispositivo abortou
    //Device --> Central
    ABORTED("ABORTADO", "O controlador avisou a central que a alteração foi abortada"),

    //Informa a central que o dispositivo esta pronto
    //Device --> Central
    PREPARE_OK("PRONTO", "O controlador avisou a central que  está pronto para realizar a alteração solicitada"),

    //Informa a central que o dispositivo nao pode realizar a transacao nesse momento
    //Device --> Central
    PREPARE_FAIL("NAO_ESTA_PRONTO", "O controlador avisou a central que não está pronto para realizar a alteração solicitada"),

    //Informa o central que a transacao foi commitada
    //Device --> Central
    COMMITED("FEITO", "O controlador avisou a central que realizou a alteração solicitada");

    private final String mnemonico;

    private final String descricao;

    EtapaTransacao(String mnemonico, String descricao) {
        this.mnemonico = mnemonico;
        this.descricao = descricao;
    }

    public String getMessage() {
        return String.format("[%s] %s", mnemonico, descricao);
    }

}
