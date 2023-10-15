package br.com.sistemadereparo.listadetarefas.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.sistemadereparo.listadetarefas.databinding.ItemTarefaBinding
import br.com.sistemadereparo.listadetarefas.model.Tarefa
import br.com.sistemadereparo.listadetarefas.view.interfaces.IAtualizar
import br.com.sistemadereparo.listadetarefas.view.interfaces.IMain


class TarefaAdapter(
    val onClickExcluir:(Int) -> Unit,
    val onClickEditar:(Tarefa) -> Unit
) : RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>() {

    private var listaTarefas: MutableList<Tarefa> = arrayListOf()
    private var imageButtonVisibilityList: MutableList<Boolean> = mutableListOf()
    private var mostrar :Boolean? = null

    fun adicionarLista(lista: MutableList<Tarefa>) {
        this.listaTarefas = lista
        this.imageButtonVisibilityList = MutableList(lista.size) { false }
        notifyDataSetChanged()

    }



    inner class TarefaViewHolder(itemBinding: ItemTarefaBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private val binding: ItemTarefaBinding

        init {
            binding = itemBinding
        }

        fun bind(tarefa: Tarefa) {
            binding.textDescricao.text = tarefa.descricao
            binding.textData.text = tarefa.dataCadastro

            binding.btnExcluir.setOnClickListener{
                onClickExcluir(tarefa.idTarefa)
            }

            binding.btnEditar.setOnClickListener{
                onClickEditar(tarefa)
            }

            val position = adapterPosition

             if (imageButtonVisibilityList[position]) {
                 binding.btnExcluir.visibility =View.VISIBLE
                 binding.btnEditar.visibility = View.GONE
            }else {
                 binding.btnExcluir.visibility = View.GONE
                 binding.btnEditar.visibility = View.VISIBLE

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val itemTarefaBinding = ItemTarefaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TarefaViewHolder(itemTarefaBinding)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa = listaTarefas[position]

        holder.bind(tarefa)
    }

    override fun getItemCount(): Int {
        return listaTarefas.size
    }

    class SwipeToDeleteCallback(private val adapter: TarefaAdapter) : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
            val dragFlags = 0
            val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            return makeMovementFlags(dragFlags, swipeFlags)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: ViewHolder,
            target: ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            if (direction == ItemTouchHelper.RIGHT) {
                // Ação de deslize para a esquerda
                // Implemente a lógica para a ação desejada (por exemplo, arquivar)

                // Atualizar a visibilidade do ImageButton
                adapter.imageButtonVisibilityList[position] = true
                adapter.notifyItemChanged(position)
            } else if (direction == ItemTouchHelper.LEFT) {
                // Ação de deslize para a direita
                // Implemente a lógica para a ação desejada (por exemplo, excluir)
                adapter.imageButtonVisibilityList[position] = false
                adapter.notifyItemChanged(position)
            }
        }
    }
}