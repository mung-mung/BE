package api.article.jobPostingArticle;

import api.article.jobPostingArticle.dto.CreateJobPostingDto;
import api.article.jobPostingArticle.dto.JobDetailDto;
import api.article.jobPostingArticle.dto.JobPostingArticleDto;
import api.article.jobPostingArticle.repository.JobPostingArticleRepository;
import api.common.util.auth.loggedInUser.LoggedInUser;
import api.owning.Owning;
import api.owning.repository.OwningRepository;
import api.user.userAccount.dto.UserAccountDto;
import api.user.enums.Role;
import api.user.owner.Owner;
import api.user.owner.repository.OwnerRepository;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JobPostingArticleService {

    private final JobPostingArticleRepository jobPostingArticleRepository;
    private final OwnerRepository ownerRepository;
    private final OwningRepository owningRepository;

    public JobPostingArticleService(JobPostingArticleRepository jobPostingArticleRepository, OwnerRepository ownerRepository, OwningRepository owningRepository) {
        this.jobPostingArticleRepository = jobPostingArticleRepository;
        this.ownerRepository = ownerRepository;
        this.owningRepository = owningRepository;
    }

    @Transactional(readOnly = true)
    public List<JobPostingArticleDto> findJobPostingArticlesByAllCriteria(Integer id, Integer writerId, Integer ownerId, Integer dogId, String walkingLocation, LocalDateTime walkingDateTime, Integer walkingMinutes, Integer hourlyRate) {
        return jobPostingArticleRepository.findJobPostingArticlesByAllCriteria(id, writerId, ownerId, dogId, walkingLocation, walkingDateTime, walkingMinutes, hourlyRate);
    }

    @Transactional
    public JobPostingArticleDto createArticle(CreateJobPostingDto createJobPostingDto) throws AccessDeniedException {
        Owner loggedInOwner = loggedInOwner();
        Optional<Owning> optionalOwning = owningRepository.findByOwnerIdAndDogId(loggedInOwner.getId(), createJobPostingDto.getDogId());
        if (optionalOwning.isEmpty()) {
            throw new AccessDeniedException("Only owner of the dog can create Job posting article.");
        }
        Owning owning = optionalOwning.get();

        JobPostingArticle newArticle = new JobPostingArticle(owning, createJobPostingDto.getJobDetailDto());
        JobPostingArticle savedArticle = jobPostingArticleRepository.save(newArticle);

        return new JobPostingArticleDto(savedArticle);
    }


    @Transactional
    public JobPostingArticleDto updateArticleContractDetail(Integer articleId, JobDetailDto jobDetailDto) throws AccessDeniedException {
        Owner loggedInOwner = loggedInOwner();
        Optional<JobPostingArticle> optionalJobPostingArticle = jobPostingArticleRepository.findById(articleId);
        if (optionalJobPostingArticle.isEmpty()) {
            throw new EntityNotFoundException("Job posting article not found with ID: " + articleId);
        }
        JobPostingArticle jobPostingArticle = optionalJobPostingArticle.get();
        if (!jobPostingArticle.getOwning().getOwner().equals(loggedInOwner)) {
            throw new AccessDeniedException("Only the author can update their own Job posting article.");
        }
        JobPostingArticle updatedArticle = jobPostingArticle.updateJobDetail(jobDetailDto);
        JobPostingArticle savedArticle = jobPostingArticleRepository.save(updatedArticle);
        return new JobPostingArticleDto(savedArticle);
    }

    @Transactional
    public void deleteArticle(Integer articleId) throws AccessDeniedException {
        Owner loggedInOwner = loggedInOwner();
        Optional<JobPostingArticle> optionalArticle = jobPostingArticleRepository.findById(articleId);
        if (optionalArticle.isEmpty()) {
            throw new EntityNotFoundException("Job posting article not found with ID: " + articleId);
        }
        JobPostingArticle article = optionalArticle.get();
        if (!article.getOwning().getOwner().equals(loggedInOwner)) {
            throw new AccessDeniedException("Only the author can delete their own Job posting article.");
        }
        jobPostingArticleRepository.deleteById(articleId);
    }

    private Owner loggedInOwner() throws AccessDeniedException {
        UserAccountDto loggedInUserAccountDto = LoggedInUser.getLoggedInUserAccountDto();
        if (loggedInUserAccountDto == null || !Role.OWNER.equals(loggedInUserAccountDto.getRole())) {
            throw new AccessDeniedException("Only owners can update Job posting article.");
        }

        Optional<Owner> optionalOwner = ownerRepository.findByEmail(loggedInUserAccountDto.getEmail());
        if (optionalOwner.isEmpty()) {
            throw new AccessDeniedException("Invalid owner account");
        }

        return optionalOwner.get();
    }

}
