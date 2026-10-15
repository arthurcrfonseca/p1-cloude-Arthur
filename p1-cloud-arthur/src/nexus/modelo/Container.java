package nexus.modelo;

import java.util.Objects;

/*
 * RN01 - Container (aplicação) hospedado em um servidor.
 * O ID é o que identifica o container. (RA 25240314-2)
 */
public class Container {

    /* Atributos encapsulados e imutáveis. (RA 25240314-2) */
    private final String id;
    private final String nomeApp;
    private final double ramNecessaria;

    /* Construtor com ID, nome da aplicação e RAM em GB. (RA 25240314-2) */
    public Container(String id, String nomeApp, double ramNecessaria) {
        this.id = id;
        this.nomeApp = nomeApp;
        this.ramNecessaria = ramNecessaria;
    }

    /* Leitura do ID. (RA 25240314-2) */
    public String getId() {
        return id;
    }

    /* Leitura do nome da aplicação. (RA 25240314-2) */
    public String getNomeApp() {
        return nomeApp;
    }

    /* Leitura da RAM exigida; é o valor somado no Stream do Servidor. (RA 25240314-2) */
    public double getRamNecessaria() {
        return ramNecessaria;
    }

    /* Dois containers são o mesmo quando têm o mesmo ID. (RA 25240314-2) */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Container)) {
            return false;
        }
        Container outro = (Container) o;
        return Objects.equals(this.id, outro.id);
    }

    /* Hash calculado a partir do ID, coerente com o equals. (RA 25240314-2) */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /* Formato de exibição do container. (RA 25240314-2) */
    @Override
    public String toString() {
        return "[Container " + id + "] " + nomeApp + " - " + ramNecessaria + " GB";
    }
}
