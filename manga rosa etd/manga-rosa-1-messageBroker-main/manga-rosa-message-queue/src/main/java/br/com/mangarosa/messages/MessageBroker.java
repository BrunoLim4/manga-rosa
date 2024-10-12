package br.com.mangarosa.messages;

import br.com.mangarosa.messages.interfaces.Consumer;
import br.com.mangarosa.messages.interfaces.MessageRepository;
import br.com.mangarosa.messages.interfaces.Topic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MessageBroker {

    private final Map<String, Topic> topics; // Mapa que armazena tópicos pelo nome
    private final MessageRepository repository; // Repositório de mensagens
    private final ScheduledExecutorService scheduleAtFixedRate; // Executor para tarefas agendadas

    public MessageBroker(MessageRepository repository){
        this.topics = new HashMap<>(); // Inicializa o mapa de tópicos
        this.repository = repository; // Define o repositório de mensagens
        this.scheduleAtFixedRate = Executors.newScheduledThreadPool(5); // Cria um pool de threads para agendamento
    }

    /**
     * Cria um novo tópico se o nome não existir.
     * @param topic Tópico a ser criado.
     */
    public void createTopic(Topic topic){
        if(topics.containsKey(topic.name()))
            throw new IllegalArgumentException("O nome do tópico já existe");
        this.topics.put(topic.name(), topic); // Adiciona o tópico ao mapa
    }

    /**
     * Remove um tópico se existir.
     * @param topic Nome do tópico a ser removido.
     */
    public void removeTopic(String topic){
        if(!topics.containsKey(topic))
            throw new IllegalArgumentException("O nome do tópico não existe, verifique se está enviando a chave correta");
        Topic t = this.topics.get(topic);
        t.consumers().forEach(t::unsubscribe); // Desinscreve todos os consumidores do tópico
        topics.remove(topic); // Remove o tópico do mapa
    }

    /**
     * Inscreve um consumidor em um tópico.
     * @param topic Nome do tópico.
     * @param consumer Consumidor a ser inscrito.
     */
    public void subscribe(String topic, Consumer consumer){
        if(!topics.containsKey(topic))
            throw new IllegalArgumentException("O nome do tópico não existe, verifique se está enviando a chave correta");
        Topic t = this.topics.get(topic);
        t.subscribe(consumer); // Inscreve o consumidor no tópico
    }

    /**
     * Remove a inscrição de um consumidor em um tópico.
     * @param topic Nome do tópico.
     * @param consumer Consumidor a ser desinscrito.
     */
    public void unsubscribe(String topic, Consumer consumer){
        if(!topics.containsKey(topic))
            throw new IllegalArgumentException("O nome do tópico não existe, verifique se está enviando a chave correta");
        Topic t = this.topics.get(topic);
        t.unsubscribe(consumer); // Remove a inscrição do consumidor
    }

    /**
     * Obtém um tópico pelo seu nome.
     * @param topic Nome do tópico.
     * @return Tópico correspondente.
     */
    public Topic getTopicByName(String topic){
        if(!topics.containsKey(topic))
            throw new IllegalArgumentException("O nome do tópico não existe, verifique se está enviando a chave correta");
        return this.topics.get(topic); // Retorna o tópico
    }

    /**
     * Notifica os consumidores sobre novas mensagens.
     */
    public void notifyConsumers(){
        Runnable notifyTask = () -> {
            // Itera por todos os tópicos
            topics.keySet().forEach(key -> {
                // Obtém mensagens não consumidas do repositório
                List<Message> messages = repository
                        .getAllNotConsumedMessagesByTopic(key);

                if(Objects.nonNull(messages)){
                    // Aqui deveria ser a lógica para notificar os consumidores
                }
            });
        };
        // Agenda a tarefa para executar a cada 1 minuto, após 2 minutos
        ScheduledFuture<?> scheduledFuture = scheduleAtFixedRate
                .scheduleAtFixedRate(notifyTask, 2, 1, TimeUnit.MINUTES);
    }
}
