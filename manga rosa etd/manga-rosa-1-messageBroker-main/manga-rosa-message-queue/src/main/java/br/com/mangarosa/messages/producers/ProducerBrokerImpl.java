package br.com.mangarosa.messages.producers;

import br.com.mangarosa.messages.Message;
import br.com.mangarosa.messages.MessageBroker;
import br.com.mangarosa.messages.factory.MessageFactory;
import br.com.mangarosa.messages.interfaces.Producer;
import br.com.mangarosa.messages.interfaces.Topic;

import java.util.Objects;

public class ProducerBrokerImpl implements Producer {

    private final String name; // Nome do produtor
    private final MessageBroker messageBroker; // Broker de mensagens para gerenciar tópicos
    private final String topicName; // Nome do tópico ao qual o produtor envia mensagens

    /**
     * Construtor que inicializa um produtor com nome, broker de mensagens e tópico.
     * @param name Nome do produtor.
     * @param messageBroker Broker de mensagens.
     * @param topicName Nome do tópico.
     */
    public ProducerBrokerImpl(String name, MessageBroker messageBroker, String topicName) {
        this.name = Objects.requireNonNull(name, "O nome do produtor não pode ser nulo");
        this.messageBroker = Objects.requireNonNull(messageBroker, "O produtor deve ter um broker de mensagens");
        this.topicName = Objects.requireNonNull(topicName, "O nome do tópico não pode ser nulo");
    }

    @Override
    public void addTopic(Topic topic) {
        // Adiciona um novo tópico ao broker de mensagens.
        this.messageBroker.createTopic(topic);
    }

    @Override
    public void removeTopic(Topic topic) {
        // Remove um tópi
    }
}
