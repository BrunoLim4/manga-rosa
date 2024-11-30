package br.com.mangarosa.messages.consumers;

import br.com.mangarosa.datastructures.interfaces.impl.LinkedQueue;
import br.com.mangarosa.messages.Message;

public class ConsumerQueueImpl {
    private final LinkedQueue messageQueue;

    public ConsumerQueueImpl(LinkedQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    // consome uma mensagem da fila
    public void consume() {
        if (!messageQueue.isEmpty()) {

            Message message = messageQueue.dequeue();
            System.out.println("Consumido: " + message);
        } else {
            System.out.println("A fila ficou vazia.");
        }
    }
}
