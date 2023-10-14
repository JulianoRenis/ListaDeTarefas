package br.com.sistemadereparo.listadetarefas.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.sistemadereparo.listadetarefas.IMessage
import br.com.sistemadereparo.listadetarefas.controller.AdicionarTarefaController
import br.com.sistemadereparo.listadetarefas.databinding.ActivityAdicionarTarefaBinding
import br.com.sistemadereparo.listadetarefas.model.Tarefa


class AdicionarTarefaActivity : AppCompatActivity(),IMessage{

    private val binding by lazy { ActivityAdicionarTarefaBinding.inflate(layoutInflater) }
    private lateinit var adicionarTarefaController: AdicionarTarefaController
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adicionarTarefaController=AdicionarTarefaController(this)

        val bundle = intent.extras
       if(bundle!=null){
           adicionarTarefaController.recebeBundle(bundle!!)
       }

        binding.btnSalvar.setOnClickListener {
         val tarefas = binding.editTarefa.text.toString()
            adicionarTarefaController.pegaDados(tarefas)

        }

    }
    fun pegarBundle(tarefa:String){
        binding.editTarefa.setText(tarefa)
    }

     fun editar(descricao:String) {
        var descricao=descricao
            descricao = binding.editTarefa.text.toString()
         adicionarTarefaController.editar(descricao)

    }

     fun salvar(tarefa: String) {
         var tarefa = tarefa
         var description = binding.editTarefa.text.toString()
       adicionarTarefaController.salvar(description)

    }

    override fun message(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }




}