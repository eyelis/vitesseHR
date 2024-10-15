package com.vitesse.hr.data.presentation.validation

import com.vitesse.hr.R
import com.vitesse.hr.presentation.edit.state.EditState
import com.vitesse.hr.presentation.util.DateUtils.now
import com.vitesse.hr.presentation.util.UiString
import com.vitesse.hr.presentation.validation.ValidateState
import kotlinx.datetime.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ValidateStateTest(
    private val state: EditState,
    private val expectedErrors: Map<String, MutableList<UiString>>
) {

    @Test
    fun validate() {
        //given
        val validator = ValidateState(EditState::class)

        //when
        val actualErrors = validator.validate(state)

        //then
        assertEquals(
            expectedErrors.mapValues { it -> it.value.map { it.toString() } },
            actualErrors.mapValues { it -> it.value.map { it.toString() } }
        )

    }

    companion object {
        private val MANDATORY_FIELD = UiString.StringResource(resId = R.string.validation_mandatory)
        private val INVALID_FORMAT =
            UiString.StringResource(resId = R.string.validation_invalid_format)

        @JvmStatic
        @Parameterized.Parameters(name = "{index}: entity={0}, expectedErrors={1}")
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf(
                    EditState(
                        firstName = "firstName",
                        lastName = "lastName",
                        dateOfBirth = LocalDate.now(),
                        email = "firstName.lastName@email.com",
                        phoneNumber = "0600000000",
                        expectedSalary = "5000"
                    ),
                    emptyMap<String, MutableList<UiString>>()
                ),
                arrayOf(
                    EditState(
                        firstName = "",
                        lastName = "",
                        dateOfBirth = null,
                        email = "firstName.lastName",
                        phoneNumber = "06XXXXXXX",
                        expectedSalary = "Cinq mille"
                    ),
                    mapOf(
                        "firstName" to listOf(MANDATORY_FIELD),
                        "lastName" to listOf(MANDATORY_FIELD),
                        "dateOfBirth" to listOf(MANDATORY_FIELD),
                        "email" to listOf(INVALID_FORMAT),
                        "phoneNumber" to listOf(INVALID_FORMAT),
                        "expectedSalary" to listOf(INVALID_FORMAT)
                    )

                )
            )
        }
    }
}


