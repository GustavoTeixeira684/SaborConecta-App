package com.example.saborconecta.activitys_menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.saborconecta.MainActivity
import com.example.saborconecta.R
import com.example.saborconecta.activitys.Home
import com.example.saborconecta.databinding.ActivityConfigPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class config_perfil : AppCompatActivity() {
    private lateinit var binding: ActivityConfigPerfilBinding
    private val auth = FirebaseAuth.getInstance()
    private val BD = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        LeituraDados(binding)

        binding.buttonAlterarSenha.setOnClickListener {
            Troca_de_Tela(AlterarSenha::class.java)
        }

        binding.imageViewBack.setOnClickListener {
            Troca_de_Tela(Home::class.java)
        }

        binding.buttonSairConta.setOnClickListener {
            deslogar()
            Troca_de_Tela(MainActivity::class.java)
        }

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    Troca_de_Tela(MainActivity::class.java)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_profile -> {
                    Troca_de_Tela(config_perfil::class.java)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false // Caso nenhum dos itens corresponda
        }

    }

    private fun deslogar() {
        FirebaseAuth.getInstance().signOut()
    }

    private fun LeituraDados(binding: ActivityConfigPerfilBinding) {
        val UsuarioAtual = auth.currentUser?.uid.toString()
        BD.collection("InfoUsuarios").document(UsuarioAtual)
            .addSnapshotListener { documento, error ->
                if (documento != null) {
                    binding.textViewNome.text = documento.getString("Nome");
                    binding.textViewEmail.text = documento.getString("email")
                    binding.ClassificacaoUsuario.text = documento.getString("Classificação Usuário")
                }
            }
    }

    private fun Troca_de_Tela(next_tela: Class<*>) {
        val intent = Intent(this, next_tela)
        startActivity(intent)
        finish()
    }
}