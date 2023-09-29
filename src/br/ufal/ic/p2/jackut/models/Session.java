package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.services.Manager;

import java.util.Objects;

public class Session {
   private User user;
   private String ID;

    public Session(User user){
        this.user = user;
        this.ID = generateSessionId(user.getUserAttribute("login"));
    }
    /**
     * Gera um ID �nico de sess�o combinando o login e o momento da cria��o.
     *
     * @param login O login do usu�rio.
     */
    private String generateSessionId(String login) {
        long timestamp = System.currentTimeMillis();
        return login + "_" + timestamp;
    }
    public User getUser() {
        return user;
    }
    public String getID() {
        return ID;
    }
    public void editProfile(String atributte, String valor, Manager system){

        if (Objects.equals(atributte, "nome")) this.user.setName(valor);
        else if (Objects.equals(atributte, "senha")) this.user.setPassword(valor);
        else if (Objects.equals(atributte, "login")) {
            if (system.verifyUser(valor)) throw new InvalidCredentialException("Login inv�lido.");
            else this.user.setLogin(valor);
        }
        else this.user.setAttributes(atributte, valor);
    }
    /**
     * Adiciona um usu�rio autenticado como amigo de outro usu�rio enviando uma solicita��o, deve ser confirmada
     * pelo recebedor do pedido.
     *
     * @param friend O login do amigo a ser adicionado.
     * @throws RuntimeException Se algum dos usu�rios n�o for encontrado.
     */
    public void addFriend(User friend) {
        if (Objects.equals(this.user, friend))
            throw new InvalidFriendSolicitationException("Usu�rio n�o pode adicionar a si mesmo como amigo.");
        else if (user.getMyFriends().getFriendSolicitation().contains(friend.getLogin())) {
            user.getMyFriends().addFriends(friend.getLogin());
            friend.getMyFriends().addFriends(user.getLogin());
        } else if (friend.getMyFriends().getFriendSolicitation().contains(user.getLogin()))
            throw new InvalidFriendSolicitationException("Usu�rio j� est� adicionado como amigo, esperando aceita��o do convite.");
        else if (user.isFriend(friend.getLogin()))
            throw new InvalidFriendSolicitationException("Usu�rio j� est� adicionado como amigo.");
        else {
            friend.getMyFriends().addFriendSolicitation(user.getLogin());
        }
    }

    public Community createCommunity(String name, String description){
        Community newCommunity = new Community(name, description, this.user.getLogin());
        newCommunity.addMember(this.user);
        return newCommunity;
    }
}
