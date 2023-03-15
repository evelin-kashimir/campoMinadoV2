package br.com.model;

@FunctionalInterface
public interface CampoObservador {

  //Recebe o evento e o tipo de evento que será disparado
  void eventoOcorreu(Campo campo, CampoEvento evento);
}
