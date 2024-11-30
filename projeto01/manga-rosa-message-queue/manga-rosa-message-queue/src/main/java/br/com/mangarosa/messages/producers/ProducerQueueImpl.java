package br.com.mangarosa.messages.producers;

// Importa a classe LinkedQueue, que representa a fila de mensagens
import br.com.mangarosa.datastructures.interfaces.impl.LinkedQueue;

// Importa a classe Message, que representa uma mensagem a ser enviada
import br.com.mangarosa.messages.Message;

// Define a classe ProducerQueueImpl, que implementa um produtor de mensagens
public class ProducerQueueImpl {
    // Atributo que armazena a fila de mensagens
    private final LinkedQueue messageQueue;

    // Construtor da classe, que inicializa a fila de mensagens
    public ProducerQueueImpl(LinkedQueue messageQueue) {
        this.messageQueue = messageQueue; // Atribui a fila recebida ao atributo
    }

    // Método para enviar uma nova mensagem para a fila
    public void sendMessage(String content) {
        // Cria uma nova instância de Message com o conteúdo fornecido
        Message message = new Message(content);
        
        // Adiciona a nova mensagem à fila
        messageQueue.enqueue(message);
        
        // Exibe uma mensagem indicando que a mensagem foi produzida
        System.out.println("Produzido: " + message);
    }
}

