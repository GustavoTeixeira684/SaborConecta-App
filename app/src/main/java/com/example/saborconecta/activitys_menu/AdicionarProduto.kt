package com.example.saborconecta.activitys_menu

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.saborconecta.MainActivity
import com.example.saborconecta.R
import com.example.saborconecta.activitys.Home
import com.example.saborconecta.databinding.ActivityAdicionarProdutoBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.grpc.Context

class AdicionarProduto : AppCompatActivity() {
    private lateinit var binding: ActivityAdicionarProdutoBinding
    private val auth = FirebaseAuth.getInstance()
    private val BD = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdicionarProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var Tipo_de_Alimento: String = ""

        binding.imageViewBack.setOnClickListener {
            Troca_de_Tela(Home::class.java)
        }

        binding.radioButtonVerdurasVegetais.setOnClickListener {
            binding.radioButtonVerdurasVegetais.isChecked = true
            binding.radioButtonFrutasFrescas.isChecked = false
            binding.radioButtonLegumesGraos.isChecked = false
            binding.radioButtonErvasTemperos.isChecked = false
            Tipo_de_Alimento = "Verduras e Vegetais"
        }

        binding.radioButtonFrutasFrescas.setOnClickListener {
            binding.radioButtonVerdurasVegetais.isChecked = false
            binding.radioButtonFrutasFrescas.isChecked = true
            binding.radioButtonLegumesGraos.isChecked = false
            binding.radioButtonErvasTemperos.isChecked = false
            Tipo_de_Alimento = "Frutas Frescas"
        }

        binding.radioButtonLegumesGraos.setOnClickListener {
            binding.radioButtonVerdurasVegetais.isChecked = false
            binding.radioButtonFrutasFrescas.isChecked = false
            binding.radioButtonLegumesGraos.isChecked = true
            binding.radioButtonErvasTemperos.isChecked = false
            Tipo_de_Alimento = "Legumes e Grãos"
        }

        binding.radioButtonErvasTemperos.setOnClickListener {
            binding.radioButtonVerdurasVegetais.isChecked = false
            binding.radioButtonFrutasFrescas.isChecked = false
            binding.radioButtonLegumesGraos.isChecked = false
            binding.radioButtonErvasTemperos.isChecked = true
            Tipo_de_Alimento = "Ervas e Temperos"
        }

        binding.buttonAddProduct.setOnClickListener {
            val inputMethodManager =
                getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            val Nome_Produto = binding.editTextProductName.text.toString()
            val Preco = binding.editTextPrice.text.toString()
            SalvarProduto(Nome_Produto, Preco, Tipo_de_Alimento)
            Mensagens("O produto foi adicionado em seu perfil!", it, Color.parseColor("#118DF0"))
        }




        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.adicionarPedido -> {
                    Troca_de_Tela(AdicionarProduto::class.java)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.Meus_Produtos -> {
                    Troca_de_Tela(MeusPedidos::class.java)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false // Caso nenhum dos itens corresponda
        }
    }

    private fun SalvarProduto(Nome_Produto: String, Preco: String, Tipo_de_Alimento: String) {
        val usuarioatual = auth.currentUser?.uid.toString()

        val infoUsuariosRef = BD.collection("InfoUsuarios")
        infoUsuariosRef.document(usuarioatual).get()
            .addOnSuccessListener { usuarioSnapshot ->
                val nomeUsuario = usuarioSnapshot.getString("Nome") ?: ""

                val dadousuario = hashMapOf(
                    "ID Agrofamiliar" to usuarioatual,
                    "Nome do Agrofamiliar" to nomeUsuario,
                    "Nome do Produto" to Nome_Produto,
                    "Preco" to Preco,
                    "Tipo_de_Alimento" to Tipo_de_Alimento,
                )

                BD.collection("Produtos_Cadastro").get().addOnSuccessListener { documents ->
                    val quantidadeDocumentos = documents.size() + 1
                    BD.collection("Produtos_Cadastro").document("Produto - $quantidadeDocumentos")
                        .set(dadousuario)
                }
            }
    }

    private fun Mensagens(mensagem: String, view: View, cor: Int) {
        val snack = Snackbar.make(view, mensagem, Snackbar.LENGTH_LONG)
        snack.setBackgroundTint(cor)
        snack.show()
    }

    private fun Troca_de_Tela(next_tela: Class<*>) {
        val intent = Intent(this, next_tela)
        startActivity(intent)
        finish()
    }
}