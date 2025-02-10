package com.vikramaditya.journalApp.controller;


import com.vikramaditya.journalApp.entity.JournalEntry;
import com.vikramaditya.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {


    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntries() {
        List<JournalEntry> journalEntries = journalEntryService.getAllEntries();

        return new ResponseEntity<>(journalEntries, HttpStatus.OK);

    }


    @PostMapping
    public ResponseEntity<String> createEntry(@RequestBody JournalEntry myEntry) {
        try {

            if (myEntry.getDate() == null) {
                myEntry.setDate(LocalDateTime.now());
            }

            journalEntryService.saveEntry(myEntry);

            // Return HTTP 201 Created with a success message
            return new ResponseEntity<>("Entry created successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle any errors during entry saving
            return new ResponseEntity<>("Failed to create entry.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("id/{myId}")
    public JournalEntry GetEntryById(@PathVariable ObjectId myId) {
        return journalEntryService.getEntryById(myId)
                .orElseThrow(() -> new RuntimeException("JournalEntry not found with ID: " + myId));
    }


    @DeleteMapping("id/{myId}")
    public JournalEntry DeleteEntryById(@PathVariable ObjectId myId){
        return journalEntryService.deleteEntryById(myId)
                .orElseThrow(() -> new RuntimeException("JournalEntry not found with ID: " + myId));

    }

    @PutMapping("id/{myId}")
    public JournalEntry UpdateEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry myEntry){
        return journalEntryService.updateEntryById(myId, myEntry)
                .orElseThrow(() -> new RuntimeException("JournalEntry not found with ID: " + myId));

    }


}
