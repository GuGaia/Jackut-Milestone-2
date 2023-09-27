package br.ufal.ic.p2.jackut;

import java.util.Objects;

public class Session {
   private User user;
   private String ID;

    public Session(User user){
        this.user = user;
        this.ID = generateSessionId(user.getUserAttribute("login"));
    }
    /**
     * Gera um ID único de sessão combinando o login e o momento da criação.
     *
     * @param login O login do usuário.
     */
    private String generateSessionId(String login) {
        long timestamp = System.currentTimeMillis();
        return login + "_" + timestamp;
    }
    public User getUser() {
        return user;
    }
    public String getID() {
        return ID;
    }
    public void editProfile(String atributte, String valor, Manager system){

        if (Objects.equals(atributte, "nome")) this.user.setName(valor);
        else if (Objects.equals(atributte, "senha")) this.user.setPassword(valor);
        else if (Objects.equals(atributte, "login")) {
            if (system.verifyUser(valor)) throw new RuntimeException("Login inválido.");
            else this.user.setLogin(valor);
        }
        else this.user.setAttributes(atributte, valor);
    }
}
