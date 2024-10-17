package com.vitesse.hr.presentation


import app.cash.turbine.test
import com.vitesse.hr.domain.model.Candidate
import com.vitesse.hr.domain.usecase.ListAll
import com.vitesse.hr.domain.usecase.ListFavorites
import com.vitesse.hr.domain.usecase.UseCases
import com.vitesse.hr.presentation.list.ListViewModel
import com.vitesse.hr.presentation.list.event.ListEvent
import com.vitesse.hr.presentation.util.DateUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest {

    private lateinit var useCases: UseCases
    private lateinit var useCaseListAll: ListAll
    private lateinit var useCaseListFavorites: ListFavorites

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ListViewModel

    private lateinit var candidate: Candidate

    @Before
    fun setUp() {
        useCases = mock()
        useCaseListAll = mock()
        useCaseListFavorites = mock()

        Mockito.`when`(useCases.getCandidates).thenReturn(useCaseListAll)
        Mockito.`when`(useCases.getFavorites).thenReturn(useCaseListFavorites)

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
    fun `Given an existing candidate an favorite list, when the viewmodel is initialized, then the state is correctly filled`() =
        runTest {
            //given
            Mockito.`when`(useCaseListAll.invoke("")).thenReturn(listOf(listOf(candidate)).asFlow())
            Mockito.`when`(useCaseListFavorites.invoke(""))
                .thenReturn(listOf(listOf(candidate)).asFlow())

            //when
            viewModel = ListViewModel(
                useCases = useCases
            )

            //then
            viewModel.state.test {
                val item = awaitItem()
                Assert.assertTrue(item.searchText.isEmpty())
                Assert.assertTrue(!item.isSearching)
                Assert.assertTrue(item.candidates.contains(candidate))
                Assert.assertTrue(item.favorites.contains(candidate))
                Assert.assertTrue(item.activeTabIndex == 0)
            }

        }

    @Test
    fun `Given empty search text, when the search text is changed, then the search state is correctly filled`() =
        runTest {
            val searchText = "text"

            //given
            Mockito.`when`(useCaseListAll.invoke("")).thenReturn(listOf(listOf(candidate)).asFlow())
            Mockito.`when`(useCaseListFavorites.invoke(""))
                .thenReturn(listOf(listOf(candidate)).asFlow())

            Mockito.`when`(useCaseListAll.invoke(searchText))
                .thenReturn(emptyList<List<Candidate>>().asFlow())
            Mockito.`when`(useCaseListFavorites.invoke(searchText))
                .thenReturn(listOf(listOf(candidate)).asFlow())

            //when
            viewModel = ListViewModel(
                useCases = useCases
            )

            //then
            viewModel.state.test {
                val initItem = awaitItem()
                Assert.assertTrue(initItem.searchText.isEmpty())
                viewModel.onEvent(ListEvent.OnSearch(searchText))
                val item = awaitItem()
                Assert.assertTrue(item.searchText == searchText)
            }
        }

    @Test
    fun `Given candidate active tab, when the active tab change, then the active tab state is correctly filled`() =
        runTest {
            //given
            Mockito.`when`(useCaseListAll.invoke("")).thenReturn(listOf(listOf(candidate)).asFlow())
            Mockito.`when`(useCaseListFavorites.invoke(""))
                .thenReturn(listOf(listOf(candidate)).asFlow())


            //when
            viewModel = ListViewModel(
                useCases = useCases
            )

            //then
            viewModel.state.test {
                val initItem = awaitItem()
                Assert.assertTrue(initItem.activeTabIndex == 0)
                viewModel.onEvent(ListEvent.OnChangeTab(1))
                val item = awaitItem()
                Assert.assertTrue(item.activeTabIndex == 1)
            }
        }


}