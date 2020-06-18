package com.example.parcialdispositvos.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.parcialdispositvos.DataBase.Mascotas.MascotaDao
import com.example.parcialdispositvos.DataBase.Login.UserLoginDao
import com.example.parcialdispositvos.Entities.Login.User
import com.example.parcialdispositvos.Entities.Mascotas.Mascota

@Database(
    entities = [User::class, Mascota::class],
    version = 1,
    exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun userLoginDao(): UserLoginDao
    abstract fun MascotaDao(): MascotaDao

    companion object {
        var INSTANCE: DataBase? = null

        fun getDataBase(context: Context): DataBase? {
            if (INSTANCE == null) {
                synchronized(DataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DataBase::class.java,
                        "DataBase"
                    ).allowMainThreadQueries().build() // No es lo mas recomendable que se ejecute en el mainthread
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}