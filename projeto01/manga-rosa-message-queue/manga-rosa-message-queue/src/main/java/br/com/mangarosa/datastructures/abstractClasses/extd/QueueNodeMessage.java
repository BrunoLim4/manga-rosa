package br.com.mangarosa.datastructures.abstractClasses.extd;

public class QueueNodeMessage {
    private QueueNodeMessage next;

    // obtém o próximo nó na fila
    public QueueNodeMessage getNext() {
        return this.next;
    }

    //  define o próximo nó na fila
    public void setNext(QueueNodeMessage next) {
        this.next = next;
    }
}
