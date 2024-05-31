package com.note.web.service;
import java.util.List;
import java.util.Optional;

import com.note.web.model.Notes;
import com.note.web.notesDTO.NotesDTO;

public interface NotesService {

	public Notes addNewNotes(Notes notes); //done
	public Notes updatNotesById(int id, Notes note); //done
	public Optional<Notes> getNotesById(int id);
	public List<Notes> getAllNotes();
	public void deleteNotesById(int id);
	Optional<NotesDTO> getNoteDTOById(int id);
	List<NotesDTO> getNoteDTOByTitle(String title);
}
