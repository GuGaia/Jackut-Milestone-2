package br.ufal.ic.p2.jackut.exceptions;

public class InvalidCredentialException extends RuntimeException{
    public InvalidCredentialException(){
        super("Credencial Inválida");
    }
    public InvalidCredentialException(String message){
        super(message);
    }

}
