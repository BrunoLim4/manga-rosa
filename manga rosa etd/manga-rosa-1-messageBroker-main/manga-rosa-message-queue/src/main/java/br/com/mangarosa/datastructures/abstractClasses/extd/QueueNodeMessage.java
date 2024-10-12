package br.com.mangarosa.datastructures.abstractClasses.extd;

import br.com.mangarosa.datastructures.abstractClasses.QueueNode;
import br.com.mangarosa.messages.Message;

import java.time.LocalDateTime;

public class QueueNodeMessage extends QueueNode<Message> implements Comparable<QueueNodeMessage> {

    /**
     * Construtor que cria um nó de fila com um valor de mensagem.
     * @param value Mensagem a ser armazenada no nó.
     */
    public QueueNodeMessage(Message value) {
        this.value = value;
    }

    /**
     * Construtor que cria um nó de fila com um valor de mensagem e o próximo nó.
     * @param value Mensagem a ser armazenada no nó.
     * @param next Próximo nó na fila.
     */
    public QueueNodeMessage(Message value, QueueNode<Message> next) {
        this.value = value;
        this.next = next;
    }

    /**
     * Compara este nó com outro nó de mensagem com base na data de criação da mensagem.
     * @param node Nó de mensagem a ser comparado.
     * @return -1 se este nó é menor, 0 se são iguais, ou 1 se este nó é maior.
     */
    @Override
    public int compareTo(QueueNodeMessage node) {
        LocalDateTime actualNode = this.value.getCreatedAt();
        LocalDateTime nodeCreatedAt = node.getValue().getCreatedAt();

        if (actualNode.isBefore(nodeCreatedAt)) return -1; // Este nó é menor que o nó passado.
        else if (actualNode.isEqual(nodeCreatedAt)) return 0; // Os nós são iguais.
        return 1; // Este nó é maior que o nó passado.
    }
}

