package br.com.sistemadereparo.listadetarefas.controller

import android.content.Context
import android.content.Intent
import android.widget.Toast
import br.com.sistemadereparo.listadetarefas.IMessage
import br.com.sistemadereparo.listadetarefas.adapter.TarefaAdapter
import br.com.sistemadereparo.listadetarefas.database.ITarefaDAO
import br.com.sistemadereparo.listadetarefas.database.TarefaDAO
import br.com.sistemadereparo.listadetarefas.model.Tarefa
import br.com.sistemadereparo.listadetarefas.view.MainActivity

class MainController (
    private val mainActivity: MainActivity
){

private  var listas= emptyList<Tarefa>()

    fun atualisarTarefa(lista: List<Tarefa>) {
        var listas= lista
        val tarefaDAO = TarefaDAO(mainActivity)
        listas = tarefaDAO.listar()
        mainActivity.atualisartarefa(listas)

    }

    fun confirmarExclusao(id:Int){
        val tarefaDAO = TarefaDAO(mainActivity)
        var message = ""

        if( tarefaDAO.deletar(id)) {
            mainActivity.atualisartarefa(listas)
             message= "Excluido com sucesso!"

        }else{
            message= "Erro ao excluir!"

        }

       mainActivity.message(message)
    }




}