package br.com.mangarosa.datastructures.interfaces.impl;

// Importa a classe Message do pacote br.com.mangarosa.messages
import br.com.mangarosa.messages.Message;

// Importa as classes necessárias para trabalhar com iteradores e exceções
import java.util.Iterator;
import java.util.NoSuchElementException;

// Importa a classe QueueNode, que representa um nó da fila
import br.com.mangarosa.datastructures.abstractClasses.QueueNode;

// Define a classe LinkedQueue, que representa uma fila encadeada
public class LinkedQueue {
    // Atributo que aponta para o primeiro nó da fila
    private QueueNode firstNode;

    // Atributo que aponta para o último nó da fila
    private QueueNode lastNode;

    // Atributo que armazena o tamanho da fila
    private int size;

    // Construtor da classe, que inicializa uma fila vazia
    public LinkedQueue() {
        this.firstNode = null; // O primeiro nó é nulo inicialmente
        this.lastNode = null;  // O último nó é nulo inicialmente
        this.size = 0;        // O tamanho da fila é zero
    }

    // Método para adicionar um novo elemento na fila (no final da fila)
    public void enqueue(Message message) {
        QueueNode newNode = new QueueNode(message); // Cria um novo nó com a mensagem

        if (size == 0) { // Se a fila estiver vazia
            firstNode = newNode; // O primeiro nó é o novo nó
            lastNode = newNode;  // O último nó também é o novo nó
        } else {
            lastNode.setNext(newNode); // O próximo do último nó aponta para o novo nó
            lastNode = newNode; // O último nó agora é o novo nó
        }
        size++; // Aumenta o tamanho da fila
    }

    // Método para remover e retornar o primeiro elemento da fila
    public Message dequeue() {
        if (firstNode == null) { // Verifica se a fila está vazia
            throw new IllegalStateException("A fila está vazia."); // Lança exceção se estiver vazia
        }

        Message node = firstNode.getValue(); // Armazena o valor do primeiro nó

        firstNode = firstNode.getNext(); // Atualiza o primeiro nó para o próximo

        if (firstNode == null) { // Se a fila ficou vazia após a remoção
            lastNode = null; // Atualiza o último nó para null
        }
        size--; // Diminui o tamanho da fila
        return node; // Retorna o valor removido
    }

    // Método para espiar o primeiro elemento sem removê-lo
    public Message peek() {
        if (firstNode == null) { // Verifica se a fila está vazia
            throw new IllegalStateException("A fila está vazia."); // Lança exceção se estiver vazia
        }
        return firstNode.getValue(); // Retorna o valor do primeiro nó
    }

    // Método que verifica se a fila está vazia
    public boolean isEmpty() {
        return size == 0; // Retorna true se o tamanho for zero
    }

    // Método que retorna o tamanho da fila
    public int size() {
        return size; // Retorna o tamanho atual da fila
    }

    // Método que limpa a fila, removendo todos os elementos
    public void clear() {
        firstNode = null; // Reseta o primeiro nó
        lastNode = null;  // Reseta o último nó
        size = 0;        // Reseta o tamanho para zero
    }

    // Método que retorna um iterador para percorrer os elementos da fila
    public Iterator<Message> iterator() {
        return new Iterator<>() {
            private QueueNode current = firstNode; // Variável que rastreia o nó atual

            @Override
            public boolean hasNext() {
                return current != null; // Retorna true se ainda houver nós a percorrer
            }

            @Override
            public Message next() {
                if (!hasNext()) { // Verifica se há um próximo elemento
                    throw new NoSuchElementException(); // Lança exceção se não houver
                }
                Message message = current.getValue(); // Armazena o valor do nó atual
                current = current.getNext(); // Move para o próximo nó
                return message; // Retorna o valor do nó atual
            }
        };
    }
}
