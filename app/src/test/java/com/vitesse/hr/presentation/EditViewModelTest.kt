package com.vitesse.hr.presentation


import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.vitesse.hr.R
import com.vitesse.hr.domain.model.Candidate
import com.vitesse.hr.domain.usecase.AddCandidate
import com.vitesse.hr.domain.usecase.GetCandidate
import com.vitesse.hr.domain.usecase.UseCases
import com.vitesse.hr.presentation.edit.EditViewModel
import com.vitesse.hr.presentation.edit.event.EditEvent
import com.vitesse.hr.presentation.edit.state.EditState
import com.vitesse.hr.presentation.util.DateUtils
import com.vitesse.hr.presentation.util.UiString
import com.vitesse.hr.presentation.validation.ValidateState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class EditViewModelTest {

    private lateinit var useCases: UseCases
    private lateinit var useCaseGetCandidate: GetCandidate
    private lateinit var useCaseAddCandidate: AddCandidate
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var stateValidator: ValidateState<EditState>

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val testDispatcher = AppDispatchers(
        IO = UnconfinedTestDispatcher()
    )

    private lateinit var viewModel: EditViewModel

    private lateinit var candidate: Candidate

    @Before
    fun setUp() {
        useCases = mock()
        useCaseGetCandidate = mock()
        useCaseAddCandidate = mock()
        savedStateHandle = mock()
        stateValidator = mock()

        Mockito.`when`(useCases.getCandidate).thenReturn(useCaseGetCandidate)
        Mockito.`when`(useCases.addCandidate).thenReturn(useCaseAddCandidate)

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
    fun `Given candidate id, when the viewmodel is initialized, then the state is correctly filled`() =
        runTest {
            //given
            Mockito.`when`(savedStateHandle.get<String>("id")).thenReturn("1")
            Mockito.`when`(useCaseGetCandidate.invoke(1)).thenReturn(candidate)

            //when
            viewModel = EditViewModel(
                useCases = useCases,
                savedStateHandle = savedStateHandle,
                appDispatchers = testDispatcher,
                stateValidator = stateValidator
            )

            //then
            Assert.assertEquals(
                EditState(
                    id = candidate.id,
                    firstName = candidate.firstName,
                    lastName = candidate.lastName,
                    dateOfBirth = candidate.dateOfBirth,
                    email = candidate.email,
                    isFavorite = candidate.isFavorite,
                    phoneNumber = candidate.phoneNumber,
                    expectedSalary = candidate.expectedSalary.toString(),
                    note = candidate.note,
                    photo = candidate.photo,
                    errors = emptyMap(),
                    isLoading = false
                ),
                viewModel.state.value
            )
        }

    @Test
    fun `Given empty mandatory fields, when the the data is saved, then errors are filled`() =
        runTest {
            //given
            val mandatory: UiString = UiString.StringResource(resId = R.string.validation_mandatory)
            val invalid: UiString =
                UiString.StringResource(resId = R.string.validation_invalid_format)

            val expectedErrors = mapOf(
                "firstName" to mutableListOf(mandatory),
                "email" to mutableListOf(invalid)
            )

            val editState = EditState(
                firstName = "",
                lastName = candidate.lastName,
                dateOfBirth = candidate.dateOfBirth,
                email = "invalid.email",
                isFavorite = candidate.isFavorite,
                phoneNumber = candidate.phoneNumber,
                expectedSalary = candidate.expectedSalary.toString(),
                note = candidate.note,
                photo = candidate.photo,
                errors = emptyMap(),
                isLoading = false
            )

            Mockito.`when`(savedStateHandle.get<String>("id")).thenReturn("-1")
            Mockito.`when`(stateValidator.validate(editState)).thenReturn(expectedErrors)

            viewModel = EditViewModel(
                useCases = useCases,
                savedStateHandle = savedStateHandle,
                appDispatchers = testDispatcher,
                stateValidator = stateValidator
            )

            viewModel.state.test {
                awaitItem()
                viewModel.updateProperty(editState)
                awaitItem()
                viewModel.onEvent(event = EditEvent.OnSave)
                awaitItem()

                //then
                Assert.assertEquals(viewModel.state.value.errors, expectedErrors)
            }

        }

    @Test
    fun `Given valid fields, when the the data is saved, then data is saved`() =
        runTest {
            //given
            val editState = EditState(
                firstName = candidate.firstName,
                lastName = candidate.lastName,
                dateOfBirth = candidate.dateOfBirth,
                email = candidate.email,
                isFavorite = candidate.isFavorite,
                phoneNumber = candidate.phoneNumber,
                expectedSalary = candidate.expectedSalary.toString(),
                note = candidate.note,
                photo = candidate.photo,
                errors = emptyMap(),
                isLoading = false
            )

            Mockito.`when`(savedStateHandle.get<String>("id")).thenReturn("-1")
            Mockito.`when`(stateValidator.validate(editState)).thenReturn(emptyMap())

            viewModel = EditViewModel(
                useCases = useCases,
                savedStateHandle = savedStateHandle,
                appDispatchers = testDispatcher,
                stateValidator = stateValidator
            )

            viewModel.state.test {
                awaitItem()
                viewModel.updateProperty(editState)
                awaitItem()
                viewModel.onEvent(event = EditEvent.OnSave)
                awaitItem()

                //then
                Mockito.verify(useCaseAddCandidate).invoke(candidate.copy(id = null))
            }
        }
}