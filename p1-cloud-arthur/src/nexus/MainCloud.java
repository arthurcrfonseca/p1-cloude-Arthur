package nexus;

import nexus.erros.CapacidadeExcedidaException;
import nexus.modelo.Cliente;
import nexus.modelo.Container;
import nexus.modelo.Servidor;
import nexus.nucleo.CloudManager;

/*
 * Roteiro de testes da atividade (passos 1 a 10). O programa precisa rodar
 * até o fim sem quebrar. (RA 25240314-2)
 */
public class MainCloud {

    public static void main(String[] args) {

        /* Passo 1 - gerenciador principal. (RA 25240314-2) */
        CloudManager manager = new CloudManager();

        /* Passo 2 - cliente do teste. (RA 25240314-2) */
        Cliente wayne = new Cliente("111.222.333-44", "Wayne Enterprises");

        /* Passo 3 - servidor de 16 GB a R$ 5,50/h; começa desligado. (RA 25240314-2) */
        Servidor servidor = new Servidor("192.168.0.1", 16.0, 5.50);

        /* Passo 4 - registra o cliente e entrega o servidor para ele. (RA 25240314-2) */
        manager.registrarCliente(wayne);
        manager.provisionarServidor(wayne, servidor);
        System.out.println("--- Situação inicial ---");
        System.out.println(wayne);
        System.out.println(servidor);

        /* Passo 5 - dois containers: 10 GB e 8 GB. (RA 25240314-2) */
        Container container1 = new Container("C-01", "Loja Online", 10.0);
        Container container2 = new Container("C-02", "Motor de Busca", 8.0);

        /* Os passos 6 a 8 ficam dentro de um try porque alocarContainer declara
           a exceção CHECKED e o compilador exige o tratamento. Esse catch só
           captura CapacidadeExcedidaException, então a ServidorOfflineException
           (unchecked) passa direto por ele. (RA 25240314-2) */
        try {
            /* Passo 6 - falha unchecked: com o servidor desligado, esta linha
               estoura a ServidorOfflineException em vermelho no console. Foi
               testada e depois comentada (passo 7) para o roteiro seguir. (RA 25240314-2) */
            // servidor.alocarContainer(container1);

            /* Passo 7 - liga o servidor. (RA 25240314-2) */
            servidor.ligar();
            System.out.println("\n--- Servidor ligado ---");

            /* Passo 8 - alocação válida: 10 GB cabem nos 16 GB. (RA 25240314-2) */
            servidor.alocarContainer(container1);
            System.out.println("OK: " + container1 + " alocado.");
        } catch (CapacidadeExcedidaException e) {
            System.out.println("Falha inesperada: " + e.getMessage());
        }
        System.out.println(servidor);

        /* Passo 9 - falha checked: 10 + 8 = 18 GB, acima do limite de 16 GB.
           O catch captura a CapacidadeExcedidaException e mostra uma mensagem
           organizada, e o programa segue normalmente. (RA 25240314-2) */
        System.out.println("\n--- Tentando alocar " + container2.getId() + " ---");
        try {
            servidor.alocarContainer(container2);
            System.out.println("OK: " + container2 + " alocado.");
        } catch (CapacidadeExcedidaException e) {
            System.out.println("+-----------------------------------------------------------------+");
            System.out.println("| " + e.getMessage());
            System.out.println("| Pedido: " + container2.getRamNecessaria() + " GB | Em uso: "
                    + servidor.calcularRamEmUso() + " GB | Total: " + servidor.getRamTotal() + " GB");
            System.out.println("+-----------------------------------------------------------------+");
        }

        /* Passo 10 - fatura ativa; resultado esperado: 5.5 (só o servidor
           ligado é cobrado). (RA 25240314-2) */
        System.out.println("\n--- Fatura ---");
        System.out.println("Total por hora de " + wayne.getNome() + ": R$ " + manager.calcularFaturaAtiva(wayne));
    }
}
