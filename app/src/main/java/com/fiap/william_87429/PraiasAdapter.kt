package com.fiap.william_87429

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class PraiasAdapter(
    private val context: Context
) : RecyclerView.Adapter<PraiasAdapter.PraiaViewHolder>() {

    private val praias = mutableListOf<PraiaModel>()

    fun addPraia(newPraia: PraiaModel) {
        if (!isPraiaNameExists(newPraia.nome)) {
            praias.add(newPraia)
            notifyDataSetChanged()
            showNotification("Praia adicionada: ${newPraia.nome}")
        } else {
            showNotification("A praia ${newPraia.nome} já existe na lista.")
        }
    }

    fun removePraia(praia: PraiaModel) {
        praias.remove(praia)
        notifyDataSetChanged()
        showNotification("Praia removida: ${praia.nome}")
    }

    fun updatePraia(updatedPraia: PraiaModel) {
        val index = praias.indexOfFirst { it.id == updatedPraia.id }
        if (index != -1) {
            praias[index] = updatedPraia
            notifyItemChanged(index) // Corrigido para chamar o método notifyItemChanged do RecyclerView.Adapter
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PraiaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_praia, parent, false)
        return PraiaViewHolder(view)
    }

    override fun getItemCount(): Int = praias.size

    override fun onBindViewHolder(holder: PraiaViewHolder, position: Int) {
        val praia = praias[position]
        holder.bind(praia)
    }

    inner class PraiaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewNome: TextView = view.findViewById(R.id.textViewNome)
        val textViewCidade: TextView = view.findViewById(R.id.textViewCidade)
        val textViewEstado: TextView = view.findViewById(R.id.textViewEstado)
        val buttonDelete: ImageButton = view.findViewById(R.id.imageButtonDelete)
        val buttonEdit: ImageButton = view.findViewById(R.id.imageButtonEdit)

        fun bind(praia: PraiaModel) {
            textViewNome.text = praia.nome
            textViewCidade.text = praia.cidade
            textViewEstado.text = praia.estado

            buttonDelete.setOnClickListener {
                showDeleteConfirmationDialog(praia)
            }

            buttonEdit.setOnClickListener {
                showEditDialog(praia)
            }
        }

        private fun showDeleteConfirmationDialog(praia: PraiaModel) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Confirmar exclusão")
            builder.setMessage("Tem certeza de que deseja excluir esta praia?")
            builder.setPositiveButton("Sim") { _, _ ->
                removePraia(praia)
            }
            builder.setNegativeButton("Não", null)
            builder.show()
        }

        private fun showEditDialog(praia: PraiaModel) {
            val dialogView = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(16, 16, 16, 16)
            }

            val editTextNome = EditText(context).apply {
                hint = "Nome da Praia"
                setText(praia.nome)
            }
            val editTextCidade = EditText(context).apply {
                hint = "Cidade"
                setText(praia.cidade)
            }
            val editTextEstado = EditText(context).apply {
                hint = "Estado"
                setText(praia.estado)
            }

            dialogView.addView(editTextNome)
            dialogView.addView(editTextCidade)
            dialogView.addView(editTextEstado)

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Editar Praia")
            builder.setView(dialogView)
            builder.setPositiveButton("Salvar") { _, _ ->
                val newNome = editTextNome.text.toString()
                val newCidade = editTextCidade.text.toString()
                val newEstado = editTextEstado.text.toString()

                if (newNome.isNotBlank() && newCidade.isNotBlank() && newEstado.isNotBlank()) {
                    val updatedPraia = praia.copy(nome = newNome, cidade = newCidade, estado = newEstado)
                    updatePraia(updatedPraia)
                }
            }
            builder.setNegativeButton("Cancelar", null)
            builder.show()
        }
    }

    private fun isPraiaNameExists(nome: String): Boolean {
        return praias.any { it.nome == nome }
    }

    private fun showNotification(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
