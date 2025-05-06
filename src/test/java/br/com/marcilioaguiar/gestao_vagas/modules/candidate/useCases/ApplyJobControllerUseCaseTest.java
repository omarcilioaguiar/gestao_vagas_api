package br.com.marcilioaguiar.gestao_vagas.modules.candidate.useCases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import br.com.marcilioaguiar.gestao_vagas.exceptions.JobNotFoundException;
import br.com.marcilioaguiar.gestao_vagas.exceptions.UserNotFoundException;
import br.com.marcilioaguiar.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.marcilioaguiar.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.marcilioaguiar.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.marcilioaguiar.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.marcilioaguiar.gestao_vagas.modules.company.entities.JobEntity;
import br.com.marcilioaguiar.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobControllerUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Não deve ser possível se inscrever na vaga se o candidato não existir")
    public void shouldNotBeAbleToApplyJobWithCandidateNotFound() {
        try {
            applyJobCandidateUseCase.execute(null, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Deve ser possível se inscrever na vaga se a vaga não existir")
    public void shouldNotBeAbleToApplyJobWithJobNotFound() {
        
        var idCandidate = UUID.randomUUID();

        var candidate = new CandidateEntity();
        candidate.setId(idCandidate);

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));
        
        try {
            applyJobCandidateUseCase.execute(idCandidate, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Deve ser possível criar uma nova inscrição na vaga")
    public void ShouldBeApleToCreateANewApplyJob() {
        var idCandidate = UUID.randomUUID();
        var idJob = UUID.randomUUID();

        var applyJob = ApplyJobEntity.builder()
                .idCandidate(idCandidate)
                .idJob(idJob)
                .build();

        var applyJobCreated = ApplyJobEntity.builder().id(UUID.randomUUID()).build();

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity()));

        when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

        var result = applyJobCandidateUseCase.execute(idCandidate, idJob);

        assertThat(result).hasFieldOrProperty("id");
        assertNotNull(result.getId());
    }
}