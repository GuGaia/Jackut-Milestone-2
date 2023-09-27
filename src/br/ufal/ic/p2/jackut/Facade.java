package br.ufal.ic.p2.jackut;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.*;

/**
 * A classe Facade oferece uma interface para acessar as funcionalidades do sistema.
 *
 * @author Gustavo Gaia
 */
public class Facade {
    /**
     * Mensagem de erro que indica que um usuário não foi encontrado no sistema.
     */
    public static final String USER_NOT_FOUND= "Usuário não cadastrado.";
    private final Manager system;
    /**
     * Construtor da classe Facade.
     * Inicializa o sistema.
     */
    public Facade() {
        this.system = new Manager();
    }
    /**
     * Método zerarSistema exclui todos os dados do sistema, limpando os mapas de usuários, sessões e arquivo de dados.
     */
    public void zerarSistema(){
        system.cleanSystem();
    }
    /**
     * Cria um novo usuário com as informações fornecidas e o adiciona ao sistema.
     */
    public void criarUsuario(String login, String senha, String nome){
        system.createUser(login, senha, nome);
    }
    /**
     * Obtém o valor de um atributo específico para um usuário desejado a partir de seu login.
     *
     * @param login O login do usuário.
     * @param atributo O atributo desejado ("nome", "senha", "login" ou atributo extra).
     * @return O valor do atributo solicitado.
     */
    public String getAtributoUsuario(String login, String atributo){
        User user = system.getUser(login);
        return user.getUserAttribute(atributo);
    }
    /**
     * Abre uma sessão para um usuário autenticado.
     *
     * @param login O login do usuário.
     * @param senha A senha do usuário.
     * @return O ID da sessão.
     */
    private String abrirSessao (String login, String senha) {
        return system.openSession(login, senha);
    }
    /**
     * Edita o perfil de um usuário autenticado.
     *
     * @param Id O ID da sessão do usuário.
     * @param atributo O atributo a ser editado ("nome", "senha", "login" ou atributo extra).
     * @param valor O novo valor para o atributo.
     */
    public void editarPerfil(String Id, String atributo, String valor){
        Session session = system.getSession(Id);
        session.editProfile(atributo, valor, system);
    }
    /**
     * Verifica se um usuário é amigo de outro usuário.
     *
     * @param login O login do primeiro usuário.
     * @param amigo O login do segundo usuário.
     * @return `true` se forem amigos, `false` caso contrário.
     */
    public boolean ehAmigo(String login, String amigo){
        return system.getUser(login).getFriends().contains(amigo);
    }

    /**
     * Adiciona um usuário autenticado como amigo de outro usuário enviando uma solicitação, deve ser confirmada
     * pelo recebedor do pedido.
     *
     * @param id O ID da sessão do usuário.
     * @param amigo O login do amigo a ser adicionado.
     * @throws RuntimeException Se algum dos usuários não for encontrado.
     */
    public void adicionarAmigo(String id, String amigo){
        User user = system.getSession(id).getUser();
        User friend = system.getUser(amigo);
        user.addFriend(friend);

        if (user == null)throw new RuntimeException(USER_NOT_FOUND);
        User friendUser = users.get(amigo);
        if (friendUser != null) {
            if (Objects.equals(user.getLogin(), amigo))
                throw new RuntimeException("Usuário não pode adicionar a si mesmo como amigo.");
            else if (user.getFriendSolicitation().contains(amigo)) {
                user.addFriends(amigo);
                friendUser.addFriends(user.getLogin());
            } else if (friendUser.getFriendSolicitation().contains(user.getLogin()))
                throw new RuntimeException("Usuário já está adicionado como amigo, esperando aceitação do convite.");
            else if (ehAmigo(user.getLogin(), amigo))
                throw new RuntimeException("Usuário já está adicionado como amigo.");
            else {
                friendUser.addFriendSolicitation(user.getLogin());
            }
        }
        else{ throw new RuntimeException(USER_NOT_FOUND);}
    }
    /**
     * Obtém a lista de amigos de um usuário.
     *
     * @param login O login do usuário.
     * @return Uma representação da lista de amigos.
     */
    public String getAmigos(String login){
        User user = users.get(login);
        ArrayList<String> friends = user.getFriends();
        return friends.isEmpty() ? "{}" : "{" + String.join(",", friends) + "}";
    }
    /**
     * Envia um recado de um usuário para outro.
     *
     * @param id O ID da sessão do remetente.
     * @param destinatario O login do destinatário do recado.
     * @param mensagem O conteúdo da mensagem.
     * @throws RuntimeException Se os usuários não forem encontrados.
     */
    public void enviarRecado(String id, String destinatario, String mensagem){
        User sender = sessions.get(id);
        User receiver = users.get(destinatario);
        if (sender == receiver) throw new RuntimeException("Usuário não pode enviar recado para si mesmo.");
        if(sender != null){
            if(receiver != null){
                Recado recado = new Recado(sender.getLogin(), mensagem);
                receiver.receiveMessage(recado);
            }
            else throw new RuntimeException(USER_NOT_FOUND);
        }
        else throw new RuntimeException(USER_NOT_FOUND);
    }
    /**
     * Lê o primeiro recado da caixa de mensagens de um usuário.
     *
     * @param id O ID da sessão do usuário.
     * @return O conteúdo do recado lido.
     * @throws RuntimeException Se não houver recados na caixa de mensagens.
     */
    public String lerRecado(String id){
        User user = sessions.get(id);
        Recado recado = user.getMessageBox().poll();
        if(recado == null) throw new RuntimeException("Não há recados.");
        else return recado.getMensagem();

    }
    /**
     * Encerra o sistema, salvando os dados em um arquivo JSON.
     */
    public void encerrarSistema() {
        system.closeSystem();
    }
}

