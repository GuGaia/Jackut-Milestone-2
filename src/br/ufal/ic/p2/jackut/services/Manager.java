package br.ufal.ic.p2.jackut.services;

import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.models.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Manager {

    private Map<String, User> users; // Mapa para armazenar usuários
    private Map<String, Session> sessions; // Mapa para armazenar sessões

    private ArrayList<Community> communities; //Lista de Comunidades
    private File usersData;
    /**
     * Construtor da classe Facade.
     * Inicializa os mapas de usuários e sessões e carrega os dados existente do sistema, se disponíveis.
     */
    public Manager() {
        this.users = new HashMap<>();
        this.sessions = new HashMap<>();
        loadSystem();
    }
    /**
     * Carrega os dados do sistema a partir de um arquivo JSON, se ele existir, é inicializado no construtor.
     * Os dados carregados incluem usuários, amigos, solicitações de amizade e mensagens.
     */
    public void loadSystem(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.usersData = new File("usuarios.json");

            if(usersData.exists()){
                List<User> usersList = objectMapper.readValue(usersData, new TypeReference<List<User>>() {});

                for (User user : usersList) {
                    createUser(user.getUserAttribute("login"), user.getUserAttribute("senha"), user.getUserAttribute("nome"));
                    User newUser = users.get(user.getUserAttribute("login"));
                    newUser.setFriends(user.getMyFriends());
                    //newUser.friends.setFriendSolicitation(user.friends.getFriendSolicitation());
                    newUser.setMessageBox(user.getMessageBox());
                    for(Map.Entry<String, String> entry : user.getAttributes().entrySet()){
                        newUser.setAttributes(entry.getKey(), entry.getValue());}
                }
            }
        } catch (IOException e){
            System.err.println("Erro ao carregar dados do JSON.");
            e.printStackTrace();
        }
    }
    /**
     * Método zerarSistema exclui todos os dados do sistema, limpando os mapas de usuários, sessões e arquivo de dados.
     */
    public void cleanSystem(){
        users.clear();
        sessions.clear();
        this.usersData.deleteOnExit();
    }
    /**
     * Encerra o sistema, salvando os dados em um arquivo JSON.
     */
    public void closeSystem() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.usersData = new File("usuarios.json");

            List<User> usersList = new ArrayList<>(users.values());

            objectMapper.writeValue(usersData, usersList);

            System.out.println("Todos os dados foram salvos.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados.");
            e.printStackTrace();
        }
    }
    /**
     * Cria um novo usuário com as informações fornecidas e o adiciona ao sistema.
     *
     * @param login O login do novo usuário.
     * @param password A senha do novo usuário.
     * @param name O nome do novo usuário.
     * @throws RuntimeException Se o login ou a senha forem inválidos ou se um usuário com o mesmo login já existir.
     */
    public void createUser(String login, String password, String name){
        if (!users.containsKey(login)) {
            if(login == null) throw new InvalidCredentialException("Login inválido.");
            if (password == null) throw new InvalidCredentialException("Senha inválida.");
            User user = new User(login, password, name);
            users.put(login, user);
        }
        else throw new InvalidCredentialException("Conta com esse nome já existe.");
    }
    User getUser(String login){
        if (users.containsKey(login)) return users.get(login);
        else throw new UserNotFoundException();
    }
    Session getSession(String id){
        if (sessions.containsKey(id)) return sessions.get(id);
        else throw new UserNotFoundException();
    }
    public String openSession (String login, String password) {
        User user = users.get(login);
        if(user != null && user.verifyPassword(password)){
            Session session = new Session(user);
            sessions.put(session.getID(), session);
            return session.getID();
        }
        else throw new InvalidCredentialException("Login ou senha inválidos.");
    }
    public boolean verifyUser(String login){
        return users.containsKey(login);
    }


}
