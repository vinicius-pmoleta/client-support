package com.clientsupport.feature.tickets.presentation.model

import android.content.Context
import com.clientsupport.R
import com.clientsupport.feature.common.data.model.Ticket
import org.joda.time.format.DateTimeFormat

class TicketScreenConverter(context: Context) {

    private val idFormat = context.getString(R.string.tickets_id)
    private val lastUpdateFormat = context.getString(R.string.tickets_last_update)

    private val remoteDateFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ")
    private val screenDateFormat = DateTimeFormat.forPattern("dd/MM/yy HH:mm")

    fun toScreenModel(model: Ticket): TicketScreenModel {
        return TicketScreenModel(
                id = String.format(idFormat, model.id),
                subject = model.subject,
                description = model.description,
                lastUpdate = String.format(lastUpdateFormat, formatLastUpdate(model.updatedAt)),
                status = model.status.name
        )
    }

    internal fun formatLastUpdate(updatedAt: String): String {
        val parsedDate = remoteDateFormat.parseLocalDateTime(updatedAt)
        return screenDateFormat.print(parsedDate)
    }
}