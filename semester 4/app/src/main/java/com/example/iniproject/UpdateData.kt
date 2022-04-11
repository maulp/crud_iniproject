package com.example.iniproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.TextUtils.isEmpty
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_update_data.*

class UpdateData : AppCompatActivity() {
    private var database: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    private var cekNama: String? = null
    private var cekAlamat: String? = null
    private var cekNoHp: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)
        supportActionBar!!.title = "Update Data"

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        data
        update.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                cekNama = new_nama.getText().toString()
                cekAlamat = new_alamat.getText().toString()
                cekNoHp = new_nohp.getText().toString()

                if(isEmpty(cekNama!!) || isEmpty(cekAlamat!!) || isEmpty(cekNoHp!!)){
                    Toast.makeText(this@UpdateData,
                        "DATA TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show()
                }else{
                    val setinidata = ini_data()
                    setinidata.nama = new_nama.getText().toString()
                    setinidata.alamat = new_alamat.getText().toString()
                    setinidata.no_hp = new_nohp.getText().toString()
                    updateData(setinidata)
                }
            }
        })
    }
    private fun isEmpty(s:String):Boolean{
        return TextUtils.isEmpty(s)
    }
    private val data: Unit
    private get() {
        val getNama = intent.extras!!.getString("dataNama")
        val getAlamat = intent.extras!!.getString("dataAlamat")
        val getNoHp = intent.extras!!.getString("dataNoHp")

        new_nama!!.setText(getNama)
        new_alamat!!.setText(getAlamat)
        new_nohp!!.setText(getNoHp)
    }

    private fun updateData(Data: ini_data){
        val userID = auth!!.uid
        val getKey = intent.extras!!.getString("getPrimaryKey")
        database!!.child("Admin")
            .child(userID!!)
            .child("iniData")
            .child(getKey!!)
            .setValue(Data)
            .addOnSuccessListener {
                new_nama!!.setText("")
                new_alamat!!.setText("")
                new_nohp!!.setText("")
                Toast.makeText(this@UpdateData, "Data Berhasil Diupdate",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
    }
}