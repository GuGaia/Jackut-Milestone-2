package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.InvalidCredentialException;
import br.ufal.ic.p2.jackut.exceptions.InvalidMessageException;
import br.ufal.ic.p2.jackut.exceptions.MessageNotFoundException;
import br.ufal.ic.p2.jackut.exceptions.UserNotFoundException;
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
    private Friends myFriends;
    private Map<String, String> attributes;// Atributos extras do usu�rio
    private Queue<Message> messageBox;// Caixa de mensagens do usu�rio

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
        myFriends = new Friends();
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
    public Friends getMyFriends() {
        return myFriends;
    }
    /**
     * Obt�m a caixa de mensagens do usu�rio
     * @return caixa de mensagens do usu�rio
     */
    public Queue<Message> getMessageBox() {
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
        this.myFriends = friends;
    }

    /**
     * Atualiza a caixa de mensagem do usu�rio
     * @param messageBox caixa de mensagem do usu�rio
     */
    public void setMessageBox(Queue<Message> messageBox) {
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
            else throw new InvalidCredentialException("Atributo n�o preenchido.");
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
     * @param message O recado a ser adicionado.
     */
    public void receiveMessage(Message message){
        this.messageBox.add(message);
    }

    public boolean isFriend(String friend){
        return myFriends.getFriendsList().contains(friend);
    }
    public String getFriendList(){
        ArrayList<String> friends = this.myFriends.getFriendsList();
        return friends.isEmpty() ? "{}" : "{" + String.join(",", friends) + "}";
    }

    /**
     * Envia um recado para outro usu�rio.
     *
     * @param receiver O login do destinat�rio do recado.
     * @param messageContent O conte�do da mensagem.
     * @throws RuntimeException Se os usu�rios n�o forem encontrados.
     */
    public void sendMessage(User receiver, String messageContent){
        if (this == receiver) throw new InvalidMessageException("Usu�rio n�o pode enviar recado para si mesmo.");
        if(receiver != null){
            Message message = new Message(this.login, messageContent);
            receiver.receiveMessage(message);
        }
            else throw new UserNotFoundException();
    }
    /**
     * L� o primeiro recado da caixa de mensagens de um usu�rio.
     * @throws RuntimeException Se n�o houver recados na caixa de mensagens.
     */
    public String readMessage(){
        Message message = this.messageBox.poll();
        if(message == null) throw new MessageNotFoundException("N�o h� recados.");
        else return message.getMessage();

    }
    public String getLogin() {
        return login;
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
}
