package nexus.modelo;

import java.util.Objects;

/*
 * RN01 - Cliente da NexusCloud.
 * Classe simples (POJO) que só guarda dados. O CPF é o que define quem é o
 * cliente dentro do sistema. (RA 25240314-2)
 */
public class Cliente {

    /* Atributos encapsulados: acesso só pelos métodos públicos. (RA 25240314-2) */
    private final String cpf;
    private final String nome;

    /* Construtor: recebe CPF e nome; os campos são final porque não mudam
       depois que o cliente é criado. (RA 25240314-2) */
    public Cliente(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    /* Leitura do CPF. (RA 25240314-2) */
    public String getCpf() {
        return cpf;
    }

    /* Leitura do nome. (RA 25240314-2) */
    public String getNome() {
        return nome;
    }

    /* equals sobrescrito: por padrão o Java compara referências (endereço
       de memória). A regra de negócio diz que o que importa é o CPF, então
       verificamos o tipo do objeto e comparamos só esse campo. Objects.equals
       já trata o caso de algum CPF ser null. (RA 25240314-2) */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        Cliente c = (Cliente) o;
        return Objects.equals(this.cpf, c.cpf);
    }

    /* hashCode alinhado com o equals: mesmo CPF gera o mesmo número, o que é
       necessário para o Cliente ser chave do HashMap. (RA 25240314-2) */
    @Override
    public int hashCode() {
        return Objects.hashCode(cpf);
    }

    /* Formato de exibição do cliente. (RA 25240314-2) */
    @Override
    public String toString() {
        return "[Cliente] " + nome + " | CPF: " + cpf;
    }
}
