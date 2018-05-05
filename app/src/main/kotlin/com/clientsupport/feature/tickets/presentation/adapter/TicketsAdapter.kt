package com.clientsupport.feature.tickets.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.clientsupport.R
import com.clientsupport.feature.tickets.presentation.model.TicketScreenModel

class TicketsAdapter : RecyclerView.Adapter<TicketViewHolder>() {

    private var tickets: MutableList<TicketScreenModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ticket_layout, parent, false)
        return TicketViewHolder(view)
    }

    override fun getItemCount(): Int = tickets.size

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(tickets[position])
    }

    fun updateTickets(updates: List<TicketScreenModel>) {
        tickets.clear()
        tickets.addAll(updates)
        notifyDataSetChanged()
    }
}

data class TicketViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val idView: TextView = view.findViewById(R.id.ticketId)
    private val statusView: TextView = view.findViewById(R.id.ticketStatus)
    private val subjectView: TextView = view.findViewById(R.id.ticketSubject)
    private val descriptionView: TextView = view.findViewById(R.id.ticketDescription)
    private val lastUpdateView: TextView = view.findViewById(R.id.ticketLastUpdate)

    fun bind(ticket: TicketScreenModel) {
        idView.text = ticket.id
        statusView.text = ticket.status
        subjectView.text = ticket.subject
        descriptionView.text = ticket.description
        lastUpdateView.text = ticket.lastUpdate
    }
}