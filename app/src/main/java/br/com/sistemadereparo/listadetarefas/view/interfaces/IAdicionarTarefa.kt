package br.com.sistemadereparo.listadetarefas.view.interfaces

import br.com.sistemadereparo.listadetarefas.model.Tarefa

interface IAdicionarTarefa {

    fun atualisartarefa(lista:MutableList<Tarefa>)
    fun message(message:String)

    fun pegarBundle(tarefa:String)
    fun editar(descricao:String)
    fun salvar(tarefa:String)
    fun finishActivi()


}