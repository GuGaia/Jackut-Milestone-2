package br.ufal.ic.p2.jackut;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

/**
 * A classe User representa um usu�rio do sistema de gerenciamento.
 *
 * @author Gustavo Gaia
 */
public class User {
    private String name;// Nome do usu�rio
    private String login;// Login do usu�rio
    private String password;// Senha do usu�rio
    private Friends friends;
    private Map<String, String> attributes;// Atributos extras do usu�rio
    private Queue<Recado> messageBox;// Caixa de mensagens do usu�rio

    /**
     * Construtor da classe User
     */
    public User() {
    }
    /**
     * Construtor da classe User nas configura��es para armazenamento JSON.
     *
     * @param login O login do usu�rio.
     * @param senha A senha do usu�rio.
     * @param nome O nome do
     */
    @JsonCreator
    public User(@JsonProperty("login") String login, @JsonProperty("senha") String senha, @JsonProperty("nome") String nome) {
        this.login = login;
        this.password = senha;
        this.name = nome;
        friends = new Friends();
        messageBox = new LinkedList<>();
        attributes = new HashMap<>();
    }
    /**
     * Verifica se a senha fornecida corresponde � senha do usu�rio.
     *
     * @param password A senha a ser verificada.
     * @return `true` se a senha estiver correta, `false` caso contr�rio.
     */
    public boolean verifyPassword(String password){
        return Objects.equals(password, this.password);
    }
    /**
     * Obt�m a lista de amigos do usu�rio
     * @return lista de amigos do usu�rio
     */
    public Friends getFriends() {
        return friends;
    }
    /**
     * Obt�m a caixa de mensagens do usu�rio
     * @return caixa de mensagens do usu�rio
     */
    public Queue<Recado> getMessageBox() {
        return messageBox;
    }
    /**
     * Atualiza o nome do usu�rio
     * @param name novo nome do usu�rio
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Atualiza o login do usu�rio
     * @param login novo login do usu�rio
     */
    public void setLogin(String login) {
        this.login = login;
    }
    /**
     * Atualiza a senha do usu�rio
     * @param password nova senha do usu�rio
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Atualiza a lista de amigos do usu�rio
     * @param friends nova lista de amigos do usu�rio
     */
    public void setFriends(Friends friends) {
        this.friends = friends;
    }
    /**
     * Atualiza a caixa de mensagem do usu�rio
     * @param messageBox caixa de mensagem do usu�rio
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
        else throw new RuntimeException("Atributo n�o preenchido.");
    }
    /**
     * Obt�m os atributos extras do usu�rio em um mapa.
     *
     * @return Um mapa contendo os atributos extras e seus valores.
     */
    @JsonAnyGetter
    public Map<String, String> getAttributes() {
        return attributes;
    }
    /**
     * Define os atributos extras do usu�rio a partir de um mapa.
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
     * Adiciona um recado � caixa de mensagens do usu�rio.
     *
     * @param recado O recado a ser adicionado.
     */
    public void receiveMessage(Recado recado){
        this.messageBox.add(recado);
    }
    public void addFriend(User amigo) {
        if (Objects.equals(user.getLogin(), amigo))
            throw new RuntimeException("Usu�rio n�o pode adicionar a si mesmo como amigo.");
        else if (user.getFriendSolicitation().contains(amigo)) {
            user.addFriends(amigo);
            friendUser.addFriends(user.getLogin());
        } else if (friendUser.getFriendSolicitation().contains(user.getLogin()))
            throw new RuntimeException("Usu�rio j� est� adicionado como amigo, esperando aceita��o do convite.");
        else if (ehAmigo(user.getLogin(), amigo))
            throw new RuntimeException("Usu�rio j� est� adicionado como amigo.");
        else {
            friendUser.addFriendSolicitation(user.getLogin());
        }
    }
}
