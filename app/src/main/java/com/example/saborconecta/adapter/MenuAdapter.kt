package com.example.saborconecta.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.saborconecta.R
import com.example.saborconecta.activitys.TelaCadastro
import com.example.saborconecta.activitys_menu.PedidosAtivos
import com.example.saborconecta.model.menu_home

class MenuAdapter(private val context: Context, private val menuList: List<menu_home>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.textViewTitle)
        val description: TextView = itemView.findViewById(R.id.textViewDescription)
        val image: ImageView = itemView.findViewById(R.id.imageView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                when (position) {
                    0 -> {
                        Troca_de_Tela(PedidosAtivos::class.java, "Verduras e Vegetais")
                    }

                    1 -> {
                        Troca_de_Tela(PedidosAtivos::class.java, "Frutas Frescas")
                    }

                    2 -> {
                        Troca_de_Tela(PedidosAtivos::class.java, "Legumes e Grãos")
                    }

                    3 -> {
                        Troca_de_Tela(PedidosAtivos::class.java, "Ervas e Temperos")
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu_card, parent, false)
        return MenuViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val currentItem = menuList[position]
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
        holder.image.setImageResource(currentItem.imageResource)
    }

    override fun getItemCount() = menuList.size

    private fun Troca_de_Tela(next_tela: Class<*>, data: String) {
        val intent = Intent(context, next_tela)
        intent.putExtra("extra_data", data)
        context.startActivity(intent)
    }
}