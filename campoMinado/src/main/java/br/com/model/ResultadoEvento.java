package br.com.model;

public class ResultadoEvento {

  private boolean ganhou;

  public ResultadoEvento(boolean ganhou) {
    this.ganhou = ganhou;
  }

  public boolean isGanhou() {
    return ganhou;
  }
}
