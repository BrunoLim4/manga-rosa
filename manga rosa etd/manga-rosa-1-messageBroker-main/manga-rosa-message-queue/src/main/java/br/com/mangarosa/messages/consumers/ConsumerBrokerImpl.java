package br.com.mangarosa.messages.consumers;

import br.com.mangarosa.messages.Message;
import br.com.mangarosa.messages.interfaces.Consumer;
import br.com.mangarosa.messages.interfaces.MessageRepository;

import java.util.Objects;
import java.util.UUID;

public class ConsumerBrokerImpl implements Consumer {

    private final String name; // Nome do consumidor
    private final String topicName; // Nome do tópico ao qual o consumidor está inscrito
    private final MessageRepository messageRepository; // Repositório de mensagens para interação com o banco

    /**
     * Construtor que inicializa um consumidor com nome, tópico e repositório de mensagens.
     * @param name Nome do consumidor.
     * @param topicName Nome do tópico.
     * @param messageRepository Repositório de mensagens.
     */
    public ConsumerBrokerImpl(String name, String topicName, MessageRepository messageRepository) {
        this.name = Objects.requireNonNull(name, "O nome do consumidor não pode ser nulo");
        this.topicName = Objects.requireNonNull(topicName, "O nome do tópico não pode ser nulo");
        this.messageRepository = Objects.requireNonNull(messageRepository, "O consumidor deve ter um repositório");
    }

    @Override
    public boolean consume(Message message) {
        if (message == null) throw new IllegalArgumentException("A mensagem não pode ser nula");

        System.out.println("O consumidor " + name + " está processando: " + message.getId());
        message.addConsumption(this); // Registra a consumação da mensagem

        try {
            // Verifica se a mensagem expirou
            if (message.isExperied()) {
                throw new IllegalArgumentException("Mensagem: " + message.toString() +
                        "\nExpirada em: " + message.getCreatedAt().plusMinutes(5));
            }
            // Consome a mensagem do repositório
            this.messageRepository.consumeMessage(this.topicName, UUID.fromString(message.getId()));
            this.consumeMessageOnConsole(message); // Exibe informações da mensagem consumida
            return true;

        } catch (Exception e) {
            System.err.println("Erro ao processar a mensagem: " + e.getMessage());
            return false; // Retorna false em caso de erro
        }
    }

    @Override
    public String name() {
        return name; // Retorna o nome do consumidor
    }

    /**
     * Exibe detalhes da mensagem consumida no console.
     * @param message Mensagem a ser exibida.
     */
    private void consumeMessageOnConsole(Message message) {
        System.out.println("======== CONSUMINDO MENSAGEM POR " + name + " ==========");
        System.out.println("ID: " + message.getId());
        System.out.println("Mensagem: " + message.getMessage());
        System.out.println("Produtor: " + message.getProducer().name());
        System.out.println("Criada em: " + message.getCreatedAt());
        System.out.println("Consumida: " + message.isConsumed());
        System.out.println("Expirada: " + message.isExperied());
        System.out.println("===============================================================");
    }
}
