package com.clientsupport.core.data.repository.local.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "ticket")
data class TicketEntity(
        @PrimaryKey var id: Long,
        @ColumnInfo var subject: String,
        @ColumnInfo var description: String,
        @ColumnInfo var status: String,
        @ColumnInfo var createdAt: String,
        @ColumnInfo var updatedAt: String
)