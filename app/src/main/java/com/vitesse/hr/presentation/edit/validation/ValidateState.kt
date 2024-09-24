package com.vitesse.hr.presentation.edit.validation

import android.util.Patterns
import com.vitesse.hr.presentation.util.DateUtils.dateFrom
import com.vitesse.hr.presentation.util.DateUtils.now
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

class ValidateState<State : Any>(
    private val kClass: KClass<State>
) {
    fun validate(state: State): Map<String, MutableList<String>> {
        val errors = mutableMapOf<String, MutableList<String>>()

        kClass.memberProperties.forEach { field ->
            if (field.annotations.isEmpty())
                return@forEach

            val value = field.get(state)
            val fieldName = field.name

            field.annotations.forEach { annotation ->
                if (isEmpty(value)) {
                    if (annotation is Mandatory) {
                        errors.getOrPut(fieldName) { mutableListOf() }.add("Mandatory field")
                    }
                } else {
                    when (annotation) {
                        is Digits -> {
                            if (isNotDigits(value)) {
                                errors.getOrPut(fieldName) { mutableListOf() }
                                    .add("Invalid format numeric")
                            }
                        }

                        is Email -> {
                            if (isNotEmail(value)) {
                                errors.getOrPut(fieldName) { mutableListOf() }
                                    .add("Invalid format email")
                            }
                        }

                        is Phone -> {
                            if (isNotPhone(value)) {
                                errors.getOrPut(fieldName) { mutableListOf() }
                                    .add("Invalid format phone")
                            }
                        }

                        is PastDate -> {
                            val date = dateFrom(value.toString(), annotation.format)
                            if (date == null) {
                                errors.getOrPut(fieldName) { mutableListOf() }
                                    .add("Invalid format date")
                            } else {
                                if (isFutureDate(date)) {
                                    errors.getOrPut(fieldName) { mutableListOf() }
                                        .add("Invalid past date")
                                }
                            }
                        }
                    }
                }
            }
        }

        return errors
    }

    private fun isEmpty(value: Any?): Boolean {
        return value.toString().isEmpty()
    }

    private fun isNotEmail(value: Any?): Boolean {
        return !Patterns.EMAIL_ADDRESS.matcher(value.toString()).matches()
    }

    private fun isNotPhone(value: Any?): Boolean {
        return !Patterns.PHONE.matcher(value.toString()).matches()
    }

    private fun isNotDigits(value: Any?): Boolean {
        return !value.toString().all { char -> char.isDigit() }
    }

    private fun isFutureDate(value: LocalDate): Boolean {
        return value > LocalDate.now()
    }

}


