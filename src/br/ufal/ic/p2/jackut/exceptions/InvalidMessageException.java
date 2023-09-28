package br.ufal.ic.p2.jackut.exceptions;

public class InvalidMessageException extends RuntimeException {
    public InvalidMessageException(){
        super("Usuário não pode enviar recado para esse destino.");
    }
    public InvalidMessageException(String message){
        super(message);
    }

}
