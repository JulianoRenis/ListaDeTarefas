package br.com.sistemadereparo.listadetarefas.view.interfaces

import br.com.sistemadereparo.listadetarefas.model.Tarefa

interface IAtualizar {

    fun atualizarListaRv(listaTarefa: MutableList<Tarefa>)
}