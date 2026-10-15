package nexus.erros;

/*
 * RN03 - Exceção de memória insuficiente.
 * Herda de Exception (e não de RuntimeException), então é CHECKED: o
 * compilador só aceita a chamada se ela estiver num try-catch ou se o
 * método declarar "throws". (RA 25240314-2)
 */
public class CapacidadeExcedidaException extends Exception {

    /* Repassa a mensagem para Exception. (RA 25240314-2) */
    public CapacidadeExcedidaException(String msg) {
        super(msg);
    }
}
