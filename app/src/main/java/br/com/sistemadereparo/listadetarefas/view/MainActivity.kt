package br.com.sistemadereparo.listadetarefas.view

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.sistemadereparo.listadetarefas.IMessage
import br.com.sistemadereparo.listadetarefas.adapter.TarefaAdapter
import br.com.sistemadereparo.listadetarefas.controller.MainController
import br.com.sistemadereparo.listadetarefas.database.TarefaDAO
import br.com.sistemadereparo.listadetarefas.databinding.ActivityMainBinding
import br.com.sistemadereparo.listadetarefas.model.Tarefa

class MainActivity : AppCompatActivity(),IMessage{



    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var mainController: MainController

    private var listaTarefas = emptyList<Tarefa>()
    private var tarefaAdapter: TarefaAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mainController = MainController(this)

        binding.fabAdicionar.setOnClickListener {
            var cliqueFAB = binding.fabAdicionar.isClickable
            val intent = Intent(this, AdicionarTarefaActivity::class.java)
            intent.putExtra("clique",cliqueFAB)
            startActivity(intent)
        }

        tarefaAdapter = TarefaAdapter(
            {id -> confirmarExclusao(id)},
            {tarefa -> editar(tarefa)}
        )

        binding.rvTarefa.adapter=tarefaAdapter
        binding.rvTarefa.layoutManager = LinearLayoutManager(this)

    }

     fun editar(tarefa: Tarefa) {

        val intent = Intent(this, AdicionarTarefaActivity::class.java)
        intent.putExtra("tarefa",tarefa)
        startActivity(intent)

    }


     fun confirmarExclusao(id: Int) {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.apply {
            setTitle("Confirmar exclusão")
            setMessage("Deseja realmente excluir a tarefa?")
            setPositiveButton("Sim"){ dialogInterface: DialogInterface, i: Int ->
                mainController.confirmarExclusao(id)
            }
            setNegativeButton("Não"){ dialogInterface: DialogInterface, i: Int -> }
            create().show()
        }


    }

     fun atualisartarefa(listaTarefas:List<Tarefa>){
        tarefaAdapter?.adicionarLista(listaTarefas)
    }

    override fun onStart() {
        super.onStart()
       mainController.atualisarTarefa(listaTarefas)
    }

    override fun message(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}