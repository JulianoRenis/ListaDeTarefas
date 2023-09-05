package br.com.sistemadereparo.listadetarefas.database

import br.com.sistemadereparo.listadetarefas.model.Tarefa


interface ITarefaDAO {
    fun salvar( tarefa: Tarefa ): Boolean
    fun atualizar( tarefa: Tarefa): Boolean
    fun deletar( id: Int ): Boolean
    fun listar(): List<Tarefa>
}