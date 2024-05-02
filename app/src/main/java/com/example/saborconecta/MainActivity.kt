package com.example.saborconecta

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.saborconecta.activitys.Home
import com.example.saborconecta.activitys.TelaCadastro
import com.example.saborconecta.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.NovoCadastro.setOnClickListener {
            Troca_de_Tela(TelaCadastro::class.java)
        }

        binding.buttonLogin.setOnClickListener {
            login(it)
        }
    }


    override fun onStart() {
        super.onStart()
        val usuarioatual = FirebaseAuth.getInstance().currentUser
        if (usuarioatual != null){
            Troca_de_Tela(Home::class.java)
        }
    }

    private fun login(view: View) {
        val email = binding.editTextEmail.text.toString()
        val senha = binding.editTextPassword.text.toString()

        when {
            email.isEmpty() || senha.isEmpty() -> {
                Mensagens("Atenção: Preencha todos os campos", view, Color.parseColor("#118DF0"))
            }

            else -> {
                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Troca_de_Tela(Home::class.java)
                    }
                }.addOnFailureListener {
                    when (it) {
                        is FirebaseAuthInvalidCredentialsException -> Mensagens(
                            "E-mail Inválido: Digite novamente!", view, Color.parseColor("#118DF0"))
                        is FirebaseNetworkException -> Mensagens(
                            "Sem conexão com a internet!", view, Color.parseColor("#118DF0"))
                        else -> {
                            Mensagens("Credenciais inválidas", view, Color.parseColor("#118DF0"))
                        }
                    }
                }
            }
        }
    }

    private fun Mensagens(mensagem: String, view: View, cor: Int){
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