package com.example.saborconecta.activitys_menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.saborconecta.R
import com.example.saborconecta.activitys.Home
import com.example.saborconecta.adapter.MenuPedidosAdapter
import com.example.saborconecta.adapter.MenuPedidosAdapterAtivos
import com.example.saborconecta.databinding.ActivityMeusPedidosBinding
import com.example.saborconecta.databinding.ActivityPedidosAtivosBinding
import com.example.saborconecta.model.MenuPedidos
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PedidosAtivos : AppCompatActivity() {
    private lateinit var binding: ActivityPedidosAtivosBinding
    private val auth = FirebaseAuth.getInstance()
    private val BD = FirebaseFirestore.getInstance()
    private val menuPedidos = mutableListOf<MenuPedidos>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPedidosAtivosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lerProdutos()

        val adapter = MenuPedidosAdapterAtivos(menuPedidos, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.MeuCarrinho -> {
                    Troca_de_Tela(AdicionarProduto::class.java)
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
        val TipodeAlimento = intent.getStringExtra("extra_data")

        produtosRef.whereEqualTo("Tipo_de_Alimento", TipodeAlimento).get()
            .addOnSuccessListener { productsSnapshot ->
                for (productDocument in productsSnapshot) {
                    val NomeAgroFamiliar = "Agrofamiliar: " + productDocument.getString("Nome do Agrofamiliar") ?: ""
                    val documento = productDocument.id
                    val nomeProduto = "Nome do Produto: " + productDocument.getString("Nome do Produto") ?: ""
                    val preco = "Preço: R$" + productDocument.getString("Preco") ?: ""

                    val novoPedido = MenuPedidos(documento, NomeAgroFamiliar, nomeProduto, preco)
                    menuPedidos.add(novoPedido)
                }
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Tratar falhas ao buscar os documentos
                Log.e("FirestoreError", "Erro ao buscar documentos: ", exception)
            }
    }


    private fun Troca_de_Tela(next_tela: Class<*>) {
        val intent = Intent(this, next_tela)
        startActivity(intent)
        finish()
    }
}