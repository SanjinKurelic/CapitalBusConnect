package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.viewmodel.ticket.Ticket;

import java.util.Locale;

public interface TicketService {

  Ticket getTicket(String username, Locale language, Integer userTravelHistoryId);

  //Tickets getTickets(String username, Locale language, Integer ...userTravelHistoryIds);

}
