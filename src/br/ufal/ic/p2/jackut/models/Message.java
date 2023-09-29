package br.ufal.ic.p2.jackut.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * A classe Recado representa uma mensagem enviada por um remetente.
 *
 * @author Gustavo Gaia
 */
public class Message {
    private String remetente;
    private String message;

    /**
     * Construtor da classe Recado
     */
    public Message(){};
    /**
     * Construtor da classe Recado nas configurações para armazenamento JSON.
     *
     * @param remetente O remetente da mensagem.
     * @param mensagem O conteúdo da mensagem.
     */
    @JsonCreator
    public Message(@JsonProperty("remetente") String remetente, @JsonProperty("mensagem") String mensagem) {
        this.remetente = remetente;
        this.message = mensagem;
    }
    /**
     * Obtém o remetende da mensagem
     * @return remetende da mensagem
     */
    public String getRemetente() {
        return remetente;
    }
    /**
     * Obtém a mensagem
     * @return a mensagem
     */
    public String getMessage() {
        return message;
    }

}
