package br.com.mangarosa.messages.consumers;

import br.com.mangarosa.messages.Message;
import br.com.mangarosa.messages.interfaces.Consumer;
import br.com.mangarosa.messages.interfaces.MessageRepository;

import java.util.Objects;
import java.util.UUID;

/**
 * Implementação da interface Consumer para processar mensagens de um tópico específico.
 */
public class ConsumerBrokerImpl implements Consumer {

    private final String name; // Nome do consumidor
    private final String topicName; // Nome do tópico associado ao consumidor
    private final MessageRepository messageRepository; // Repositório de mensagens

    /**
     * Construtor da classe ConsumerBrokerImpl.
     *
     * @param name o nome do consumidor
     * @param topicName o nome do tópico
     * @param messageRepository o repositório de mensagens
     * @throws NullPointerException se o nome, o tópico ou o repositório forem nulos
     */
    public ConsumerBrokerImpl(String name, String topicName, MessageRepository messageRepository) {
        this.name = Objects.requireNonNull(name, "Consumer name can`t be null");
        this.topicName = Objects.requireNonNull(topicName, "The topic name can`t be null");
        this.messageRepository = Objects.requireNonNull(messageRepository, "The consumer must have a repository");
    }

    /**
     * Consome uma mensagem, processando-a e registrando-a no repositório.
     *
     * @param message a mensagem a ser consumida
     * @return true se a mensagem foi consumida com sucesso, false caso contrário
     * @throws IllegalArgumentException se a mensagem for nula
     */
    @Override
    public boolean consume(Message message) {
        if (message == null) throw new IllegalArgumentException("Message can`t be null");

        System.out.println("Consumer " + name + " is processing: " + message.getId());
        message.addConsumption(this);

        try {
            if (message.isExperied()) {
                throw new IllegalArgumentException("Message: " + message.toString() +
                        "\nExpired in: " + message.getCreatedAt().plusMinutes(5));
            }
            this.messageRepository.consumeMessage(this.topicName, UUID.fromString(message.getId()));
            this.consumeMessageOnConsole(message);
            return true;

        } catch (Exception e) {
            System.err.println("Error on processes message: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retorna o nome do consumidor.
     *
     * @return o nome do consumidor
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * Exibe os detalhes da mensagem consumida no console.
     *
     * @param message a mensagem a ser exibida
     */
    private void consumeMessageOnConsole(Message message) {
        System.out.println("======== CONSUMING MESSAGE BY " + name + " ==========");
        System.out.println("ID: " + message.getId());
        System.out.println("Message: " + message.getMessage());
        System.out.println("Producer: " + message.getProducer().name());
        System.out.println("CreatedAt: " + message.getCreatedAt());
        System.out.println("Consumed: " + message.isConsumed());
        System.out.println("Expired: " + message.isExperied());
        System.out.println("===============================================================");
    }
}
