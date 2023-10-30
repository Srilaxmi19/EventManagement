package com.practice.eventmanagement.service;

import com.practice.eventmanagement.exceptions.ResourceNotFoundException;
import com.practice.eventmanagement.model.Event;
import com.practice.eventmanagement.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Integer eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        if(event.isPresent()){
            return event.get();
        }else
//            throw new ResourceNotFoundException("Event","eventId",eventId);
        return eventRepository.findById(eventId).orElseThrow(()-> new ResourceNotFoundException("Event","eventId",eventId));
    }

    public String deleteEvent(Integer eventId) {
        eventRepository.findById(eventId).orElseThrow(()-> new ResourceNotFoundException("Event","eventId",eventId));
        eventRepository.deleteById(eventId);
        return "Event Deleted with:" + eventId;
    }

    public Event updateEvent(Integer eventId, Event event) {

    Event existingEvent = eventRepository.findById(eventId).orElseThrow(()-> new ResourceNotFoundException("Event","eventId",eventId));

            existingEvent.setEventDate(event.getEventDate());
            existingEvent.setEventName(event.getEventName());
            existingEvent.setEventDetails(event.getEventDetails());
            existingEvent.setTicketPrice(event.getTicketPrice());
            eventRepository.save(existingEvent);
        return existingEvent;
    }


    public Event updateEventByFields(Integer eventId, Map<String, Object> fields) {
        Optional<Event> existingEvent = eventRepository.findById(eventId);
        if (existingEvent.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Event.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingEvent.get(), value);
            });

            return eventRepository.save(existingEvent.get());
        }

        return null;
    }

}
