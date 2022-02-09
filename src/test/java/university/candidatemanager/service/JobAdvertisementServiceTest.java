package university.candidatemanager.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import university.candidatemanager.model.JobAdvertisement;
import university.candidatemanager.repository.JobAdvertisementRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/**
 * Test class of {@link JobAdvertisementService}.
 */
class JobAdvertisementServiceTest {

    private static final Long ID = 1L;
    private static final String SCOPE = "Fejlesztő gyakornok";
    private static final String COMPANY_NAME = "Cégteszt Kft.";
    private static final String CONTENT = "abcdefghsdsds";

    @InjectMocks
    private JobAdvertisementService underTest;

    @Mock
    private JobAdvertisementRepository jobAdvertisementRepository;

    @BeforeEach
    public void setUp() { MockitoAnnotations.initMocks(this);}

    @AfterEach
    public void tearDown() { underTest = null;}

    @Test
    void getJobAdvertisementById() {
        //given
        JobAdvertisement expected = createJobAdvertisement();
        given(jobAdvertisementRepository.findById(ID)).willReturn(createOptionalJobAdvertisement());

        //when
        JobAdvertisement actual = underTest.getJobAdvertisementById(ID);

        //then
        assertEquals(actual, expected);
        then(jobAdvertisementRepository).should().findById(ID);
    }

    @Test
    void jobAdvertisements() {
        //given
        Collection<JobAdvertisement> expected = List.of(createJobAdvertisement());
        given(jobAdvertisementRepository.findAll()).willReturn(List.of(createJobAdvertisement()));

        //when
        Collection<JobAdvertisement> actual = underTest.jobAdvertisements();

        //then
        assertEquals(actual, expected);
        then(jobAdvertisementRepository).should().findAll();
    }

    @Test
    void saveJobAdvertisement() {
        //given
        JobAdvertisement job = createJobAdvertisement();

        //when
        underTest.saveJobAdvertisement(job);

        //then
        then(jobAdvertisementRepository).should().save(job);
    }

    @Test
    void updateJobAdvertisement() {
        //given
        JobAdvertisement job = createJobAdvertisement();
        given(jobAdvertisementRepository.findById(ID)).willReturn(Optional.of(job));

        //when
        underTest.updateJobAdvertisement(job);

        //then
        then(jobAdvertisementRepository).should().save(job);
    }

    @Test
    void delete() {
        //given
        //when
        underTest.delete(ID);

        //then
        then(jobAdvertisementRepository).should().deleteById(ID);
    }

    private Optional<JobAdvertisement> createOptionalJobAdvertisement() {return Optional.of(createJobAdvertisement());}

    private JobAdvertisement createJobAdvertisement() {
        JobAdvertisement job = new JobAdvertisement();
        job.setId(ID);
        job.setScope(SCOPE);
        job.setCompany_name(COMPANY_NAME);
        job.setContent(CONTENT);

        return job;
    }
}