package br.com.view;

import br.com.model.Tabuleiro;
import javax.swing.JFrame;

public class TelaPrincipal extends JFrame {

  //Criando configurações da tela diretamente do construtor com os métodos que foram herdados
  public TelaPrincipal() {
    Tabuleiro tabuleiro = new Tabuleiro(16, 30, 50);

    //Adicionando o agrupador para a view com o tabuleiro como parametro
    add(new PainelTabuleiro(tabuleiro));

    setTitle("Campo Minado");
    setSize(690, 438);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setVisible(true);
  }

  public static void main(String[] args) {

    new TelaPrincipal();

  }

}
