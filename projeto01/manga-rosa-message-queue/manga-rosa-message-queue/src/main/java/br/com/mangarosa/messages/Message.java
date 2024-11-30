package br.com.mangarosa.messages;

public class Message {

    private String content;

    public Message(String content) {
        this.content = content;
    }

    // Método para pegar os dados da da mensagem
    public String getContent() {
        return content;
    }

    // Método para mudar os dados da mensagem
    public void setContent(String content) {
        this.content = content;
    }

    // Método que converte a mensagem em uma string para exibir
    @Override
    public String toString() {
        return content + '\'';
    }
}
