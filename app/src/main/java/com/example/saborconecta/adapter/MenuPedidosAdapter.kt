package com.example.saborconecta.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.saborconecta.R
import com.example.saborconecta.model.MenuPedidos

class MenuPedidosAdapter(private val menuList: MutableList<MenuPedidos>, private val callingActivity: Activity) :
    RecyclerView.Adapter<MenuPedidosAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var Numero_Pedido: TextView = itemView.findViewById(R.id.txtNumeroPedido)
        var Agrofamiliar: TextView = itemView.findViewById(R.id.txtAgroFamiliar)
        var NomeProduto: TextView = itemView.findViewById(R.id.txtNomeProduto)
        var Preco: TextView = itemView.findViewById(R.id.txtPreco)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_meuspedidos_card, parent, false)
        return MenuViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val currentItem = menuList[position]

        holder.Numero_Pedido.text = currentItem.NumeroPedido
        holder.Agrofamiliar.text = currentItem.Agrofamiliar
        holder.NomeProduto.text = currentItem.Nome_Produto
        holder.Preco.text = currentItem.Preco
    }

    override fun getItemCount() = menuList.size
}