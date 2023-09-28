package br.ufal.ic.p2.jackut.exceptions;

public class InvalidFriendSolicitationException extends RuntimeException{

    public InvalidFriendSolicitationException(){
        super("Usuário não pode ser adicionado como amigo");
    }
    public InvalidFriendSolicitationException(String message){
        super(message);
    }
}
