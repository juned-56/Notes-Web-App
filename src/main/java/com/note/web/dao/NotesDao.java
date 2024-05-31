package com.note.web.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.note.web.model.Notes;


@Repository
public interface NotesDao extends JpaRepository<Notes, Integer>{
	
	 List<Notes> findByTitle(String title);

}
