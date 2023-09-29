package br.ufal.ic.p2.jackut.exceptions;

public class InvalidCommunityException extends RuntimeException{

    public InvalidCommunityException(){
        super("Esse objeto já existe.");
    }
    public InvalidCommunityException(String message){
        super(message);
    }
}
