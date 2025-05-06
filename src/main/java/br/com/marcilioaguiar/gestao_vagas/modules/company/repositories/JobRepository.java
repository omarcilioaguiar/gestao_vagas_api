package br.com.marcilioaguiar.gestao_vagas.modules.company.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.marcilioaguiar.gestao_vagas.modules.company.entities.JobEntity;
import java.util.List;


public interface JobRepository extends JpaRepository<JobEntity, UUID> {

    // "contains - LIKE " 

    // SELECT * FROM job WHERE description LIKE '%filter%'

    List<JobEntity> findByDescriptionContainingIgnoreCase(String filter); 
}
