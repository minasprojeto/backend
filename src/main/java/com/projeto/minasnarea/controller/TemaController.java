package com.projeto.minasnarea.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;


import com.projeto.minasnarea.model.Minas;
import com.projeto.minasnarea.repository.TemaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tema")
@CrossOrigin(origins = "*" , allowedHeaders = "*")
public class TemaController {
	@Autowired
	private TemaRepository temaRepository;
	
	
	@GetMapping
	public ResponseEntity<List<Minas>> getAll(){
		return ResponseEntity.ok(temaRepository.findAll());
	}
	@GetMapping("/{id}")
	public ResponseEntity<Minas> getById(@PathVariable Long id){
		
		return temaRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<Minas>> getByDescricao(@PathVariable String descricao){
		return ResponseEntity.ok(temaRepository.findAllByDescricaoContainingIgnoreCase(descricao));
		
	}
	
	 @PostMapping
	    public ResponseEntity<Minas> post(@Valid @RequestBody Minas tema){
	        return ResponseEntity.status(HttpStatus.CREATED)
	        .body(temaRepository.save(tema));
	    }
	    
	    @PutMapping
	    public ResponseEntity<Minas> put(@Valid @RequestBody Minas tema){
	        return temaRepository.findById(tema.getId())
	        .map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
	        .body(temaRepository.save(tema)))
	        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	    }
	    
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    @DeleteMapping("/{id}")
	    public void delete(@PathVariable Long id) {
	        Optional<Minas> tema = temaRepository.findById(id);
	        
	        if(tema.isEmpty())
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	        
	        temaRepository.deleteById(id);              
	    }
}


