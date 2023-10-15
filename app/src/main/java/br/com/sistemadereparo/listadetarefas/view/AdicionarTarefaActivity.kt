package br.com.sistemadereparo.listadetarefas.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.sistemadereparo.listadetarefas.presenter.AdicionarTarefaPresenter
import br.com.sistemadereparo.listadetarefas.databinding.ActivityAdicionarTarefaBinding
import br.com.sistemadereparo.listadetarefas.model.Tarefa
import br.com.sistemadereparo.listadetarefas.view.interfaces.IAdicionarTarefa
import br.com.sistemadereparo.listadetarefas.view.interfaces.IAtualizar


class AdicionarTarefaActivity : AppCompatActivity(), IAdicionarTarefa {

    private val binding by lazy { ActivityAdicionarTarefaBinding.inflate(layoutInflater) }
    private lateinit var adicionarTarefaPresenter: AdicionarTarefaPresenter
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adicionarTarefaPresenter=AdicionarTarefaPresenter(this,binding.root.context)

        val bundle = intent.extras
       if(bundle!=null){
           adicionarTarefaPresenter.recebeBundle(bundle!!)
       }

        binding.btnSalvar.setOnClickListener {
         val tarefas = binding.editTarefa.text.toString()
            adicionarTarefaPresenter.pegaDados(tarefas)

        }

    }
 override   fun pegarBundle(tarefa:String){
        binding.editTarefa.setText(tarefa)
    }

    override  fun editar(descricao:String) {
        var descricao=descricao
            descricao = binding.editTarefa.text.toString()
         adicionarTarefaPresenter.editar(descricao)

    }

    override   fun salvar(tarefa: String) {
         var tarefa = tarefa
         var description = binding.editTarefa.text.toString()
       adicionarTarefaPresenter.salvar(description)

    }

    override fun finishActivi() {
        finish()
    }



    override fun atualisartarefa(lista: MutableList<Tarefa>) {
        TODO("Not yet implemented")
    }

    override fun message(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }



}