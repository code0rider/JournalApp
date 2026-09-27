package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try{
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveEntry(user);
        }catch (Exception e) {
            System.out.println("Error saving entry: " + e.getMessage());
            throw new RuntimeException("An error occurred while saving the journal entry.");
        }
    }
    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }
    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }
    public JournalEntry findById(ObjectId id) {
        return journalEntryRepository.findById(id.toString()).orElse(null);
    }
    public void deleteById(ObjectId id) {
        journalEntryRepository.deleteById(id.toString());
    }
    public JournalEntry updateEntry(ObjectId id, JournalEntry updatedEntry) {
        if (journalEntryRepository.existsById(id.toString())) {
            updatedEntry.setId(id);
            return journalEntryRepository.save(updatedEntry);
        }
        return null;
    }
}
