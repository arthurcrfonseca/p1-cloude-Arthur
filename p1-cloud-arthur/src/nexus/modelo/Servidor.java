package nexus.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import nexus.erros.CapacidadeExcedidaException;
import nexus.erros.ServidorOfflineException;

/*
 * Servidor virtual da NexusCloud (RN01 a RN04).
 * Identificado pelo IP, guarda os containers em ordem de chegada e controla
 * o consumo de memória. (RA 25240314-2)
 */
public class Servidor {

    /* Dados fixos do servidor. (RA 25240314-2) */
    private final String ip;
    private final double precoPorHora;
    private final double ramTotal;

    /* Estado do servidor: começa desligado e muda com ligar()/desligar(). (RA 25240314-2) */
    private boolean statusLigado;

    /* RN02: ArrayList criado já na declaração, então a lista existe desde o
       nascimento do objeto e nunca causa NullPointerException. A ordem de
       inserção é mantida, formando o histórico cronológico. (RA 25240314-2) */
    private final List<Container> containers = new ArrayList<>();

    /* Construtor (ip, ramTotal, precoPorHora); o servidor sai desligado. (RA 25240314-2) */
    public Servidor(String ip, double ramTotal, double precoPorHora) {
        this.ip = ip;
        this.ramTotal = ramTotal;
        this.precoPorHora = precoPorHora;
        this.statusLigado = false;
    }

    /* Liga o servidor. (RA 25240314-2) */
    public void ligar() {
        this.statusLigado = true;
    }

    /* Desliga o servidor. (RA 25240314-2) */
    public void desligar() {
        this.statusLigado = false;
    }

    /* RN03 - Alocação com Fail-Fast.
       A ideia é falhar o quanto antes: as regras são checadas em sequência e,
       na primeira que falhar, a exceção sai do método e o container não é
       guardado. Só no fim, com tudo validado, o add acontece. O "throws" na
       assinatura é exigido porque a exceção de capacidade é checked. (RA 25240314-2) */
    public void alocarContainer(Container c) throws CapacidadeExcedidaException {

        /* Regra 1 - status: se estiver desligado, para tudo aqui. (RA 25240314-2) */
        if (!this.statusLigado) {
            throw new ServidorOfflineException(
                    "Erro: Não é possível alocar containers em um servidor desligado.");
        }

        /* Regra 2 - capacidade: memória livre = total - em uso. Se o container
           pedir mais do que está livre, a soma estoura o limite. (RA 25240314-2) */
        double ramLivre = this.ramTotal - calcularRamEmUso();
        if (c.getRamNecessaria() > ramLivre) {
            throw new CapacidadeExcedidaException(
                    "Falha Crítica: Memória RAM insuficiente no servidor " + this.ip + ".");
        }

        /* Regras OK: registra o container no histórico. (RA 25240314-2) */
        this.containers.add(c);
    }

    /* RN04 - RAM em uso via Streams.
       stream() abre o fluxo da lista, mapToDouble(Container::getRamNecessaria)
       converte cada container em um double com sua RAM e sum() fecha o
       fluxo somando os valores. Nenhum laço for é necessário. (RA 25240314-2) */
    public double calcularRamEmUso() {
        return this.containers.stream()
                .mapToDouble(Container::getRamNecessaria)
                .sum();
    }

    /* Leitura do IP. (RA 25240314-2) */
    public String getIp() {
        return ip;
    }

    /* Leitura do preço por hora. (RA 25240314-2) */
    public double getPrecoPorHora() {
        return precoPorHora;
    }

    /* Leitura da RAM total. (RA 25240314-2) */
    public double getRamTotal() {
        return ramTotal;
    }

    /* Leitura do status (ligado = true). (RA 25240314-2) */
    public boolean isStatusLigado() {
        return statusLigado;
    }

    /* Lista protegida contra alteração: quem recebe pode ler, mas não pode
       adicionar nem remover itens por fora do alocarContainer. (RA 25240314-2) */
    public List<Container> getContainers() {
        return Collections.unmodifiableList(containers);
    }

    /* Servidores são iguais quando o IP é igual; é isso que evita duplicados
       no Set do CloudManager. (RA 25240314-2) */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Servidor)) {
            return false;
        }
        Servidor outro = (Servidor) o;
        return Objects.equals(this.ip, outro.ip);
    }

    /* Hash do IP, alinhado com o equals. (RA 25240314-2) */
    @Override
    public int hashCode() {
        return Objects.hashCode(ip);
    }

    /* Formato de exibição do servidor. (RA 25240314-2) */
    @Override
    public String toString() {
        String status = statusLigado ? "online" : "offline";
        return "[Servidor " + ip + "] " + status + " | RAM " + calcularRamEmUso() + " de " + ramTotal
                + " GB | R$ " + precoPorHora + "/h";
    }
}
