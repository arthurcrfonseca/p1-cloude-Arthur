# NexusCloud Manager

Arthur — RA 25240314-2  
Programação Orientada a Objetos · UniCesumar · Atividade em sala (15/10)

Sistema de orquestração de servidores e containers da NexusCloud, feito em Java puro (VS Code, sem build tools).

## Pacotes

| Pacote | Conteúdo |
|---|---|
| `nexus.modelo` | `Cliente`, `Servidor`, `Container` |
| `nexus.erros` | `ServidorOfflineException`, `CapacidadeExcedidaException` |
| `nexus.nucleo` | `CloudManager` |
| `nexus` | `MainCloud` (roteiro de testes) |

## O que cada regra virou no código

1. **RN01 (identidade):** `equals`/`hashCode` comparam o CPF, o IP e o ID com `Objects.equals` e `Objects.hashCode`.
2. **RN02 (coleções):** os containers ficam num `ArrayList` criado na declaração, e o `bancoDeDados` é um `HashMap<Cliente, Set<Servidor>>`.
3. **RN03 (exceções):** `alocarContainer` confere o status e depois a RAM livre. Só então adiciona o container.
4. **RN04 (Streams):** a RAM em uso é `mapToDouble().sum()`, e a fatura é `filter().mapToDouble().sum()`.

## Rodando

Execute `MainCloud.java` no VS Code. A última linha mostra a fatura: **R$ 5.5** por hora.

Para conferir o erro do passo 6, descomente `servidor.alocarContainer(container1);` e rode de novo. O console mostra a `ServidorOfflineException`.
