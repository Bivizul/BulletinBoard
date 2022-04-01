package com.bivizul.bulletinboard.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bivizul.bulletinboard.R
import com.bivizul.bulletinboard.utils.CityHelper

class DialogSpinnerHelper {

    // создаем диалог с выбором
    fun showSpinnerDialog(context: Context, list: ArrayList<String>) {
        val builder = AlertDialog.Builder(context)
        val rootView = LayoutInflater.from(context).inflate(R.layout.spinner_layout, null)
        val adapter = RcViewDialogSpinnerAdapter()
        val rcView = rootView.findViewById<RecyclerView>(R.id.rcSpView)
        val sv = rootView.findViewById<SearchView>(R.id.svSpinner)
        // укажем как будет выглядеть rcView
        rcView.layoutManager = LinearLayoutManager(context)
        // присваеваем rcView созданный адаптер
        rcView.adapter = adapter
        builder.setView(rootView)
        adapter.updateAdapter(list)
        setSearchViewListener(adapter, list, sv)
        builder.show()
    }

    // слушатель по поиску
    private fun setSearchViewListener(
        adapter: RcViewDialogSpinnerAdapter,
        list: ArrayList<String>,
        sv: SearchView?
    ) {
        sv?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                val tempList = CityHelper.filterListData(list, newText)
                adapter.updateAdapter(tempList)
                return true
            }
        })
    }


}