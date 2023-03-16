package br.com.model;

import java.util.ArrayList;
import java.util.List;

public class Campo {
  private final int LINHA;
  private final int COLUNA;

  private boolean minado = false;
  private boolean aberto = false;
  private boolean marcado = false;

  private List<Campo> vizinhos = new ArrayList<>();

  /* Lista de observadores com interface funcional, BiConsumer recebe dois parametros e não retorna nada
  private List<BiConsumer<Campo, CampoEvento>> observadores = new ArrayList<>();
  */

  //Lista de observadores com interface personalizada - implementada
  private List<CampoObservador> observadores = new ArrayList<>();

  Campo(int linha, int coluna) {
    this.LINHA = linha;
    this.COLUNA = coluna;
  }

  public void registrarObservador(CampoObservador observador) {
    observadores.add(observador);
  }

  //Sempre que algum evento ocorrer, será disparado esse método
  private void notificarObservadores(CampoEvento evento){
    observadores.stream()
        .forEach(obs -> obs.eventoOcorreu(this, evento));
  }

  boolean addVizinho(Campo vizinho){
    boolean linhaDiferente = this.LINHA != vizinho.LINHA;
    boolean colunaDiferente = this.COLUNA != vizinho.COLUNA;

    boolean diagonal = linhaDiferente && colunaDiferente;

    int deltaLinha = Math.abs(this.LINHA - vizinho.LINHA);
    int deltaColuna = Math.abs(this.COLUNA - vizinho.COLUNA);
    int deltaGeral = deltaColuna + deltaLinha;

    if(deltaGeral == 1 && !diagonal) {
      vizinhos.add(vizinho);
      return true;
    } else if (deltaGeral == 2 && diagonal) {
      vizinhos.add(vizinho);
      return true;
    } else {
      return false;
    }
  }

  public void alternarMarcacao() {
    if(!aberto) {
      this.marcado = !this.marcado;

      if(marcado) {
        notificarObservadores(CampoEvento.MARCAR);
      } else {
        notificarObservadores(CampoEvento.DESMARCAR);
      }
    }
  }

  public boolean abrir() {
    if(!this.marcado && !this.aberto) {

      if(this.minado) {
        notificarObservadores(CampoEvento.EXPLODIR);
        return true;
      }

      setAberto(true);

      if(vizinhancaSegura()) {
        vizinhos.forEach(Campo::abrir);
      }

      return true;
    } else {
      return false;
    }
  }

  public boolean vizinhancaSegura() {
    return vizinhos.stream().noneMatch(v -> v.minado);
  }

  public boolean isMarcado(){
    return this.marcado;
  }

  public boolean isAberto(){
    return this.aberto;
  }

  public boolean isMinado(){
    return this.minado;
  }

  public void setMarcado(boolean marcado) {
    this.marcado = marcado;
  }

  public void setMinado(boolean minado) {
    this.minado = minado;
  }

  public void setAberto(boolean aberto) {
    this.aberto = aberto;

    if (aberto){
      notificarObservadores(CampoEvento.ABRIR);
    }
  }

  public int getCOLUNA() {
    return COLUNA;
  }

  public int getLINHA() {
    return LINHA;
  }

  boolean objetivoAlcancado() {
    boolean desvendado = !minado && aberto;
    boolean protegido = minado && marcado;
    return desvendado || protegido;
  }

  //Retorna a qtd de minas no jogo
  public int minasNaVizinhanca() {
    return (int) vizinhos.stream().filter(v -> v.minado).count();
  }

  void reiniciar() {
    this.aberto = false;
    this.marcado = false;
    this.minado = false;
    notificarObservadores(CampoEvento.REINICIAR);
  }
}
