package br.com.mangarosa;

import br.com.mangarosa.datastructures.interfaces.impl.LinkedQueue;
import br.com.mangarosa.messages.consumers.ConsumerQueueImpl;
import br.com.mangarosa.messages.producers.ProducerQueueImpl;

public class Main {
    public static void main(String[] args) {
        LinkedQueue messageQueue = new LinkedQueue();

        ProducerQueueImpl producer = new ProducerQueueImpl(messageQueue);
        ConsumerQueueImpl consumer = new ConsumerQueueImpl(messageQueue);

        producer.sendMessage("1 -  mensagem ");
        producer.sendMessage("2 -  mensagem ");
        producer.sendMessage("3 -  mensagem ");

        consumer.consume();
        consumer.consume();
        consumer.consume();

        consumer.consume();
    }
}
