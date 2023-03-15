package br.com.view;

import br.com.model.Tabuleiro;
import java.awt.GridLayout;
import javax.swing.JPanel;

//JPanel é um agrupador de componentes, pra esse caso, um agrupador de botões
public class PainelTabuleiro extends JPanel {

  public PainelTabuleiro(Tabuleiro tabuleiro) {

    //seta um layout para a janela, GridLayout recebe a qtd de linhas e colunas como parâmetro
    setLayout(new GridLayout(tabuleiro.getLinhas(), tabuleiro.getColunas()));

    //passando um campo para a classe Botao campo que renderiza o botao na tela
    tabuleiro.paraCadaCampo(c -> add(new BotaoCampo(c)));

    tabuleiro.registrarObservador(e -> {
      //TO DO mostrar resuçtado pro usuário!
    });

  }
}
