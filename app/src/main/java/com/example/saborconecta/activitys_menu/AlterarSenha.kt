package com.example.saborconecta.activitys_menu

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.example.saborconecta.MainActivity
import com.example.saborconecta.R
import com.example.saborconecta.activitys.Home
import com.example.saborconecta.databinding.ActivityAlterarSenhaBinding
import com.example.saborconecta.databinding.ActivityConfigPerfilBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class AlterarSenha : AppCompatActivity() {

    private lateinit var binding: ActivityAlterarSenhaBinding
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlterarSenhaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.iconBackarrow.setOnClickListener {
            Troca_de_Tela(Home::class.java)
        }

        binding.buttonResetPassword.setOnClickListener {
            val email = binding.editEmail.text.toString()
            resetarSenha(email, it)
        }
    }

    private fun deslogar() {
        FirebaseAuth.getInstance().signOut()
    }

    private fun resetarSenha(email: String, view: View) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Mensagens("Link para redefinição enviado por e-mail", view, Color.parseColor("#118DF0"))
                    Handler(Looper.getMainLooper()).postDelayed({
                        deslogar()
                        val intent = Intent(view.context, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        view.context.startActivity(intent)
                    }, 1000)
                } else {
                    Mensagens("E-mail não cadastrado", view, Color.parseColor("#118DF0"))
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