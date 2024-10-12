package br.com.mangarosa.messages.repositories;

import br.com.mangarosa.datastructures.abstractClasses.extd.QueueNodeMessage;
import br.com.mangarosa.datastructures.interfaces.impl.LinkedQueue;
import br.com.mangarosa.messages.Message;
import br.com.mangarosa.messages.interfaces.MessageRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryMessageRepositoryImpl implements MessageRepository {

    private final Map<String, LinkedQueue> topics; // Mapa para armazenar filas de mensagens por tópico

    public InMemoryMessageRepositoryImpl() {
        this.topics = new HashMap<>(); // Inicializa o mapa de tópicos
    }

    @Override
    public void append(String topic, Message message) {
        // Adiciona uma nova mensagem a um tópico. Se o tópico não existir, cria uma nova fila.
        if (this.topics.get(topic) == null) {
            this.topics.put(topic, new LinkedQueue());
        }
        topics.get(topic).enqueue(new QueueNodeMessage(message)); // Enfileira a mensagem
    }

    @Override
    public void consumeMessage(String topic, UUID messageId) {
        // Consome uma mensagem específica em um tópico, marcando-a como consumida.
        LinkedQueue messagesQueue = topics.get(topic);
        if (messagesQueue == null) throw new IllegalArgumentException(topic + " não encontrado");

        // Itera pela fila para encontrar e consumir a mensagem correspondente
        for (QueueNodeMessage node : messagesQueue) {
            String uuid = node.getValue().getId();
            if (Objects.equals(uuid, messageId.toString())) {
                node.getValue().setConsumed(true); // Marca a mensagem como consumida
            }
        }
    }

    @Override
    public List<Message> getAllNotConsumedMessagesByTopic(String topic) {
        // Retorna todas as mensagens não consumidas de um tópico.
        LinkedQueue messagesQueue = topics.get(topic);
        if (messagesQueue == null) throw new IllegalArgumentException(topic + " não encontrado");
        List<Message> messageList = new ArrayList<>();

        // Adiciona mensagens não consumidas à lista
        for (QueueNodeMessage node : messagesQueue) {
            if (!node.getValue().isConsumed()) {
                messageList.add(node.getValue());
            }
        }
        return messageList; // Retorna a lista de mensagens não consumidas
    }

    @Override
    public List<Message> getAllConsumedMessagesByTopic(String topic) {
        // Retorna todas as mensagens consumidas de um tópico.
        Message[] messagesQueue = topics.get(topic).toArrayValues();
        if (messagesQueue == null) throw new IllegalArgumentException(topic + " não encontrado");
        return Arrays.stream(messagesQueue).filter(Message::isConsumed).collect(Collectors.toList()); // Filtra e retorna mensagens consumidas
    }
}

