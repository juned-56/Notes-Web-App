package com.note.web.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.note.web.model.Notes;
import com.note.web.notesDTO.NotesDTO;
import com.note.web.service.NotesService;
import com.note.web.serviceImpl.NotesServiceImpl;
import com.note.web.util.NotesUtil;

@RestController
@RequestMapping("/app")
public class NotesController {

	@Autowired
	private NotesServiceImpl notesServiceImpl;	
	@Autowired
	private NotesUtil notesUtil;
	@Autowired
	private NotesService notesService;
	
	@PostMapping("/addNewNotes")
    public ResponseEntity<String> createNote(@RequestBody Notes notes) {
        try {
            Notes createdNote = notesServiceImpl.addNewNotes(notes);
            return new ResponseEntity<String>(notesUtil.NEW_NOTES_SAVED+createdNote, HttpStatus.OK);//ResponseEntity.ok(createdNote);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(notesUtil.INTERNAL_SERVER_ERROR);
        }
    }
	
	@PutMapping("/updateNotes/{id}")
	public ResponseEntity<String> updateNotes(@PathVariable int id, @RequestBody Notes note){
		try {
			Notes updateNote = notesServiceImpl.updatNotesById(id, note);
			return new ResponseEntity<String>(notesUtil.NOTES_DETAILS_UPDATED+updateNote, HttpStatus.OK);
		} catch(IllegalArgumentException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}catch (Exception e) {
			return ResponseEntity.status(500).body(notesUtil.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getNotesById/{id}")
	public ResponseEntity<?> getNotesById(@PathVariable int id){
		return notesServiceImpl.getNoteDTOById(id) 
				.map(noteDTO -> ResponseEntity.ok(String.format("id: %d\n"
				+notesUtil.USER_NAME
				+"%s\n"+notesUtil.TITLE
				+"%s\n"+notesUtil.DESCRIPTION
				+"%s\n"+notesUtil.CREATED_AT
				+"%s",	
				        noteDTO.getId(),
						noteDTO.getUsername(),
						noteDTO.getTitle(),
						noteDTO.getDescription(),
						noteDTO.getCreatedAt())))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/getNotesByTitle/{title}")
	public ResponseEntity<?> getNotesByTitle(@PathVariable String title){
		List<NotesDTO> notes = notesServiceImpl.getNoteDTOByTitle(title);
		if(notes.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			StringBuilder response = new StringBuilder();
			for(NotesDTO noteDTO : notes) {
				response.append(String.format("id: %d\n"
				+notesUtil.USER_NAME
				+"%s\n"+notesUtil.TITLE
				+"%s\n"+notesUtil.DESCRIPTION
				+"%s\n"+notesUtil.CREATED_AT
				+"%s", 
				noteDTO.getId(),
				noteDTO.getUsername(),
				noteDTO.getTitle(),
				noteDTO.getDescription(),
				noteDTO.getCreatedAt()));
			}
			return ResponseEntity.ok(response.toString());
		}
	}
	
	@GetMapping("/getNoteById/{id}")
	public Optional<Notes> getNoteById(@PathVariable int id) {
		return notesServiceImpl.getNotesById(id);
	}

	
	@GetMapping("/getAllNotes")
    public ResponseEntity<List<Notes>> getAllNotes() {
        List<Notes> listOfNotes = notesServiceImpl.getAllNotes();
        if (listOfNotes.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(listOfNotes);
        }
    }
	
	@DeleteMapping("/deleteNoteById/{id}")
	public ResponseEntity<?> deleteNoteById(@PathVariable int id){
		try {
			notesServiceImpl.deleteNotesById(id);
			return ResponseEntity.noContent().build();
		}catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
		}catch (Exception e) {
			return ResponseEntity.status(500).body(notesUtil.INTERNAL_SERVER_ERROR);
		}
	}
}