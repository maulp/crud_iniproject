package com.example.iniproject

class ini_data {
    var nama: String? = null
    var alamat: String? = null
    var no_hp: String? = null
    var key: String? = null
    var jkel: String? = null
    var jml:String? = null
//    var makfav:String? = null

    constructor()

    constructor(nama: String?, alamat: String?, no_hp:String?, jkel: String?, makfav: String?){
        this.nama = nama
        this.alamat = alamat
        this.no_hp = no_hp
        this.jkel = jkel
        this.jml = jml
//        this.makfav = makfav
    }
}