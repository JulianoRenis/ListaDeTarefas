package br.com.sistemadereparo.listadetarefas.presenter

import android.content.Context
import android.os.Build
import android.os.Bundle
import br.com.sistemadereparo.listadetarefas.database.TarefaDAO
import br.com.sistemadereparo.listadetarefas.model.Tarefa
import br.com.sistemadereparo.listadetarefas.view.interfaces.IAdicionarTarefa

class AdicionarTarefaPresenter(
   private val view : IAdicionarTarefa,
   private val context: Context

) {

   private var tarefa: Tarefa?= null
   private var message=""

   private  var cliqueSalvar: Boolean = false

   fun recebeBundle(bundle:Bundle) {
      var tarefaMod: String?= ""


      if(bundle != null){
         if (bundle.getBoolean("clique")){
            cliqueSalvar = bundle.getBoolean("clique")
         }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
               tarefa = bundle.getSerializable("tarefa",Tarefa::class.java)
               //cliqueSalvar = bundle.getBoolean("clique")
            }else{
               tarefa = bundle.getSerializable("tarefa")as Tarefa
               // cliqueSalvar = bundle.getBoolean("clique")
            }
            tarefaMod = tarefa?.descricao
            view.pegarBundle(tarefaMod!!)
         }
      }
   }

   fun pegaDados(tarefa:String):String{

      if(tarefa.isNotEmpty()){

         if (tarefa != null){

            if(cliqueSalvar==true) {
               view.salvar(tarefa)
               message= "Tarefa Salva"
            }
            else{
               view.editar(tarefa)
               message= "Tarefa Salva"
            }

         }else{
            message = "Preencha uma Tarefa"
         }
      }else{
        message = "Preencha uma Tarefa"
      }
      return message
   }

   fun salvar(descricao: String){
      val tarefa = Tarefa(-1, descricao, "default")

      val tarefaDAO = TarefaDAO(context)
      if (tarefaDAO.salvar(tarefa)) {
         message= "Salvo com sucesso!"
         view.finishActivi()
      }
   }

   fun editar(descricao:String):String{
      var tarefa= tarefa

      val tarefaAtualizar = Tarefa(tarefa!!.idTarefa,descricao,"default")


      val tarefaDAO = TarefaDAO(context)
      if (tarefaDAO.atualizar(tarefaAtualizar)){

         message = "Tarefa atualizada com sucesso"
         view.finishActivi()
      }
      return view.message(message).toString()
   }

}