package br.ufal.ic.p2.jackut.exceptions;

public class CommunityCreationException extends RuntimeException{

    public CommunityCreationException(){
        super("Esse objeto já existe.");
    }
    public CommunityCreationException(String message){
        super(message);
    }
}
