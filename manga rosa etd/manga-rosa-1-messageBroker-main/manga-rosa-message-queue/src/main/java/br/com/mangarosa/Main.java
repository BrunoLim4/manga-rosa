package br.com.mangarosa;

import br.com.mangarosa.messages.Message;
import br.com.mangarosa.messages.MessageBroker;
import br.com.mangarosa.messages.consumers.ConsumerBrokerImpl;
import br.com.mangarosa.messages.interfaces.Consumer;
import br.com.mangarosa.messages.interfaces.MessageRepository;
import br.com.mangarosa.messages.interfaces.Producer;
import br.com.mangarosa.messages.interfaces.Topic;
import br.com.mangarosa.messages.producers.ProducerBrokerImpl;
import br.com.mangarosa.messages.repositories.InMemoryMessageRepositoryImpl;
import br.com.mangarosa.messages.topics.TopicBrokerImpl;

import java.util.List;
import java.util.Scanner;

/**
 * Classe principal que implementa um broker de mensagens.
 * Este broker permite a interação entre produtores e consumidores de mensagens
 * utilizando tópicos definidos.
 */
public class Main {

    public static MessageRepository repository; // Repositório de mensagens
    public static MessageBroker messageBroker; // Broker de mensagens

    public static Topic fastDeliveryItems; // Tópico para itens de entrega rápida
    public static Topic longDistanceItems; // Tópico para itens de longa distância

    public static Producer foodDeliveryProducer; // Produtor para entregas de comida
    public static Producer physicPersonDeliveryProducer; // Produtor para entregas de pessoas físicas
    public static Producer pyMarketPlaceProducer; // Produtor para o marketplace
    public static Producer fastDeliveryProducer; // Produtor para entrega rápida

    public static Consumer fastDeliveryItemsConsumer; // Consumidor para itens de entrega rápida
    public static Consumer longDistanceItemsConsumer; // Consumidor para itens de longa distância

    /**
     * Método principal que inicia o aplicativo.
     * @param args Argumentos da linha de comando.
     */
    public static void main(String[] args) {
        try {
            setUp(); // Configura o broker e os tópicos
            interaction(); // Inicia a interação com o usuário
        } catch (Exception e) {
            System.out.println(e.getMessage()); // Exibe mensagens de erro
        }
    }

    /**
     * Configura o broker de mensagens, tópicos e produtores/consumidores.
     */
    private static void setUp() {
        repository = new InMemoryMessageRepositoryImpl(); // Inicializa o repositório de mensagens
        messageBroker = new MessageBroker(repository); // Inicializa o broker

        // Inicializa os tópicos
        fastDeliveryItems = new TopicBrokerImpl("fast-delivery-items", repository);
        longDistanceItems = new TopicBrokerImpl("long-distance-items", repository);

        // Inicializa os produtores
        foodDeliveryProducer = new ProducerBrokerImpl("FoodDeliveryProducer", messageBroker, fastDeliveryItems.name());
        physicPersonDeliveryProducer = new ProducerBrokerImpl("PhysicPersonDeliveryProducer", messageBroker, fastDeliveryItems.name());
        pyMarketPlaceProducer = new ProducerBrokerImpl("PyMarketPlaceProducer", messageBroker, longDistanceItems.name());
        fastDeliveryProducer = new ProducerBrokerImpl("FastDeliveryProducer", messageBroker, longDistanceItems.name());

        // Inicializa os consumidores
        fastDeliveryItemsConsumer = new ConsumerBrokerImpl("FastDeliveryItemsConsumer", fastDeliveryItems.name(), repository);
        longDistanceItemsConsumer = new ConsumerBrokerImpl("LongDistanceItemsConsumer", longDistanceItems.name(), repository);

        // Adiciona tópicos aos produtores
        foodDeliveryProducer.addTopic(fastDeliveryItems);
        pyMarketPlaceProducer.addTopic(longDistanceItems);

        // Inscreve consumidores nos tópicos
        messageBroker.subscribe(fastDeliveryItems.name(), fastDeliveryItemsConsumer);
        messageBroker.subscribe(longDistanceItems.name(), longDistanceItemsConsumer);
    }

    /**
     * Método para interação com o usuário, permitindo envio e consulta de mensagens.
     * @throws InterruptedException Exceção gerada ao interromper o thread.
     */
    private static void interaction() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        Boolean alive = true;

        while (alive) {
            int topicNumber;
            int topicOption;
            String messageText;
            String aliveModify;
            List<Message> messageList;

            // Interface do usuário
            System.out.println("Bem vindo ao Message Broker!");
            System.out.println("Selecione um tópico para sua mensagem: \n" +
                    "\t 1 - fastDeliveryItems\n" +
                    "\t 2 - longDistanceItems");

            topicNumber = sc.nextInt();
            sc.nextLine();

            // Tratamento de tópicos
            if (topicNumber == 1) {
                // Tópico para entrega rápida
                System.out.println("\nTopic: fast-delivery-items");
                System.out.println("Selecione um producer para sua mensagem: \n" +
                        "\t 1 - FoodDeliveryProducer\n" +
                        "\t 2 - PhysicPersonDeliveryProducer");

                System.out.println("\nOu selecione um dos métodos: \n" +
                        "\t 3 - Ver todas as mensagens NÃO consumidas de um tópico\n" +
                        "\t 4 - Ver todas as mensagens consumidas de um tópico");

                topicOption = sc.nextInt();
                sc.nextLine();

                switch (topicOption) {
                    case 1:
                        System.out.println("\nProducer: FoodDeliveryProducer");
                        System.out.println("Digite sua mensagem: ");
                        messageText = sc.nextLine();
                        foodDeliveryProducer.sendMessage(messageText);
                        break;

                    case 2:
                        System.out.println("\nProducer: PhysicPersonDeliveryProducer");
                        System.out.println("Digite sua mensagem: ");
                        messageText = sc.nextLine();
                        physicPersonDeliveryProducer.sendMessage(messageText);
                        break;

                    case 3:
                        // Consulta de mensagens não consumidas
                        messageList = repository.getAllNotConsumedMessagesByTopic("fast-delivery-items");
                        if (messageList.isEmpty()) {
                            System.out.println("Lista está vazia");
                            break;
                        }
                        for (Message message : messageList) {
                            System.out.println(message.toString());
                        }
                        break;

                    case 4:
                        // Consulta de mensagens consumidas
                        messageList = repository.getAllConsumedMessagesByTopic("fast-delivery-items");
                        if (messageList.isEmpty()) {
                            System.out.println("Lista está vazia");
                            break;
                        }
                        for (Message message : messageList) {
                            System.out.println(message.toString());
                        }
                        break;

                    default:
                        System.out.println("Opção inválida!");
                        break;
                }

            } else if (topicNumber == 2) {
                // Tópico para longa distância
                System.out.println("Topic: long-distance-items");
                System.out.println("Selecione um producer para sua mensagem: \n" +
                        "\t 1 - PyMarketPlaceProducer\n" +
                        "\t 2 - FastDeliveryProducer");

                System.out.println("\nOu selecione um dos métodos: \n" +
                        "\t 3 - Ver todas as mensagens NÃO consumidas de um tópico\n" +
                        "\t 4 - Ver todas as mensagens consumidas de um tópico");

                topicOption = sc.nextInt();
                sc.nextLine();

                switch (topicOption) {
                    case 1:
                        System.out.println("\nProducer: PyMarketPlaceProducer");
                        System.out.println("Digite sua mensagem: ");
                        messageText = sc.nextLine();
                        pyMarketPlaceProducer.sendMessage(messageText);
                        break;

                    case 2:
                        System.out.println("\nProducer: FastDeliveryProducer");
                        System.out.println("Digite sua mensagem: ");
                        messageText = sc.nextLine();
                        fastDeliveryProducer.sendMessage(messageText);
                        break;

                    case 3:
                        // Consulta de mensagens não consumidas
                        messageList = repository.getAllNotConsumedMessagesByTopic("long-distance-items");
                        if (messageList.isEmpty()) {
                            System.out.println("Lista está vazia");
                            break;
                        }
                        for (Message message : messageList) {
                            System.out.println(message.toString());
                        }
                        break;

                    case 4:
                        // Consulta de mensagens consumidas
                        messageList = repository.getAllConsumedMessagesByTopic("long-distance-items");
                        if (messageList.isEmpty()) {
                            System.out.println("Lista está vazia");
                            break;
                        }
                        for (Message message : messageList) {
                            System.out.println(message.toString());
                        }
                        break;

                    default:
                        System.out.println("Opção inválida!");
                        break;
                }
            } else {
                System.out.println("Opção inválida!");
            }

            Thread.sleep(3000); // Pausa de 3 segundos

            do {
                System.out.println("\nDeseja continuar no message broker (y or n)");
                aliveModify = sc.nextLine();
            } while (!aliveModify.equalsIgnoreCase("Y") && !aliveModify.equalsIgnoreCase("N"));

            alive = aliveModify.equalsIgnoreCase("Y"); // Controle do loop de interação
        }

        sc.close(); // Fecha o scanner ao final da interação
    }
}
