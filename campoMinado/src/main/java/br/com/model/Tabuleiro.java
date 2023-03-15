package br.com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro  implements CampoObservador{
  private final int linhas;
  private final int colunas;
  private final int minas;

  public final int getLinhas() {
    return linhas;
  }

  public final int getColunas() {
    return colunas;
  }

  private final List<Campo> campos = new ArrayList<>();
  private final List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();

  public Tabuleiro(int linhas, int colunas, int minas) {
    this.linhas = linhas;
    this.colunas = colunas;
    this.minas = minas;

    gerarCampos();
    assossiarVizinhos();
    distribuirMinas();
  }

  public void registrarObservador(Consumer<ResultadoEvento> observador) {
    observadores.add(observador);
  }

  public void notificarObservadores(boolean resultado) {
    observadores.stream().forEach(obs -> obs.accept(new ResultadoEvento(resultado))); //verificando se está aceito
  }


  public void abrirCampo(int linha, int coluna) {
    try {
      campos.stream()
          .filter(c -> c.getLINHA() == linha && c.getCOLUNA() == coluna)
          .findFirst().ifPresent(Campo::abrir);
    } catch (Exception e) {
      campos.forEach(campo -> campo.setAberto(true));

    }
  }

  private void mostrarMinas() {
    campos.stream().filter(c -> c.isMinado()).forEach(c -> c.setAberto(true));
  }

  public void marcarCampo(int linha, int coluna) {
    campos.stream()
        .filter(c -> c.getLINHA() == linha && c.getCOLUNA() == coluna)
        .findFirst().ifPresent(Campo::alternarMarcacao);
  }

  //Cria uma matriz de Campos
  private void gerarCampos() {
    for(int linha = 0; linha < linhas; linha++) {
      for(int coluna = 0; coluna < colunas; coluna++) {
        //Criando o campo e tranformando todos os campos em observadores
        Campo campo = new Campo(linha, coluna);
        campo.registrarObservador(this);
        campos.add(campo);
      }
    }
  }

  private void assossiarVizinhos() {
    for (Campo c1: campos) {
      for (Campo c2: campos) {
        c1.addVizinho(c2);
      }
    }
  }

  //Enquanto o número de minas criadas for menor que o que foi especificado na criação do objeto,
  //continuará criando minas.
  private void distribuirMinas() {
    long minasArmadas = 0;
    Predicate<Campo> minado = Campo::isMinado; //verificando todos os campos com o atributo minado true;

    do {
      int aleatorio = (int) (Math.random() * campos.size()); //gerando um valor randomico baseado no tamanho da lista
      campos.get(aleatorio).setMinado(true);
      minasArmadas = campos.stream().filter(minado).count();
    } while (minasArmadas < minas);
  }

  //Todos os campos estão com objetivo alcançado, baseado no método criado na classe Campo
  public boolean objetivoAlcancado() {
    return campos.stream().allMatch(Campo::objetivoAlcancado);
  }

  public void reinicializarJogo() {
    campos.stream().forEach(Campo::reiniciar);
    distribuirMinas();
  }

  public List<Campo> getCampos() {
    return campos;
  }

  @Override
  public void eventoOcorreu(Campo campo, CampoEvento evento) {
    if(evento.equals(CampoEvento.EXPLODIR)){
      System.out.println("Perdeu o Jogo!");
      notificarObservadores(false);
      mostrarMinas();
    } else if (objetivoAlcancado()) {
      System.out.println("Ganhou o Jogo!");
      notificarObservadores(true);
      mostrarMinas();
    }
  }

  public void paraCadaCampo(Consumer<Campo> funcao) {
    campos.forEach(funcao); //percorrendo todos os campos da matriz, um for each dentro do outros
  }
}
