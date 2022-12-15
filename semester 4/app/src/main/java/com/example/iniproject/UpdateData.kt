package com.example.iniproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.TextUtils.isEmpty
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_update_data.*

class UpdateData : AppCompatActivity() {
    private var database: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    private var cekNama: String? = null
    private var cekAlamat: String? = null
    private var cekNoHp: String? = null
    private var jkel: String? = null
    private var cekjml:String? = null
//    private var makfav: String? = null
    private fun setDataSpinnerJK(){
        val adapter = ArrayAdapter.createFromResource(this, R.array.JenisKelamin, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        newspinnerJK.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)
        supportActionBar!!.title = "Update Data"
        setDataSpinnerJK()
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        data
        update.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                cekNama = new_nama.getText().toString()
                cekAlamat = new_alamat.getText().toString()
                cekNoHp = new_nohp.getText().toString()
                jkel = newspinnerJK.selectedItem.toString()
                cekjml = new_jml.getText().toString()
//                makfav = newspinnerMF.selectedItem.toString()

                if(isEmpty(cekNama!!) || isEmpty(cekAlamat!!) || isEmpty(cekNoHp!!)){
                    Toast.makeText(this@UpdateData,
                        "DATA TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show()
                }else{
                    val setinidata = ini_data()
                    setinidata.nama = new_nama.getText().toString()
                    setinidata.alamat = new_alamat.getText().toString()
                    setinidata.no_hp = new_nohp.getText().toString()
                    setinidata.jkel = newspinnerJK.selectedItem.toString()
                    setinidata.jml = new_jml.getText().toString()
//                    setinidata.makfav = newspinnerMF.selectedItem.toString()
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
        val jkel = intent.extras!!.getString("datajkel")
        val getjml = intent.extras!!.getString("datajml")
//        val makfav = intent.extras!!.getString("datamakfav")

        new_nama!!.setText(getNama)
        new_alamat!!.setText(getAlamat)
        new_nohp!!.setText(getNoHp)
        new_jml!!.setText(getjml)
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
                new_jml!!.setText("")
                Toast.makeText(this@UpdateData, "Data Berhasil Diupdate",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
    }
}