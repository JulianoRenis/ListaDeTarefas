package br.com.sistemadereparo.listadetarefas.database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import br.com.sistemadereparo.listadetarefas.model.Tarefa


class TarefaDAO(context: Context) : ITarefaDAO {

    private val escrita = DatabaseHelper(context).writableDatabase
    private val leitura = DatabaseHelper(context).readableDatabase

    override fun salvar(tarefa: Tarefa): Boolean {

        val conteudos = ContentValues()
        conteudos.put("${DatabaseHelper.COLUNA_DESCRICAO}",tarefa.descricao)
        try {
            escrita?.insert(
                DatabaseHelper.TABELA_TAREFAS ,null,conteudos)
            Log.i("info_db", "Sucesso ao criar a tabela ")
        } catch (e: Exception){
            Log.i("info_db", "erro ao criar tabela ${e.message}")
            return false
        }
        return true
    }

    override fun atualizar(tarefa: Tarefa): Boolean {
        val args = arrayOf(tarefa.idTarefa.toString())

        val conteudo = ContentValues()
        conteudo.put("${DatabaseHelper.COLUNA_DESCRICAO}", tarefa.descricao)
        try {
            escrita.update(
                DatabaseHelper.TABELA_TAREFAS,
                conteudo,
                "${DatabaseHelper.ID_TAREFA} = ?",
                args
            )
            Log.i("info_db", "Sucesso ao atualizar a tarefa ")
        } catch (e: Exception){
            Log.i("info_db", "erro ao atualizar tarefa ")
            return false
        }
        return true
    }



        override fun deletar(idTarefa: Int): Boolean {

            val args = arrayOf(idTarefa.toString())
            try {
                escrita.delete(
                    DatabaseHelper.TABELA_TAREFAS,
                    "${DatabaseHelper.ID_TAREFA} = ?",
                    args
                )
                Log.i("info_db", "Sucesso ao remover tarefa")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("info_db", "Erro ao remover tarefa")
                return false
            }

            return true

        }


    override fun listar(): List<Tarefa> {

        val listaTarefas = mutableListOf<Tarefa>()
        val sql = "SELECT ${DatabaseHelper.ID_TAREFA}, ${DatabaseHelper.COLUNA_DESCRICAO}," +
                "strftime('%d/%m/%Y %H:%M', ${DatabaseHelper.DATA_CRIACAO}) ${DatabaseHelper.DATA_CRIACAO} "+
       " FROM ${DatabaseHelper.TABELA_TAREFAS}"

        val cursor = leitura.rawQuery(sql,null)

        val indiceId = cursor.getColumnIndex(DatabaseHelper.ID_TAREFA)
        val indiceDescriacao= cursor.getColumnIndex(DatabaseHelper.COLUNA_DESCRICAO)
        val indiceData= cursor.getColumnIndex(DatabaseHelper.DATA_CRIACAO)

        while (cursor.moveToNext()){
            val idTarefa = cursor.getInt(indiceId)
            val idDescricao = cursor.getString(indiceDescriacao)
            val idData = cursor.getString(indiceData)

            listaTarefas.add(
                Tarefa(idTarefa,idDescricao,idData)
            )
        }


        return listaTarefas
    }

}