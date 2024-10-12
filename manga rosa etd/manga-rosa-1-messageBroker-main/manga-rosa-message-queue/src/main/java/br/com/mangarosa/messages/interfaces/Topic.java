package br.com.mangarosa.messages.interfaces;

import br.com.mangarosa.messages.Message;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/***
 * Tópico é uma estrutura de fila onde as mensagens ficam para serem consumidas
 * pelos consumidores.
 */
public interface Topic extends Serializable {
    /***
     * Retorna o nome do tópico.
     * @return nome do tópico.
     */
    String name();

    /**
     * Adiciona uma mensagem no final da fila do tópico.
     * @param message mensagem a ser processada.
     */
    void addMessage(Message message);

    /**
     * Adiciona um consumidor que receberá mensagens dos produtores.
     * @param consumer consumidor a ser adicionado.
     */
    void subscribe(Consumer consumer);

    /**
     * Remove um consumidor da lista de consumidores do tópico.
     * @param consumer consumidor a ser removido.
     */
    void unsubscribe(Consumer consumer);

    /**
     * Retorna a lista de consumidores atualmente inscritos.
     * @return lista de consumidores.
     */
    List<Consumer> consumers();

    /**
     * Retorna o repositório que conecta com o banco chave-valor.
     * @return repositório de mensagens.
     */
    MessageRepository getRepository();

    /***
     * Notifica todos os consumidores que há uma nova mensagem para ser consumida.
     * @param message mensagem que deve ser notificada.
     */
    default void notifyConsumers(Message message) {
        // Para cada consumidor na lista, cria uma tarefa assíncrona para consumir a mensagem.
        consumers().forEach(consumer -> {
            CompletableFuture<Boolean> completableFuture = CompletableFuture
                    .supplyAsync(() -> consumer.consume(message)); // Tenta consumir a mensagem.

            // Após a tarefa ser concluída, imprime o resultado.
            completableFuture.thenAccept(result -> 
                System.out.printf("Resultado da consumição: %s%n", result)
            );
        });
    }
}

