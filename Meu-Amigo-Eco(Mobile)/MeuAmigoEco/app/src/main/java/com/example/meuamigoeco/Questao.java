package com.example.meuamigoeco;

public class Questao {
    String questao, opcaoA, opcaoB, opcaoC, opcaoD;
    int questaoCorreta;

    public Questao(String questao, String opcaoA, String opcaoB, String opcaoC, String opcaoD, int questaoCorreta) {
        this.questao = questao;
        this.opcaoA = opcaoA;
        this.opcaoB = opcaoB;
        this.opcaoC = opcaoC;
        this.opcaoD = opcaoD;
        this.questaoCorreta = questaoCorreta;
    }

    public String getQuestao() {
        return questao;
    }

    public void setQuestao(String questao) {
        this.questao = questao;
    }

    public String getOpcaoA() {
        return opcaoA;
    }

    public void setOpcaoA(String opcaoA) {
        this.opcaoA = opcaoA;
    }

    public String getOpcaoB() {
        return opcaoB;
    }

    public void setOpcaoB(String opcaoB) {
        this.opcaoB = opcaoB;
    }

    public String getOpcaoC() {
        return opcaoC;
    }

    public void setOpcaoC(String opcaoC) {
        this.opcaoC = opcaoC;
    }

    public String getOpcaoD() {
        return opcaoD;
    }

    public void setOpcaoD(String opcaoD) {
        this.opcaoD = opcaoD;
    }

    public int getQuestaoCorreta() {
        return questaoCorreta;
    }

    public void setQuestaoCorreta(int questaoCorreta) {
        this.questaoCorreta = questaoCorreta;
    }
}
