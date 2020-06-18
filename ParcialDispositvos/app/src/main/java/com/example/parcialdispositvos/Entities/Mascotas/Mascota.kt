package com.example.parcialdispositvos.Entities.Mascotas


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mascota")
class Mascota (identifier:String,edad: Int, nombre: String, raza: String, nombredueno: String){

    @PrimaryKey
    @ColumnInfo(name = "identifier")
    var identifier: String

    @ColumnInfo(name = "edad")
    var edad: Int

    @ColumnInfo(name = "nombre")
    var nombre: String

    @ColumnInfo(name = "nombredueno")
    var nombredueno: String

    @ColumnInfo(name = "raza")
    var raza: String

    init{
        this.identifier = identifier
        this.edad = edad
        this.nombre = nombre
        this.nombredueno = nombredueno
        this.raza = raza
    }


}