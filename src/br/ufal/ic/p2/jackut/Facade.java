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
     * Mensagem de erro que indica que um usu�rio n�o foi encontrado no sistema.
     */
    public static final String USER_NOT_FOUND= "Usu�rio n�o cadastrado.";
    private final Manager system;
    /**
     * Construtor da classe Facade.
     * Inicializa o sistema.
     */
    public Facade() {
        this.system = new Manager();
    }
    /**
     * M�todo zerarSistema exclui todos os dados do sistema, limpando os mapas de usu�rios, sess�es e arquivo de dados.
     */
    public void zerarSistema(){
        system.cleanSystem();
    }
    /**
     * Cria um novo usu�rio com as informa��es fornecidas e o adiciona ao sistema.
     */
    public void criarUsuario(String login, String senha, String nome){
        system.createUser(login, senha, nome);
    }
    /**
     * Obt�m o valor de um atributo espec�fico para um usu�rio desejado a partir de seu login.
     *
     * @param login O login do usu�rio.
     * @param atributo O atributo desejado ("nome", "senha", "login" ou atributo extra).
     * @return O valor do atributo solicitado.
     */
    public String getAtributoUsuario(String login, String atributo){
        User user = system.getUser(login);
        return user.getUserAttribute(atributo);
    }
    /**
     * Abre uma sess�o para um usu�rio autenticado.
     *
     * @param login O login do usu�rio.
     * @param senha A senha do usu�rio.
     * @return O ID da sess�o.
     */
    private String abrirSessao (String login, String senha) {
        return system.openSession(login, senha);
    }
    /**
     * Edita o perfil de um usu�rio autenticado.
     *
     * @param Id O ID da sess�o do usu�rio.
     * @param atributo O atributo a ser editado ("nome", "senha", "login" ou atributo extra).
     * @param valor O novo valor para o atributo.
     */
    public void editarPerfil(String Id, String atributo, String valor){
        Session session = system.getSession(Id);
        session.editProfile(atributo, valor, system);
    }
    /**
     * Verifica se um usu�rio � amigo de outro usu�rio.
     *
     * @param login O login do primeiro usu�rio.
     * @param amigo O login do segundo usu�rio.
     * @return `true` se forem amigos, `false` caso contr�rio.
     */
    public boolean ehAmigo(String login, String amigo){
        return system.getUser(login). isFriend(amigo);
    }
    /**
     * Obt�m a lista de amigos de um usu�rio.
     *
     * @param login O login do usu�rio.
     * @return Uma representa��o da lista de amigos.
     */
    public String getAmigos(String login){
        User user = system.getUser(login);
        return  user.getFriendList();
    }
    /**
     * Envia um recado de um usu�rio para outro.
     *
     * @param id O ID da sess�o do remetente.
     * @param destinatario O login do destinat�rio do recado.
     * @param mensagem O conte�do da mensagem.
     * @throws RuntimeException Se os usu�rios n�o forem encontrados.
     */
    public void enviarRecado(String id, String destinatario, String mensagem){
        User sender = sessions.get(id);
        User receiver = users.get(destinatario);
        if (sender == receiver) throw new RuntimeException("Usu�rio n�o pode enviar recado para si mesmo.");
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
     * L� o primeiro recado da caixa de mensagens de um usu�rio.
     *
     * @param id O ID da sess�o do usu�rio.
     * @return O conte�do do recado lido.
     * @throws RuntimeException Se n�o houver recados na caixa de mensagens.
     */
    public String lerRecado(String id){
        User user = sessions.get(id);
        Recado recado = user.getMessageBox().poll();
        if(recado == null) throw new RuntimeException("N�o h� recados.");
        else return recado.getMensagem();

    }
    /**
     * Encerra o sistema, salvando os dados em um arquivo JSON.
     */
    public void encerrarSistema() {
        system.closeSystem();
    }
}

