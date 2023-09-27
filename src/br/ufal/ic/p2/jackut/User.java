package br.ufal.ic.p2.jackut;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

/**
 * A classe User representa um usuário do sistema de gerenciamento.
 *
 * @author Gustavo Gaia
 */
public class User {
    private String name;// Nome do usuário
    private String login;// Login do usuário
    private String password;// Senha do usuário
    private Friends myFriends;
    private Map<String, String> attributes;// Atributos extras do usuário
    private Queue<Recado> messageBox;// Caixa de mensagens do usuário

    /**
     * Construtor da classe User
     */
    public User() {
    }
    /**
     * Construtor da classe User nas configurações para armazenamento JSON.
     *
     * @param login O login do usuário.
     * @param senha A senha do usuário.
     * @param nome O nome do
     */
    @JsonCreator
    public User(@JsonProperty("login") String login, @JsonProperty("senha") String senha, @JsonProperty("nome") String nome) {
        this.login = login;
        this.password = senha;
        this.name = nome;
        myFriends = new Friends();
        messageBox = new LinkedList<>();
        attributes = new HashMap<>();
    }
    /**
     * Verifica se a senha fornecida corresponde à senha do usuário.
     *
     * @param password A senha a ser verificada.
     * @return `true` se a senha estiver correta, `false` caso contrário.
     */
    public boolean verifyPassword(String password){
        return Objects.equals(password, this.password);
    }
    /**
     * Obtém a lista de amigos do usuário
     * @return lista de amigos do usuário
     */
    public Friends getMyFriends() {
        return myFriends;
    }
    /**
     * Obtém a caixa de mensagens do usuário
     * @return caixa de mensagens do usuário
     */
    public Queue<Recado> getMessageBox() {
        return messageBox;
    }
    /**
     * Atualiza o nome do usuário
     * @param name novo nome do usuário
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Atualiza o login do usuário
     * @param login novo login do usuário
     */
    public void setLogin(String login) {
        this.login = login;
    }
    /**
     * Atualiza a senha do usuário
     * @param password nova senha do usuário
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Atualiza a lista de amigos do usuário
     * @param friends nova lista de amigos do usuário
     */
    public void setFriends(Friends friends) {
        this.myFriends = friends;
    }
    /**
     * Atualiza a caixa de mensagem do usuário
     * @param messageBox caixa de mensagem do usuário
     */
    public void setMessageBox(Queue<Recado> messageBox) {
        this.messageBox = messageBox;
    }
    public String getUserAttribute(String attribute){
        if (Objects.equals(attribute, "nome")) return this.name;
        else if (Objects.equals(attribute, "senha")) return this.password;
        else if (Objects.equals(attribute, "login")) return this.login;
        else return getExtraAttribute(attribute);
    }
    public String getExtraAttribute(String attribute) {
        if (attributes.containsKey(attribute)) return attributes.get(attribute);
        else throw new RuntimeException("Atributo não preenchido.");
    }
    /**
     * Obtém os atributos extras do usuário em um mapa.
     *
     * @return Um mapa contendo os atributos extras e seus valores.
     */
    @JsonAnyGetter
    public Map<String, String> getAttributes() {
        return attributes;
    }
    /**
     * Define os atributos extras do usuário a partir de um mapa.
     *
     * @param attribute O nome do atributo.
     * @param value O valor do atributo.
     */
    @JsonAnySetter
    public void setAttributes(String attribute, String value) {
        if (attributes.containsKey(attribute)) attributes.replace(attribute, value);
        else attributes.put(attribute, value);
    }

    /**
     * Adiciona um recado à caixa de mensagens do usuário.
     *
     * @param recado O recado a ser adicionado.
     */
    public void receiveMessage(Recado recado){
        this.messageBox.add(recado);
    }
    /**
     * Adiciona um usuário autenticado como amigo de outro usuário enviando uma solicitação, deve ser confirmada
     * pelo recebedor do pedido.
     *
     * @param friend O login do amigo a ser adicionado.
     * @throws RuntimeException Se algum dos usuários não for encontrado.
     */
    public void addFriend(User friend) {
        if (Objects.equals(this, friend))
            throw new RuntimeException("Usuário não pode adicionar a si mesmo como amigo.");
        else if (myFriends.getFriendSolicitation().contains(friend.login)) {
            myFriends.addFriends(friend.login);
            friend.myFriends.addFriends(this.login);
        } else if (friend.myFriends.getFriendSolicitation().contains(this.login))
            throw new RuntimeException("Usuário já está adicionado como amigo, esperando aceitação do convite.");
        else if (isFriend(friend.login))
            throw new RuntimeException("Usuário já está adicionado como amigo.");
        else {
            friend.myFriends.addFriendSolicitation(this.login);
        }
    }
    public boolean isFriend(String friend){
        return myFriends.getFriendsList().contains(friend);
    }
    public String getFriendList(){
        ArrayList<String> friends = this.myFriends.getFriendsList();
        return friends.isEmpty() ? "{}" : "{" + String.join(",", friends) + "}";

    }
}
