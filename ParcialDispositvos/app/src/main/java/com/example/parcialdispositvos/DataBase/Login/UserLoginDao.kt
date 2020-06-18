package com.example.parcialdispositvos.DataBase.Login

import androidx.room.*
import com.example.parcialdispositvos.Entities.Login.User

@Dao
interface UserLoginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(User: User?)

    @Update
    fun updateUser(User: User?)

    @Delete
    fun delete(User: User?)

    @Query("SELECT * FROM user WHERE id = :idUser")
    fun loadUserById(idUser: Int): User?

    @Query("SELECT id FROM user WHERE name = :User")
    fun getUserId(User: String): Int

    @Query("SELECT pass FROM user WHERE name = :UserName")
    fun loginUser(UserName: String): String

    @Query("SELECT * FROM user WHERE name= :name")
    fun loadUserByName(name: String): User?

    @Query("SELECT * FROM user WHERE email= :email")
    fun loadUserByEmail(email: String): User?
}