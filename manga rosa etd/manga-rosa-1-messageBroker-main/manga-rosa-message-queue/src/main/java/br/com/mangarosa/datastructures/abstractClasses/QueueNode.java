package br.com.mangarosa.datastructures.abstractClasses;

public abstract class QueueNode<E> {

    // Referência para o próximo nó na fila.
    public QueueNode<E> next;
    
    // Valor armazenado neste nó.
    public E value;

    /**
     * Retorna o próximo nó na fila.
     * @return próximo nó.
     */
    public QueueNode<E> getNext() {
        return next;
    }

    /**
     * Define o próximo nó na fila.
     * @param next próximo nó a ser definido.
     */
    public void setNext(QueueNode<E> next) {
        this.next = next;
    }

    /**
     * Retorna o valor armazenado neste nó.
     * @return valor do nó.
     */
    public E getValue() {
        return value;
    }

    /**
     * Define o valor armazenado neste nó.
     * @param value valor a ser armazenado.
     */
    public void setValue(E value) {
        this.value = value;
    }
}

