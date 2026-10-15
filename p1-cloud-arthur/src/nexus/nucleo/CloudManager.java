package nexus.nucleo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import nexus.modelo.Cliente;
import nexus.modelo.Servidor;

/*
 * Gerenciador central da NexusCloud (RN02 e RN04).
 * Mantém, em memória, quais servidores pertencem a cada cliente. (RA 25240314-2)
 */
public class CloudManager {

    /* RN02: declarado como a interface Map e criado como HashMap, que acha
       as chaves pelo hashCode (rápido). A chave é o Cliente e o valor é um
       Set<Servidor>, que não guarda repetidos nem segue ordem. (RA 25240314-2) */
    private final Map<Cliente, Set<Servidor>> bancoDeDados;

    /* Construtor: cria o HashMap vazio. (RA 25240314-2) */
    public CloudManager() {
        this.bancoDeDados = new HashMap<>();
    }

    /* Adiciona o cliente ao banco, com um HashSet vazio para os servidores.
       Se ele já existir, não faz nada (não sobrescreve). (RA 25240314-2) */
    public void registrarCliente(Cliente c) {
        if (!bancoDeDados.containsKey(c)) {
            bancoDeDados.put(c, new HashSet<>());
        }
    }

    /* Liga um servidor a um cliente. Garante que o cliente está registrado e
       depois coloca o servidor no Set dele; um IP repetido é ignorado pelo
       próprio Set. (RA 25240314-2) */
    public void provisionarServidor(Cliente c, Servidor s) {
        registrarCliente(c);
        bancoDeDados.get(c).add(s);
    }

    /* RN04 - Fatura ativa via Streams, sem for nem if:
       - Optional.ofNullable(...).orElse(...) troca um possível null (cliente
         sem cadastro) por um Set vazio;
       - stream() abre o fluxo dos servidores do cliente;
       - filter(Servidor::isStatusLigado) deixa passar só os ligados;
       - mapToDouble(Servidor::getPrecoPorHora) pega o preço de cada um;
       - sum() soma e devolve o valor da fatura. (RA 25240314-2) */
    public double calcularFaturaAtiva(Cliente c) {
        return Optional.ofNullable(bancoDeDados.get(c))
                .orElse(Set.of())
                .stream()
                .filter(Servidor::isStatusLigado)
                .mapToDouble(Servidor::getPrecoPorHora)
                .sum();
    }
}
