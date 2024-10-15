package com.vitesse.hr.presentation


import androidx.lifecycle.SavedStateHandle
import com.vitesse.hr.domain.model.Candidate
import com.vitesse.hr.domain.usecase.GetCandidate
import com.vitesse.hr.domain.usecase.GetCurrency
import com.vitesse.hr.domain.usecase.UseCases
import com.vitesse.hr.domain.util.Resource
import com.vitesse.hr.presentation.details.DetailState
import com.vitesse.hr.presentation.details.DetailViewModel
import com.vitesse.hr.presentation.util.DateUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private lateinit var useCases: UseCases
    private lateinit var useCaseGetCandidate: GetCandidate
    private lateinit var useCaseGetGetCurrency: GetCurrency
    private lateinit var savedStateHandle: SavedStateHandle


    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val testDispatcher = AppDispatchers(
        IO = UnconfinedTestDispatcher()
    )

    private lateinit var viewModel: DetailViewModel

    private lateinit var candidate: Candidate

    @Before
    fun setUp() {
        useCases = Mockito.mock()
        useCaseGetCandidate = Mockito.mock()
        useCaseGetGetCurrency = Mockito.mock()
        savedStateHandle = Mockito.mock()

        Mockito.`when`(useCases.getCandidate).thenReturn(useCaseGetCandidate)
        Mockito.`when`(useCases.getCurrency).thenReturn(useCaseGetGetCurrency)

        candidate = Candidate(
            id = 1,
            firstName = "firstName",
            lastName = "lastName",
            dateOfBirth = DateUtils.dateFrom("01/01/2000", "dd/MM/yyyy")!!,
            email = "firstName.lastName@email.com",
            isFavorite = false,
            phoneNumber = "0600000000",
            expectedSalary = 5000,
            note = "note",
            photo = null
        )
    }

    @Test
    fun `Given candidate id and currency, when the viewmodel is initialized, then the state is correctly filled`(): Unit =
        runTest {
            //given
            Mockito.`when`(savedStateHandle.get<String>("id")).thenReturn("1")
            Mockito.`when`(useCaseGetCandidate.invoke(1)).thenReturn(candidate)
            Mockito.`when`(useCaseGetGetCurrency.invoke(5000)).thenReturn(
                Resource.Success(2500.0)
            )

            //when
            viewModel = DetailViewModel(
                useCases = useCases,
                savedStateHandle = savedStateHandle,
                appDispatchers = testDispatcher
            )

            //then
            Assert.assertEquals(
                DetailState(
                    id = 1,
                    firstName = "firstName",
                    lastName = "lastName",
                    dateOfBirth = "01/01/2000",
                    age = "24",
                    email = "firstName.lastName@email.com",
                    isFavorite = false,
                    phoneNumber = "0600000000",
                    expectedSalary = "5000",
                    expectedSalaryGbp = "2500.0",
                    note = "note",
                    photo = null
                ),
                viewModel.state.value
            )
        }

    @Test
    fun `Given candidate id and currency fails, when the viewmodel is initialized, then the currency error is filled`(): Unit = runTest {

        //given
        Mockito.`when`(savedStateHandle.get<String>("id")).thenReturn("1")
        Mockito.`when`(useCaseGetCandidate.invoke(1)).thenReturn(candidate)
        Mockito.`when`(useCaseGetGetCurrency.invoke(5000)).thenReturn(
            Resource.Error("WIERD")
        )

        //when
        viewModel = DetailViewModel(
            useCases = useCases,
            savedStateHandle = savedStateHandle,
            appDispatchers = testDispatcher
        )

        //then
        Assert.assertTrue(viewModel.state.value.expectedSalaryGbp.contains("WIERD"))
    }

}