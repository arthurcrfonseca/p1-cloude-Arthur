package nexus.erros;

/*
 * RN03 - Exceção de servidor desligado.
 * Por herdar de RuntimeException, é UNCHECKED: o Java não obriga ninguém a
 * declarar "throws" nem a usar try-catch. Se ela não for tratada, o
 * programa encerra mostrando o erro. (RA 25240314-2)
 */
public class ServidorOfflineException extends RuntimeException {

    /* Repassa a mensagem para RuntimeException. (RA 25240314-2) */
    public ServidorOfflineException(String msg) {
        super(msg);
    }
}
