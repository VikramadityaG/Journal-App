package com.vikramaditya.journalApp.service;

import com.vikramaditya.journalApp.entity.JournalEntry;
import com.vikramaditya.journalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(String id) {
        return journalEntryRepository.findById(id);
    }

    public Optional<JournalEntry> deleteEntryById(String id) {
        Optional<JournalEntry> entry = journalEntryRepository.findById(id);
        entry.ifPresent(journalEntryRepository::delete);
        return entry;
    }

    public Optional<JournalEntry> updateEntryById(String id, JournalEntry updatedEntry) {
        return journalEntryRepository.findById(id).map(existingEntry -> {
            existingEntry.setTitle(updatedEntry.getTitle());
            existingEntry.setContent(updatedEntry.getContent());
            existingEntry.setDate(updatedEntry.getDate());
            return journalEntryRepository.save(existingEntry);
        });
    }


}
