package university.candidatemanager.service;

import javassist.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import university.candidatemanager.model.AppUser;
import university.candidatemanager.repository.AppUserRepository;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/**
 * Test class of {@link AppUserService}.
 */
class AppUserServiceTest {

    private static final Long USER_ID = 1L;
    private static final String USERNAME = "tesztuser";
    private static final String EMAIL = "tesztuser@gmail.com";
    private static final String PASSWORD = "teszt";
    private static final String FIRST_NAME = "Teszt";
    private static final String SUR_NAME = "Elek";
    private static final LocalDate BIRTH_DATE = LocalDate.of(2000, 1, 8);
    private static final String BIRTH_PLACE = "Debrecen";
    private static final String PHONE = "+36307627192";
    private static final String NATIONALITY = "Magyar";
    private static final String ADDRESS = "Debrecen";
    private static final String INTRODUCTION = "adcdefgh";

    @InjectMocks
    private AppUserService underTest;

    @Mock
    private AppUserRepository appUserRepository;

    @BeforeEach
    public void setUp() { MockitoAnnotations.initMocks(this);}

    @AfterEach
    public void tearDown() { underTest = null;}

    @Test
    void getAppUserByUserName() throws NotFoundException {
        //given
        //AppUser expected = createUser();
        //given(appUserRepository.findByUserName(USERNAME)).willReturn(createOptionalUser());

        //when
        //AppUser actual = underTest.getAppUserByUserName(USERNAME);

        //then
        //assertEquals(actual, expected);
        //then(appUserRepository).should().findByUserName(USERNAME);
    }

    @Test
    void getAppUserById() throws NotFoundException {
        //given
        AppUser expected = createUser();
        given(appUserRepository.findById(USER_ID)).willReturn(createOptionalUser());

        //when
        AppUser actual = underTest.getAppUserById(USER_ID);

        //then
        assertEquals(actual, expected);
        then(appUserRepository).should().findById(USER_ID);
    }

    @Test
    void getAppUserForUpdateById() throws NotFoundException {
        //given
        AppUser expected = createUser();
        given(appUserRepository.findById(USER_ID)).willReturn(createOptionalUser());

        //when
        AppUser actual = underTest.getAppUserForUpdateById(USER_ID);
        actual.setEncryptedPassword(PASSWORD);

        //then
        assertEquals(actual, expected);
        then(appUserRepository).should().findById(USER_ID);
    }

    @Test
    void getAppUsers() {
        //given
        Collection<AppUser> expected = List.of(createUser());
        given(appUserRepository.findAll()).willReturn(List.of(createUser()));

        //when
        Collection<AppUser> actual = underTest.getAppUsers();

        //then
        assertEquals(actual, expected);
        then(appUserRepository).should().findAll();
    }

    @Test
    void getUsers() {
        //given
        Collection<AppUser> expected = List.of(createUser());
        given(appUserRepository.findAll()
                .stream()
                .filter(x -> !x.getRoleNames().contains("ROLE_ADMIN"))
                .collect(Collectors.toList())).willReturn(List.of(createUser()));

        //when
        Collection<AppUser> actual = underTest.getAppUsers();

        //then
        assertEquals(actual, expected);
        then(appUserRepository).should().findAll();
    }

    @Test
    void saveAppUser() {
        //given
        AppUser user = createUser();

        //when
        underTest.saveAppUser(user);

        //then
        then(appUserRepository).should().save(user);
    }

    @Test
    void updateAppUser() {
        //given
        AppUser user = createUser();
        given(appUserRepository.findById(USER_ID)).willReturn(Optional.of(user));

        //when
        underTest.updateAppUser(user);

        //then
        then(appUserRepository).should().save(user);
    }

    @Test
    void delete() {
        //given
        //when
        underTest.delete(USER_ID);

        //then
        then(appUserRepository).should().deleteById(USER_ID);
    }

    private Optional<AppUser> createOptionalUser() { return Optional.of(createUser());}

    private AppUser createUser(){
        AppUser user = new AppUser();
        user.setId(USER_ID);
        user.setUserName(USERNAME);
        user.setEncryptedPassword(PASSWORD);
        user.setEmail(EMAIL);
        user.setFirst_name(FIRST_NAME);
        user.setSur_name(SUR_NAME);
        user.setBirth_place(BIRTH_PLACE);
        user.setBirth_date(BIRTH_DATE);
        user.setPhone(PHONE);
        user.setNationality(NATIONALITY);
        user.setAddress(ADDRESS);
        user.setIntroduction(INTRODUCTION);
        user.setIsUser(Boolean.TRUE);
        user.setIsAdmin(Boolean.FALSE);

        return user;
    }
}