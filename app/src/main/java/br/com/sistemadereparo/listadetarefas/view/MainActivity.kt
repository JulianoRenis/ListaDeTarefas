package br.com.sistemadereparo.listadetarefas.view

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.sistemadereparo.listadetarefas.database.TarefaDAO
import br.com.sistemadereparo.listadetarefas.view.adapter.TarefaAdapter
import br.com.sistemadereparo.listadetarefas.presenter.MainPresenter
import br.com.sistemadereparo.listadetarefas.databinding.ActivityMainBinding
import br.com.sistemadereparo.listadetarefas.model.Tarefa
import br.com.sistemadereparo.listadetarefas.view.interfaces.IAtualizar
import br.com.sistemadereparo.listadetarefas.view.interfaces.IMain

class MainActivity : AppCompatActivity(),IMain {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var mainPresenter: MainPresenter

    private var listaTarefas = mutableListOf<Tarefa>()
    private lateinit var tarefaAdapter: TarefaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mainPresenter = MainPresenter(this,binding.root.context)



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
        val itemTouchHelper = ItemTouchHelper(TarefaAdapter.SwipeToDeleteCallback(tarefaAdapter))
        itemTouchHelper.attachToRecyclerView(binding.rvTarefa)

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
                mainPresenter.confirmarExclusao(id)

            }
            setNegativeButton("Não"){ dialogInterface: DialogInterface, i: Int -> }
            create().show()
        }
    }

   override fun atualisarTarefa(listaTarefa:MutableList<Tarefa>){
        listaTarefas = listaTarefa
       tarefaAdapter?.adicionarLista(listaTarefas)

    }

    override fun onStart() {
        super.onStart()
        mainPresenter.atualisarTarefa(listaTarefas)

    }

    override fun message(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun finishActivi() {
        finish()
    }

}