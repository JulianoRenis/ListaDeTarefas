package br.com.sistemadereparo.listadetarefas

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.sistemadereparo.listadetarefas.adapter.TarefaAdapter
import br.com.sistemadereparo.listadetarefas.database.TarefaDAO
import br.com.sistemadereparo.listadetarefas.databinding.ActivityMainBinding
import br.com.sistemadereparo.listadetarefas.model.Tarefa

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var listaTarefas = emptyList<Tarefa>()
    private var tarefaAdapter: TarefaAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fabAdicionar.setOnClickListener {
            val intent = Intent(this,AdicionarTarefaActivity::class.java)
            startActivity(intent)
        }

        tarefaAdapter = TarefaAdapter(
            {id -> confirmarExclusao(id)},
            {tarefa -> editar(tarefa)}
        )

        binding.rvTarefa.adapter=tarefaAdapter

        binding.rvTarefa.layoutManager = LinearLayoutManager(this)

    }

    private fun editar(tarefa: Tarefa) {

        val intent = Intent(this,AdicionarTarefaActivity::class.java)
        intent.putExtra("tarefa",tarefa)
        startActivity(intent)

    }

    private fun confirmarExclusao(id: Int) {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.apply {
            setTitle("Confirmar exclusão")
            setMessage("Deseja realmente excluir a tarefa?")
            setPositiveButton("Sim"){ dialogInterface: DialogInterface, i: Int ->
                val tarefaDAO = TarefaDAO(this@MainActivity)

              if( tarefaDAO.deletar(id)) {
                    atualisartarefa()
                  Toast.makeText(this@MainActivity, "Sucesso ao Remover tarefa", Toast.LENGTH_SHORT).show()
              }else{
                  Toast.makeText(this@MainActivity, "Erro ao Remover tarefa", Toast.LENGTH_SHORT).show()

              }
            }
            setNegativeButton("Não"){ dialogInterface: DialogInterface, i: Int -> }
            create().show()
        }


    }

    private fun atualisartarefa(){
        val tarefaDAO = TarefaDAO(this)
        listaTarefas = tarefaDAO.listar()
        tarefaAdapter?.adicionarLista(listaTarefas)
    }

    override fun onStart() {
        super.onStart()
       atualisartarefa()
    }


}