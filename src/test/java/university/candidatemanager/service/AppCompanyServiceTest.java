package university.candidatemanager.service;

import javassist.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import university.candidatemanager.model.AppCompany;
import university.candidatemanager.repository.AppCompanyRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class AppCompanyServiceTest {

    private static final Long ID = 1L;
    private static final String USERNAME = "tesztcég";
    private static final String PASSWORD = "teszt";
    private static final String REGISTRATION_NUMBER = "1234567890";
    private static final String COMPANY_NAME = "Cégteszt Kft.";
    private static final String INTRODUCTION = "abcdseeerer";
    private static final String LOCATION = "Debrecen";
    private static final String EMAIL = "tesztcég@gmail.com";
    private static final String PHONE = "+36708272381";

    @InjectMocks
    private AppCompanyService underTest;

    @Mock
    private AppCompanyRepository appCompanyRepository;

    @BeforeEach
    public void setUp() { MockitoAnnotations.initMocks(this);}

    @AfterEach
    public void tearDown() { underTest = null;}

    @Test
    void getAppCompanyByUserName() throws NotFoundException {
        //given
        //AppCompany expected = createCompany();
        //given(appCompanyRepository.findByUserName(USERNAME)).willReturn(createOptionalCompany());

        //when
        //AppCompany actual = underTest.getAppCompanyByUserName(USERNAME);

        //then
        //assertEquals(actual, expected);
        //then(appCompanyRepository).should().findByUserName(USERNAME);
    }

    @Test
    void getAppCompanyById() throws NotFoundException {
        //given
        AppCompany expected = createCompany();
        given(appCompanyRepository.findById(ID)).willReturn(createOptionalCompany());

        //when
        AppCompany actual = underTest.getAppCompanyById(ID);

        //then
        assertEquals(actual, expected);
        then(appCompanyRepository).should().findById(ID);

    }

    @Test
    void getAppCompanyForUpdateById() throws NotFoundException {
        //given
        AppCompany expected = createCompany();
        given(appCompanyRepository.findById(ID)).willReturn(createOptionalCompany());

        //when
        AppCompany actual = underTest.getAppCompanyForUpdateById(ID);
        actual.setEncryptedPassword(PASSWORD);

        //then
        assertEquals(actual, expected);
        then(appCompanyRepository).should().findById(ID);

    }

    @Test
    void getAppCompanies() {
        //given
        Collection<AppCompany> expected = List.of(createCompany());
        given(appCompanyRepository.findAll()).willReturn(List.of(createCompany()));

        //when
        Collection<AppCompany> actual = underTest.getAppCompanies();

        //then
        assertEquals(actual, expected);
        then(appCompanyRepository).should().findAll();

    }

    @Test
    void saveAppCompany() {
        //given
        AppCompany company = createCompany();

        //when
        underTest.saveAppCompany(company);

        //then
        then(appCompanyRepository).should().save(company);

    }

    @Test
    void updateAppCompany() {
        //given
        AppCompany company = createCompany();
        given(appCompanyRepository.findById(ID)).willReturn(Optional.of(company));

        //when
        underTest.updateAppCompany(company);

        //then
        then(appCompanyRepository).should().save(company);

    }

    @Test
    void delete() {
        //given

        //when
        underTest.delete(ID);

        //then
        then(appCompanyRepository).should().deleteById(ID);
    }

    private Optional<AppCompany> createOptionalCompany() { return Optional.of(createCompany());}

    private AppCompany createCompany() {
        AppCompany company = new AppCompany();
        company.setId(ID);
        company.setUserName(USERNAME);
        company.setEmail(EMAIL);
        company.setEncryptedPassword(PASSWORD);
        company.setLocation(LOCATION);
        company.setCompany_name(COMPANY_NAME);
        company.setRegistrationNumber(REGISTRATION_NUMBER);
        company.setPhone(PHONE);
        company.setIntroduction(INTRODUCTION);
        company.setIsCompany(Boolean.TRUE);

        return company;
    }
}