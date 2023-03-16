package br.com.view;

import br.com.model.Campo;
import br.com.model.CampoEvento;
import br.com.model.CampoObservador;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BotaoCampo extends JButton implements CampoObservador, MouseListener {

  //Cores dos campos em RGB com a classe Color
  private final Color BG_PADRAO = new Color(184, 184, 184);
  private final Color BG_MARCADO = new Color(8, 179, 247);
  private final Color BG_EXPLODIR = new Color(189, 66, 68);
  private final Color TEXTO_VERDE = new Color(0, 100, 0);
  private Campo campo;

  public BotaoCampo(Campo campo) {
    this.campo = campo;
    setBackground(BG_PADRAO);
    setBorder(BorderFactory.createBevelBorder(0)); //criando bordas
    campo.registrarObservador(this);
    addMouseListener(this);
  }

  @Override
  public void eventoOcorreu(Campo campo, CampoEvento evento) {
    switch (evento) {
      case ABRIR:
        aplicarEstiloAbrir();
        break;
      case MARCAR:
        aplicarEstiloMarcar();
        break;
      case EXPLODIR:
        aplicarEstiloExplodir();
        break;
      default:
        aplicarEstiloPadrao();
    }
  }

  private void aplicarEstiloPadrao() {
    setBorder(BorderFactory.createBevelBorder(0));
    setBackground(BG_PADRAO);
    setIcon(new ImageIcon());
    setText("");
  }

  private void aplicarEstiloExplodir() {
    ImageIcon imgBomba = new ImageIcon("C:\\bomb.png");
    setBackground(BG_EXPLODIR);
    setIcon(imgBomba);
  }

  private void aplicarEstiloMarcar() {
    ImageIcon imgBandeira = new ImageIcon("C:\\bandeira.png");
    setIcon(imgBandeira);
    setForeground(Color.WHITE);
    setBackground(BG_MARCADO);
  }

  private void aplicarEstiloAbrir() {
    setBorder(BorderFactory.createLineBorder(Color.GRAY));

    if(campo.isMinado()) {
      ImageIcon imgBomba = new ImageIcon("C:\\bomb.png");
      setIcon(imgBomba);
      return;
    }

    setBackground(BG_PADRAO);
    switch (campo.minasNaVizinhanca()) {
      case 1:
        setForeground(TEXTO_VERDE);
        break;
      case 2:
        setForeground(Color.BLUE);
        break;
      case 3:
        setForeground(Color.YELLOW);
        break;
      case 4:
      case 5:
      case 6:
        setForeground(Color.RED);
        break;
      default:
        setForeground(Color.PINK);
    }

    String valor = !campo.vizinhancaSegura() ? campo.minasNaVizinhanca() + "":"";
    setText(valor);
  }

  //Interface dos eventos do mouse
  public void mousePressed(MouseEvent evento){
    if(evento.getButton() == 1){
      System.out.println("Botão esquerdo");
      campo.abrir();
    } else {
      campo.alternarMarcacao();
      System.out.println("Outro botão!");
    }
  }

  public void mouseClicked(MouseEvent evento) {

  }

  public void mouseEntered(MouseEvent evento) {

  }

  public void mouseExited(MouseEvent evento) {

  }

  public void mouseReleased(MouseEvent evento) {

  }
}
