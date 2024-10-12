package br.com.mangarosa.messages.factory;

import br.com.mangarosa.messages.Message;
import br.com.mangarosa.messages.interfaces.Producer;

import java.util.UUID;

public class MessageFactory {

    /**
     * Cria uma nova mensagem a partir de um produtor e do texto da mensagem.
     * @param producer O produtor que gera a mensagem.
     * @param messageText O texto da mensagem a ser criada.
     * @return A nova mensagem criada.
     */
    public static Message build(Producer producer, String messageText) {
        // Instancia uma nova mensagem com o produtor e o texto fornecido.
        Message message = new Message(producer, messageText);
        
        // Gera um ID único para a mensagem usando UUID.
        message.setId(UUID.randomUUID().toString());
        
        // Define o estado de consumo da mensagem como não consumida.
        message.setConsumed(false);
        
        // Retorna a mensagem criada.
        return message;
    }
}
