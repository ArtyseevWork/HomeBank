package com.mordansoft.homebank.data.storage

import androidx.room.*
import com.mordansoft.homebank.data.model.PurchaseD

@Dao
interface PurchaseDao {
    @Query("SELECT * FROM purchase")
    fun getAll(): List<PurchaseD>

    @Query("SELECT * FROM purchase WHERE title LIKE :title")
    fun findByTitle(title: String): PurchaseD

    @Insert
    fun insertAll(vararg todo: PurchaseD)

    @Delete
    fun delete(todo: PurchaseD)

    @Update
    fun updateTodo(vararg todos: PurchaseD)

}
