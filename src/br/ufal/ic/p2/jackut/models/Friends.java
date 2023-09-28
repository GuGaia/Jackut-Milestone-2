package br.ufal.ic.p2.jackut.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class Friends {
    private ArrayList<String> friendsList;// Lista de amigos do usu�rio
    private ArrayList<String> friendSolicitation;// Lista de solicita��es de amizade pendentes

    @JsonCreator
    public Friends() {
       friendsList = new ArrayList<>();
       friendSolicitation = new ArrayList<>();
    }
    /**
     * Obt�m a lista de amigos do usu�rio
     * @return lista de amigos do usu�rio
     */
    public ArrayList<String> getFriendsList() {
        return friendsList;
    }
    /**
     * Obt�m a lista de solicita��es de amizade do usu�rio
     * @return lista de solicita��es de amizade do usu�rio
     */
    public ArrayList<String> getFriendSolicitation() {
        return friendSolicitation;
    }

    /**
     * Atualiza a lista de amigos do usu�rio
     * @param friends nova lista de amigos do usu�rio
     */
    public void setFriends(ArrayList<String> friends) {
        this.friendsList = friends;
    }
    /**
     * Atualiza a lista de solicita��es de amizade do usu�rio
     * @param friendSolicitation nova lista de solicita��es de amizade do usu�rio
     */
    public void setFriendSolicitation(ArrayList<String> friendSolicitation) {
        this.friendSolicitation = friendSolicitation;
    }
    /**
     * Adiciona um amigo � lista de amigos do usu�rio, removendo a solicita��o de amizade, se existir.
     *
     * @param friend O login do amigo a ser adicionado.
     */
    public void addFriends(String friend) {
        this.friendSolicitation.remove(friend);
        this.friendsList.add(friend);
    }
    /**
     * Adiciona uma solicita��o de amizade � lista de solicita��es pendentes.
     *
     * @param friendSolicitation O login do usu�rio que enviou a solicita��o de amizade.
     */
    public void addFriendSolicitation(String friendSolicitation) {
        this.friendSolicitation.add(friendSolicitation);
    }



}
