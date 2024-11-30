package br.com.mangarosa.datastructures.abstractClasses;

// Importa a classe Message do pacote br.com.mangarosa.messages
import br.com.mangarosa.messages.Message;

// Define a classe QueueNode, representa um nó em uma estrutura de fila
public class QueueNode {

    // Atributo que armazena o valor do nó, é um objeto do tipo Message
    private Message value;

    // referencia o próximo nó na fila
    private QueueNode next;

    // Construtor da classe, inicializa o nó com um valor e define o próximo como null
    public QueueNode(Message value) {
        this.value = value; // Atribui o valor recebido ao atributo value
        this.next = null;   // Inicializa o próximo nó como null
    }

    // Método getter para acessar o valor do nó
    public Message getValue() {
        return value; // Retorna o valor armazenado no nó
    }

    // Método setter para modificar o valor do nó
    public void setValue(Message value) {
        this.value = value; // Atribui um novo valor ao atributo value
    }

    // Método getter para acessar o próximo nó na fila
    public QueueNode getNext() {
        return next; // Retorna o próximo nó
    }

    // Método setter para modificar o próximo nó
    public void setNext(QueueNode next) {
        this.next = next; // Atribui um novo próximo nó
    }
}