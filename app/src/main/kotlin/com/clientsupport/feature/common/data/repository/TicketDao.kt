package com.clientsupport.feature.common.data.repository

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.clientsupport.core.data.repository.local.entity.TicketEntity
import io.reactivex.Flowable

@Dao
interface TicketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTickets(tickets: List<TicketEntity>)

    @Query("SELECT * FROM ticket ORDER BY createdAt DESC")
    fun queryAllTickets(): Flowable<List<TicketEntity>>
}