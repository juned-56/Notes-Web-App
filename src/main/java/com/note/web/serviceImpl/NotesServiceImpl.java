package com.note.web.serviceImpl;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.note.web.dao.NotesDao;
import com.note.web.model.Notes;
import com.note.web.notesDTO.NotesDTO;
import com.note.web.service.NotesService;
import com.note.web.util.NotesUtil;

@Service
public class NotesServiceImpl implements NotesService{
	
	@Autowired
	private NotesDao notesDao;
	@Autowired
	private NotesUtil notesUtil;
	
	@Override
	public Notes addNewNotes(Notes notes) {
		try {
			if(validNotesData(notes)) {
				return notesDao.save(notes);
			}else {
				throw new IllegalArgumentException(notesUtil.INVALID_NOTE_DATA);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(notesUtil.FAILED_TO_ADD_NEW_USER, e);
		}
	}

	@Override
	public Notes updatNotesById(int id, Notes note) {
		return notesDao.findById(id).map(exestingUser ->{
			if(StringUtils.hasText(note.getUsername())) {
				exestingUser.setUsername(note.getUsername());
			}
			if(StringUtils.hasText(note.getTitle())) {
				exestingUser.setTitle(note.getTitle());
			}
			if(StringUtils.hasText(note.getDescription())) {
				exestingUser.setDescription(note.getDescription());
			}
			return notesDao.save(exestingUser);
		}).orElseThrow(() -> new IllegalArgumentException(notesUtil.USER_NOT_FOUND_WITH_ID+id));
	}

	@Override
	public Optional<Notes> getNotesById(int id) {
		return notesDao.findById(id);
	}

	@Override
	public List<Notes> getAllNotes() {
		return notesDao.findAll();
	}

	@Override
	public void deleteNotesById(int id) {
		if(notesDao.existsById(id)) {
			notesDao.deleteById(id);
		}else {
			throw new IllegalArgumentException(notesUtil.NOTES_NOT_FOUND_WITH_THIS_ID+id);
		}
	}
	
	
	private boolean validNotesData(Notes notes) {
		if(notes == null) {
			return false;
		}
		if(!StringUtils.hasText(notes.getUsername())) {
			return false;
		}
		if(!StringUtils.hasText(notes.getTitle())) {
			return false;
		}
		if(!StringUtils.hasText(notes.getDescription())) {
			return false;
		}
		return true;
	}

	@Override
	public Optional<NotesDTO> getNoteDTOById(int id) {
		return notesDao.findById(id).map(note -> new NotesDTO(
				note.getId(),
				note.getUsername(),
				note.getTitle(),
				note.getDescription(),
				note.getCreatedAt()
				));
	}

	@Override
	public List<NotesDTO> getNoteDTOByTitle(String title) {
		return notesDao.findByTitle(title)
				.stream()
				.map(note -> new NotesDTO(
				note.getId(),
				note.getUsername(),
				note.getTitle(),
				note.getDescription(),
				note.getCreatedAt()
				))
		.collect(Collectors.toList());
	}

}
