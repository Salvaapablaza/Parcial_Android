package com.example.parcialdispositvos.DataBase.Mascotas

import androidx.room.*
import com.example.parcialdispositvos.Entities.Mascotas.Mascota


@Dao
public interface MascotaDao {

    @Query( "SELECT * FROM mascota ORDER BY nombre")
    fun loadAllMascotas(): MutableList<Mascota>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMascota( mascota: Mascota?)

    @Update
    fun updateMascota(mascota: Mascota?)

    @Delete
    fun deleteMascota(mascota: Mascota?)

    @Query( "SELECT * FROM mascota WHERE nombredueno= :nombredueno ORDER BY nombre")
    fun loadAllMascotasbydueno(nombredueno: String): MutableList<Mascota?>?

    @Query( "SELECT * FROM mascota WHERE identifier= :identifier" )
    fun loadMascotabyid(identifier: String):Mascota??


}

