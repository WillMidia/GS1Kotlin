package com.fiap.william_87429

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : ComponentActivity() {
    private lateinit var adapter: PraiasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        adapter = PraiasAdapter(this) { praia ->
            adapter.removePraia(praia)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val nomeEditText: EditText = findViewById(R.id.editTextNome)
        val cidadeEditText: EditText = findViewById(R.id.editTextCidade)
        val estadoEditText: EditText = findViewById(R.id.editTextEstado)
        val addButton: Button = findViewById(R.id.buttonAdd)

        addButton.setOnClickListener {
            val nome = nomeEditText.text.toString().trim()
            val cidade = cidadeEditText.text.toString().trim()
            val estado = estadoEditText.text.toString().trim()

            if (nome.isEmpty() || cidade.isEmpty() || estado.isEmpty()) {
                Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (nome.length < 3 || cidade.length < 3 || estado.length < 2) {
                Toast.makeText(this, "Nome e cidade devem ter no mínimo 3 caracteres, estado no mínimo 2 caracteres", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val praia = PraiaModel(nome, cidade, estado)
            adapter.addPraia(praia)

            nomeEditText.text.clear()
            cidadeEditText.text.clear()
            estadoEditText.text.clear()
        }
    }
}
