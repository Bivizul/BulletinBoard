package com.bivizul.bulletinboard.dialogs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bivizul.bulletinboard.R

class RcViewDialogSpinnerAdapter : RecyclerView.Adapter<RcViewDialogSpinnerAdapter.SpViewHolder>() {

    private val mainList = ArrayList<String>()

    // рисуем элемент по которому создается ViewHolder
    // сколько элементов будет,столько раз и нарисуется
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.sp_list_item,
            parent,
            false
        )
        return SpViewHolder(view)
    }

    // подключаем к элементу текст и т.д.
    override fun onBindViewHolder(holder: SpViewHolder, position: Int) {
        holder.setData(mainList[position])
    }

    // узнаем сколько элементов нужно нарисовать, чтобы подготовить места
    override fun getItemCount(): Int {
        return mainList.size
    }

    // будет создаваться столько штук,сколько есть элементов
    class SpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(text: String) {
            val tvSpItem = itemView.findViewById<TextView>(R.id.tvSpItem)
            tvSpItem.text = text
        }
    }

    // Обновляем адаптер
    fun updateAdapter(list: ArrayList<String>) {
        // очищаем mainList
        mainList.clear()
        // заполняем mainList пришедшим списком
        mainList.addAll(list)
        // сообщаем адаптеру, что данные изменились. Обновляем его
        notifyDataSetChanged()
    }
}