package br.com.mangarosa.datastructures.interfaces.impl;

import br.com.mangarosa.datastructures.abstractClasses.extd.QueueNodeMessage;
import br.com.mangarosa.datastructures.interfaces.Queue;
import br.com.mangarosa.messages.Message;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A classe LinkedQueue implementa uma estrutura de fila usando uma lista encadeada.
 * Isso permite operações eficientes de enfileiramento e desenfileiramento de elementos.
 * 
 * @param <QueueNodeMessage> O tipo de elementos armazenados na fila.
 */
public class LinkedQueue implements Queue<QueueNodeMessage> {

    private QueueNodeMessage firstNode; // Aponta para o início da fila
    private QueueNodeMessage lastNode;  // Aponta para o final da fila
    private Integer size;               // Rastrea o número de elementos na fila

    /**
     * Constrói uma LinkedQueue vazia.
     */
    public LinkedQueue() {
        this.firstNode = null;
        this.lastNode = null;
        this.size = 0;
    }

    /**
     * Adiciona um novo nó ao final da fila.
     * 
     * @param newNode O novo nó a ser adicionado.
     */
    @Override
    public void enqueue(QueueNodeMessage newNode) {
        if (this.size.equals(0)){
            this.firstNode = newNode;
            this.lastNode = newNode;
        } else {
            QueueNodeMessage pointer = this.lastNode;
            this.lastNode = newNode;
            pointer.setNext(newNode);
        }
        this.size++;
    }

    /**
     * Remove e retorna o nó no início da fila.
     * 
     * @return O nó removido.
     * @throws IllegalStateException se a fila estiver vazia.
     */
    @Override
    public QueueNodeMessage dequeue() {
        if (this.firstNode == null) {
            throw new IllegalStateException("A fila está vazia.");
        }

        QueueNodeMessage node = this.firstNode;
        this.firstNode = (QueueNodeMessage) this.firstNode.getNext();

        if (this.firstNode == null) {
            this.lastNode = null;
        }
        this.size--;
        return node;
    }

    /**
     * Remove e retorna o valor do nó no início da fila.
     * 
     * @return O valor do nó removido.
     * @throws IllegalStateException se a fila estiver vazia.
     */
    public Message dequeueValue() {
        if (this.firstNode == null) {
            throw new IllegalStateException("A fila está vazia.");
        }

        QueueNodeMessage node = this.firstNode;
        this.firstNode = (QueueNodeMessage) this.firstNode.getNext();

        if (this.firstNode == null) {
            this.lastNode = null;
        }
        this.size--;
        return node.getValue(); // Retorna o valor removido
    }

    /**
     * Recupera o nó no início da fila sem removê-lo.
     * 
     * @return O nó no início da fila.
     * @throws IllegalStateException se a fila estiver vazia.
     */
    @Override
    public QueueNodeMessage peek() {
        if (this.firstNode == null) {
            throw new IllegalStateException("A fila está vazia.");
        }
        return this.firstNode;
    }

    /**
     * Verifica se a fila está vazia.
     * 
     * @return true se a fila estiver vazia, false caso contrário.
     */
    @Override
    public boolean isEmpty() {
        return this.firstNode == null;
    }

    /**
     * Retorna o número de elementos na fila.
     * 
     * @return O tamanho da fila.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Converte a fila em um array de objetos QueueNodeMessage.
     * 
     * @return Um array contendo os elementos da fila.
     */
    @Override
    public QueueNodeMessage[] toArray() {
        QueueNodeMessage[] arr = new QueueNodeMessage[this.size()];
        QueueNodeMessage current = this.firstNode;
        int index = 0;

        while (current != null) {
            arr[index] = current;
            current = (QueueNodeMessage) current.getNext();
            index++;
        }
        return arr;
    }

    /**
     * Converte a fila em um array de objetos Message.
     * 
     * @return Um array contendo os valores dos nós na fila.
     */
    public Message[] toArrayValues() {
        Message[] arr = new Message[this.size];
        QueueNodeMessage current = this.firstNode;
        int index = 0;

        while (current != null) {
            arr[index] = current.getValue();
            current = (QueueNodeMessage) current.getNext();
            index++;
        }
        return arr;
    }

    /**
     * Limpa a fila, removendo todos os elementos e liberando memória.
     */
    @Override
    public void clear() {
        QueueNodeMessage current = this.firstNode;

        while (current != null) {
            QueueNodeMessage next = (QueueNodeMessage) current.getNext();
            current.setNext(null);
            current.setValue(null);
            current = next;
        }

        this.firstNode = null;
        this.lastNode = null;
        this.size = 0;
    }

    /**
     * Retorna um iterador sobre os elementos nesta fila.
     * 
     * @return Um iterador para a fila.
     */
    @Override
    public Iterator<QueueNodeMessage> iterator() {
        return new Iterator<QueueNodeMessage>() {
            private QueueNodeMessage current = firstNode;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public QueueNodeMessage next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                QueueNodeMessage temp = current;
                current = (QueueNodeMessage) current.getNext();
                return temp;
            }
        };
    }
}

