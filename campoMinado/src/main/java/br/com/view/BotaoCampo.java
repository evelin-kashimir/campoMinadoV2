package br.com.view;

import br.com.model.Campo;
import br.com.model.CampoEvento;
import br.com.model.CampoObservador;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class BotaoCampo extends JButton implements CampoObservador {

  //Cores dos campos em RGB com a classe Color
  private final Color BG_PADRAO = new Color(190, 190, 190);
  private final Color BG_MARCADO = new Color(8, 179, 247);
  private final Color BG_EXPLODIR = new Color(189, 66, 68);
  private final Color TEXTO_VERDE = new Color(0, 100, 0);
  private Campo campo;

  public BotaoCampo(Campo campo) {
    this.campo = campo;
    setBackground(BG_PADRAO);
    setBorder(BorderFactory.createBevelBorder(0)); //criando bordas
    campo.registrarObservador(this);

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
  }

  private void aplicarEstiloExplodir() {
  }

  private void aplicarEstiloMarcar() {
  }

  private void aplicarEstiloAbrir() {
  }
}
