package br.com.exemplo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PessoaController {
	
	@Autowired
	PessoaRepository repository;

	public PessoaController(PessoaRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping(value = "/pessoas")
	public List<Pessoa> getPessoas() {
		return repository.findAll();
	}
	
	@GetMapping(value = "/pessoa/{id}")
	public Optional<Pessoa> getPessoa(@PathVariable Long id) {
		return repository.findById(id);
	}

	@PostMapping("/pessoas")
	public Pessoa addPessoa(@RequestBody Pessoa pessoa) {
		return repository.save(pessoa);
	}
	
	@PutMapping("/pessoa/{id}")
	public Pessoa replacaPessoa(@RequestBody Pessoa pessoa, @PathVariable Long id) {
		return repository.findById(id).map(p -> {
			p.setNome(pessoa.getNome());
			p.setCpf(pessoa.getCpf());
			p.setCafeManha(pessoa.getCafeManha());
			return repository.save(p);
		}).orElseGet(() -> {
			pessoa.setId(id);
			return repository.save(pessoa);
		});
	}
	
	@DeleteMapping("/pessoa/{id}")
	public void deletePessoa(@PathVariable Long id) {
		repository.deleteById(id);
	}
	
}
