package br.com.sistemadereparo.listadetarefas.presenter


import android.content.Context
import br.com.sistemadereparo.listadetarefas.database.TarefaDAO
import br.com.sistemadereparo.listadetarefas.model.Tarefa
import br.com.sistemadereparo.listadetarefas.view.adapter.TarefaAdapter
import br.com.sistemadereparo.listadetarefas.view.interfaces.IMain


class MainPresenter (
    private val view : IMain,
    private val context: Context
){

private  var listas= mutableListOf<Tarefa>()

    fun atualisarTarefa(lista: MutableList<Tarefa>) {
        this.listas = lista
        val tarefaDAO = TarefaDAO(context.applicationContext)

        listas = tarefaDAO.listar()
        view.atualisarTarefa(listas)

    }

    fun confirmarExclusao(id:Int){
        val tarefaDAO = TarefaDAO(context.applicationContext)
        var message = ""

        if( tarefaDAO.deletar(id)) {
            listas = tarefaDAO.listar()
            view.atualisarTarefa(listas)
            message= "Excluido com sucesso!"

        }else{
            message= "Erro ao excluir!"
        }
       view.message(message)
    }
}