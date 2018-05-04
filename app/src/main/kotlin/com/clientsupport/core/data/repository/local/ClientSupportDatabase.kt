package com.clientsupport.core.data.repository.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.clientsupport.core.data.repository.local.entity.TicketEntity
import com.clientsupport.feature.common.data.repository.TicketDao

@Database(
        entities = [TicketEntity::class],
        version = 1
)
abstract class ClientSupportDatabase : RoomDatabase() {

    abstract fun ticketDao(): TicketDao
}