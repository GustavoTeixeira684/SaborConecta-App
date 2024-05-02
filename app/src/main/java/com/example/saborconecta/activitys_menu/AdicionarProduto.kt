package com.example.saborconecta.activitys_menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.saborconecta.databinding.ActivityAdicionarProdutoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdicionarProduto : AppCompatActivity() {
    private lateinit var binding: ActivityAdicionarProdutoBinding
    private val auth = FirebaseAuth.getInstance()
    private val BD = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdicionarProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var Tipo_de_Alimento: String = ""

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
            Tipo_de_Alimento = "Legumes e Graos"
        }

        binding.radioButtonErvasTemperos.setOnClickListener {
            binding.radioButtonVerdurasVegetais.isChecked = false
            binding.radioButtonFrutasFrescas.isChecked = false
            binding.radioButtonLegumesGraos.isChecked = false
            binding.radioButtonErvasTemperos.isChecked = true
            Tipo_de_Alimento = "Ervas e Temperos"
        }

        binding.buttonAddProduct.setOnClickListener {
           val Nome_Produto =  binding.editTextProductName.text.toString()
           val Preco = binding.editTextPrice.text.toString()

           SalvarProduto(Nome_Produto, Preco, Tipo_de_Alimento)
        }

    }

    private fun SalvarProduto(Nome_Produto: String, Preco: String, Tipo_de_Alimento: String) {
        val usuarioatual = auth.currentUser?.uid.toString()

        BD.collection("InfoUsuarios").document(usuarioatual)
            .addSnapshotListener { documento, error ->
                if (documento != null) {
                    val dadousuario = hashMapOf(
                        "ID Usuário" to usuarioatual,
                        "AgroFamiliar" to documento.getString("Nome"),
                        "Nome do Produto" to Nome_Produto,
                        "Preco" to "R$"+Preco,
                    )
                    val colecao = BD.collection("Produtos_Cadastro").document(Tipo_de_Alimento).collection("Pedido")
                    colecao.get().addOnSuccessListener { documentos ->
                        val tamanhoColecao = documentos.size()
                        BD.collection("Produtos_Cadastro").document(Tipo_de_Alimento).collection("Pedido - $tamanhoColecao").document("Pedido - $tamanhoColecao").set(dadousuario)
                    }.addOnFailureListener { exception ->

                    }
                }
            }
    }
}