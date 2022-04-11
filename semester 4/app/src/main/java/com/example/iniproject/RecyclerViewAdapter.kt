package com.example.iniproject

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private val listini_data: ArrayList<ini_data>, context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){
        private val context: Context

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val Nama: TextView
            val Alamat: TextView
            val NoHp: TextView
            val ListItem: LinearLayout

            init{
                Nama = itemView.findViewById(R.id.nama)
                Alamat = itemView.findViewById(R.id.alamat)
                NoHp = itemView.findViewById(R.id.no_hp)
                ListItem = itemView.findViewById(R.id.list_item)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val V:View = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_design,
        parent, false)
        return ViewHolder(V)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Nama: String? = listini_data.get(position).nama
        val Alamat: String? = listini_data.get(position).alamat
        val NoHp: String? = listini_data.get(position).no_hp

        holder.Nama.text = "Nama : $Nama"
        holder.Alamat.text = "Alamat : $Alamat"
        holder.NoHp.text = "No Hp : $NoHp"
        holder.ListItem.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean{
                holder.ListItem.setOnLongClickListener { view ->
                    val action = arrayOf("Update", "Delete")
                    val alert: AlertDialog.Builder = AlertDialog.Builder(view.context)
                    alert.setItems(action, DialogInterface.OnClickListener{dialogInterface, i ->
                        when (i){ 0 ->{
                            val bundle = Bundle()
                            bundle.putString("dataNama", listini_data[position].nama)
                            bundle.putString("dataAlamat", listini_data[position].alamat)
                            bundle.putString("dataNoHp", listini_data[position].no_hp)
                            bundle.putString("getPrimaryKey", listini_data[position].key)
                            val intent = Intent(view.context, UpdateData::class.java)
                            intent.putExtras(bundle)
                            context.startActivity(intent)
                        }1 -> {
                            listener?.onDeleteData(listini_data.get(position), position)
                        }
                        }
                    })
                    alert.create()
                    alert.show()
                    true
                }
                return true
            }
        })
    }

    override fun getItemCount(): Int {
        return listini_data.size
    }
    var listener: dataListener? = null
    init{
        this.context = context
        this.listener = context as MyListData
    }

    interface dataListener{
        fun onDeleteData(data: ini_data?, position:Int)
    }
}