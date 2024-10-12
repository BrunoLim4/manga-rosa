package br.com.mangarosa.datastructures.abstractClasses;

/**
 * Classe abstrata que representa um nó de uma fila.
 * Cada nó contém um valor e uma referência para o próximo nó na fila.
 * 
 * @param <E> o tipo de dado armazenado no nó
 */
public abstract class QueueNode<E> {

    public QueueNode<E> next; // Referência para o próximo nó na fila
    public E value; // Valor armazenado no nó

    /**
     * Obtém o próximo nó na fila.
     * 
     * @return o próximo nó
     */
    public QueueNode<E> getNext() {
        return next;
    }

    /**
     * Define o próximo nó na fila.
     * 
     * @param next o próximo nó a ser definido
     */
    public void setNext(QueueNode<E> next) {
        this.next = next;
    }

    /**
     * Obtém o valor armazenado no nó.
     * 
     * @return o valor do nó
     */
    public E getValue() {
        return value;
    }

    /**
     * Define o valor armazenado no nó.
     * 
     * @param value o valor a ser armazenado no nó
     */
    public void setValue(E value) {
        this.value = value;
    }
}
