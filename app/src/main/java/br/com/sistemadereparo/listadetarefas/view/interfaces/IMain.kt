package br.com.sistemadereparo.listadetarefas.view.interfaces

import br.com.sistemadereparo.listadetarefas.model.Tarefa

interface IMain {


    fun atualisarTarefa(lista:MutableList<Tarefa>)
    fun message(message:String)
    fun finishActivi()
}