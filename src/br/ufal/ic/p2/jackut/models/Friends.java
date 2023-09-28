package br.ufal.ic.p2.jackut.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class Friends {
    private ArrayList<String> friendsList;// Lista de amigos do usuário
    private ArrayList<String> friendSolicitation;// Lista de solicitações de amizade pendentes

    @JsonCreator
    public Friends() {
       friendsList = new ArrayList<>();
       friendSolicitation = new ArrayList<>();
    }
    /**
     * Obtém a lista de amigos do usuário
     * @return lista de amigos do usuário
     */
    public ArrayList<String> getFriendsList() {
        return friendsList;
    }
    /**
     * Obtém a lista de solicitações de amizade do usuário
     * @return lista de solicitações de amizade do usuário
     */
    public ArrayList<String> getFriendSolicitation() {
        return friendSolicitation;
    }

    /**
     * Atualiza a lista de amigos do usuário
     * @param friends nova lista de amigos do usuário
     */
    public void setFriends(ArrayList<String> friends) {
        this.friendsList = friends;
    }
    /**
     * Atualiza a lista de solicitações de amizade do usuário
     * @param friendSolicitation nova lista de solicitações de amizade do usuário
     */
    public void setFriendSolicitation(ArrayList<String> friendSolicitation) {
        this.friendSolicitation = friendSolicitation;
    }
    /**
     * Adiciona um amigo à lista de amigos do usuário, removendo a solicitação de amizade, se existir.
     *
     * @param friend O login do amigo a ser adicionado.
     */
    public void addFriends(String friend) {
        this.friendSolicitation.remove(friend);
        this.friendsList.add(friend);
    }
    /**
     * Adiciona uma solicitação de amizade à lista de solicitações pendentes.
     *
     * @param friendSolicitation O login do usuário que enviou a solicitação de amizade.
     */
    public void addFriendSolicitation(String friendSolicitation) {
        this.friendSolicitation.add(friendSolicitation);
    }



}
