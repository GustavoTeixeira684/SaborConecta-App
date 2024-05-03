package com.example.saborconecta.activitys_menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.saborconecta.R
import com.example.saborconecta.activitys.Home
import com.example.saborconecta.activitys_menu.AdicionarProduto
import com.example.saborconecta.adapter.MenuPedidosAdapter
import com.example.saborconecta.databinding.ActivityMeusPedidosBinding
import com.example.saborconecta.model.MenuPedidos
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MeusPedidos : AppCompatActivity() {
    private lateinit var binding: ActivityMeusPedidosBinding
    private val auth = FirebaseAuth.getInstance()
    private val BD = FirebaseFirestore.getInstance()
    private val menuPedidos = mutableListOf<MenuPedidos>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeusPedidosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lerProdutos()

        MenuPedidos("1", "2", "3", "4")

        val adapter = MenuPedidosAdapter(menuPedidos, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.adicionarPedido -> {
                    Troca_de_Tela(AdicionarProduto::class.java)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.Meus_Produtos -> {
                    Troca_de_Tela(MeusPedidos::class.java) // Corrigido: MeusProdutos::class.java
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

        binding.imageViewBack.setOnClickListener {
            Troca_de_Tela(Home::class.java)
        }
    }

    private fun lerProdutos() {
        val produtosRef = BD.collection("Produtos_Cadastro")

        produtosRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val documento = document.id
                    val idAgrofamiliar = document.getString("ID Agrofamiliar") ?: ""
                    val nomeProduto = document.getString("Nome") ?: ""
                    val preco = document.getString("Preco") ?: ""
                    val tipoAlimento = document.getString("Tipo_de_Alimento") ?: ""

                    val novoPedido = MenuPedidos(documento, idAgrofamiliar, nomeProduto, preco)
                    menuPedidos.add(novoPedido) // Adicionar o novo pedido à lista
                }

                // Notificar o adapter sobre as mudanças nos dados
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
    }



    private fun Troca_de_Tela(next_tela: Class<*>) {
        val intent = Intent(this, next_tela)
        startActivity(intent)
        finish()
    }
}
