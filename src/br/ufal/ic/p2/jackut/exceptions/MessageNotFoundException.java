package br.ufal.ic.p2.jackut.exceptions;

import br.ufal.ic.p2.jackut.models.Message;

public class MessageNotFoundException extends RuntimeException{
    public MessageNotFoundException(){
        super("Mensagem nao encontrada.");
    }
    public MessageNotFoundException(String message){
        super(message);
    }
}

