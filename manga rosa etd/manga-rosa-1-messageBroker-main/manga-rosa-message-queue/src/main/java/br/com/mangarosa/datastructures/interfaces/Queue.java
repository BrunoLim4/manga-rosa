package br.com.mangarosa.datastructures.interfaces;

/**
 * A interface Queue define uma fila genérica do tipo T.
 * 
 * @param <T> um tipo de dados que deve ser comparável.
 */
public interface Queue<T extends Comparable<T>> extends Iterable<T> {

    /**
     * Insere um valor no final da fila.
     * 
     * @param value um valor de qualquer tipo T que será inserido na fila.
     */
    void enqueue(T value);

    /**
     * Remove e retorna o elemento no início da fila.
     * 
     * @return o primeiro elemento da fila.
     */
    T dequeue();

    /**
     * Retorna o primeiro elemento no início da fila, mas não
     * o remove da fila.
     * 
     * @return o primeiro elemento da fila.
     */
    T peek();

    /**
     * Verifica se a fila está vazia.
     * 
     * @return true se a fila estiver vazia, false caso contrário.
     */
    boolean isEmpty();

    /**
     * Retorna o tamanho da fila.
     * 
     * @return a quantidade de elementos na fila.
     */
    int size();

    /**
     * Retorna todos os elementos da fila em um array.
     * 
     * @return um array contendo todos os elementos da fila.
     */
    T[] toArray();

    /**
     * Limpa a fila, removendo todos os elementos.
     */
    void clear();
}
