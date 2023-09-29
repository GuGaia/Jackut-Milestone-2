package br.ufal.ic.p2.jackut.services;

import java.util.ArrayList;
import java.util.Set;

/**
 * A classe Facade oferece uma interface para acessar as funcionalidades do sistema.
 *
 * @author Gustavo Gaia
 */
public class Facade {
    /**
     * Mensagem de erro que indica que um usuário não foi encontrado no sistema.
     */
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
        return system.getUser(login).getUserAttribute(atributo);
    }
    /**
     * Abre uma sessão para um usuário autenticado.
     *
     * @param login O login do usuário.
     * @param senha A senha do usuário.
     * @return O ID da sessão.
     */
    public String abrirSessao (String login, String senha){
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
        system.getSession(Id).editProfile(atributo, valor, system);
    }
    /**
     * Verifica se um usuário é amigo de outro usuário.
     *
     * @param login O login do primeiro usuário.
     * @param amigo O login do segundo usuário.
     * @return `true` se forem amigos, `false` caso contrário.
     */
    public boolean ehAmigo(String login, String amigo){
        return system.getUser(login). isFriend(amigo);
    }
    /**
     * Obtém a lista de amigos de um usuário.
     *
     * @param login O login do usuário.
     * @return Uma representação da lista de amigos.
     */
    public String getAmigos(String login){
        return  system.getUser(login).getFriendList();
    }

    public void adicionarAmigo(String id, String login) {
        system.getSession(id).addFriend(system.getUser(login));
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
        system.getSession(id).getUser().sendMessage(system.getUser(destinatario), mensagem);
    }
    /**
     * Lê o primeiro recado da caixa de mensagens de um usuário.
     *
     * @param id O ID da sessão do usuário.
     * @return O conteúdo do recado lido.
     * @throws RuntimeException Se não houver recados na caixa de mensagens.
     */
    public String lerRecado(String id){
        return system.getSession(id).getUser().readMessage();
    }

    public void criarComunidade(String sessao, String nome, String descricao){
        system.createCommunity(sessao, nome, descricao);
    }
    /**
     * Encerra o sistema, salvando os dados em um arquivo JSON.
     */
    public void encerrarSistema() {
        system.closeSystem();
    }
    public String getDescricaoComunidade(String nome){
        return system.getCommunity(nome).getDescription();
    }
    public String getDonoComunidade(String nome){
        return system.getCommunity(nome).getManager();
    }
    public String getMembrosComunidade(String nome) {
        ArrayList<String> members = system.getCommunity(nome).getMembers();
        return "{" + String.join(",", members) + "}";
    }
}

