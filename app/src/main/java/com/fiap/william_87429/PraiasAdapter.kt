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
    private val context: Context,
    private val onRemove: (PraiaModel) -> Unit
) : RecyclerView.Adapter<PraiasAdapter.PraiaViewHolder>() {

    private val praias = mutableListOf<PraiaModel>()

    fun addPraia(praia: PraiaModel) {
        praias.add(praia)
        notifyDataSetChanged()
    }

    fun removePraia(praia: PraiaModel) {
        praias.remove(praia)
        notifyDataSetChanged()
    }

    fun updatePraia(updatedPraia: PraiaModel) {
        val index = praias.indexOfFirst { it.nome == updatedPraia.nome }
        if (index != -1) {
            praias[index] = updatedPraia
            notifyItemChanged(index)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PraiaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_praia, parent, false)
        return PraiaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PraiaViewHolder, position: Int) {
        val praia = praias[position]
        holder.bind(praia)
    }

    override fun getItemCount(): Int = praias.size

    inner class PraiaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewNome: TextView = itemView.findViewById(R.id.textViewNome)
        private val textViewCidade: TextView = itemView.findViewById(R.id.textViewCidade)
        private val textViewEstado: TextView = itemView.findViewById(R.id.textViewEstado)
        private val imageButtonDelete: ImageButton = itemView.findViewById(R.id.imageButtonDelete)

        fun bind(praia: PraiaModel) {
            textViewNome.text = praia.nome
            textViewCidade.text = praia.cidade
            textViewEstado.text = praia.estado

            imageButtonDelete.setOnClickListener {
                onRemove(praia)
            }
        }
    }
}
