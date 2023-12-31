package br.ufal.ic.p2.jackut.services;

import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.models.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

public class Manager {

    private Map<String, User> users; // Mapa para armazenar usu�rios
    private Map<String, Session> sessions; // Mapa para armazenar sess�es
    private Map<String, Community> communities; //Mapa de Comunidades
    private File usersData, communitiesData;
    /**
     * Construtor da classe Facade.
     * Inicializa os mapas de usu�rios e sess�es e carrega os dados existente do sistema, se dispon�veis.
     */
    public Manager() {
        this.users = new HashMap<>();
        this.sessions = new HashMap<>();
        this.communities = new HashMap<>();
        this.usersData = new File("usuarios.json");
        this.communitiesData =  new File("comunidades.json");
        loadSystem();
    }
    /**
     * Carrega os dados do sistema a partir de um arquivo JSON, se ele existir, � inicializado no construtor.
     * Os dados carregados incluem usu�rios, amigos, solicita��es de amizade e mensagens.
     */
    public void loadSystem(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            if(usersData.exists() && communitiesData.exists()){
                List<User> usersList = objectMapper.readValue(usersData, new TypeReference<List<User>>() {});
                List<Community> communitiesList = objectMapper.readValue(communitiesData, new TypeReference<List<Community>>() {});

                for (User user : usersList) {
                    createUser(user.getLogin(), user.getPassword(), user.getName());
                    User newUser = users.get(user.getUserAttribute("login"));
                    newUser.setFriends(user.getMyFriends());
                    newUser.setMessageBox(user.getMessageBox());
                    for(Map.Entry<String, String> entry : user.getAttributes().entrySet()){
                        newUser.setAttributes(entry.getKey(), entry.getValue());}
                }
                for (Community community : communitiesList){
                    Community newCommunity = new Community(community.getName(), community.getDescription(), community.getManager());
                    communities.put(newCommunity.getName(), newCommunity);
                    newCommunity.setMembers(community.getMembers());
                }
                System.err.println("Dados carregados com sucesso");
            }
        } catch (IOException e){
            System.err.println("Erro ao carregar dados do JSON.");
            e.printStackTrace();
        }
    }
    /**
     * M�todo zerarSistema exclui todos os dados do sistema, limpando os mapas de usu�rios, sess�es e arquivo de dados.
     */
    public void cleanSystem(){
        users.clear();
        sessions.clear();
        communities.clear();
        usersData.delete();
        communitiesData.delete();
    }
    /**
     * Encerra o sistema, salvando os dados em um arquivo JSON.
     */
    public void closeSystem() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            List<User> usersList = new ArrayList<>(users.values());
            List<Community> communityList = new ArrayList<>(communities.values());

            objectMapper.writeValue(usersData, usersList);
            objectMapper.writeValue(communitiesData, communityList);

            System.out.println("Todos os dados foram salvos.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados.");
            e.printStackTrace();
        }
    }
    /**
     * Cria um novo usu�rio com as informa��es fornecidas e o adiciona ao sistema.
     *
     * @param login O login do novo usu�rio.
     * @param password A senha do novo usu�rio.
     * @param name O nome do novo usu�rio.
     * @throws RuntimeException Se o login ou a senha forem inv�lidos ou se um usu�rio com o mesmo login j� existir.
     */
    public void createUser(String login, String password, String name){
        if (!users.containsKey(login)) {
            if(login == null) throw new InvalidCredentialException("Login inv�lido.");
            if (password == null) throw new InvalidCredentialException("Senha inv�lida.");
            User user = new User(login, password, name);
            users.put(login, user);
        }
        else throw new InvalidCredentialException("Conta com esse nome j� existe.");
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
        else throw new InvalidCredentialException("Login ou senha inv�lidos.");
    }
    public boolean verifyUser(String login){
        return users.containsKey(login);
    }

    public void createCommunity(String session, String name, String description) {
        if (communities.containsKey(name)) {
            throw new InvalidCommunityException("Comunidade com esse nome j� existe.");
        } else{
            Community community = getSession(session).createCommunity(name, description);
            communities.put(name, community);
        }
    }
    public Community getCommunity(String name) {
        if(communities.containsKey(name)) return communities.get(name);
        else throw new InvalidCommunityException("Comunidade n�o existe.");
    }
}
