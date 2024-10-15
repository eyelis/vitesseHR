package com.vitesse.hr.presentation.validation

import android.util.Patterns
import com.vitesse.hr.R
import com.vitesse.hr.presentation.util.DateUtils.dateFrom
import com.vitesse.hr.presentation.util.DateUtils.now
import com.vitesse.hr.presentation.util.UiString
import kotlinx.datetime.LocalDate
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

class ValidateState<T : Any>(
    private val kClass: KClass<T>
) {
    fun validate(entity: T): Map<String, MutableList<UiString>> {
        val errors = mutableMapOf<String, MutableList<UiString>>()

        kClass.memberProperties.forEach { field ->
            if (field.annotations.isEmpty())
                return@forEach

            val value = field.get(entity)
            val fieldName = field.name

            field.annotations.forEach { annotation ->
                if (isEmpty(value)) {
                    if (annotation is Mandatory) {
                        errors.getOrPut(fieldName) { mutableListOf() }
                            .add(error(resId = R.string.validation_mandatory))
                    }
                } else {
                    when (annotation) {
                        is Digits -> {
                            if (isNotDigits(value)) {
                                errors.getOrPut(fieldName) { mutableListOf() }
                                    .add(error(resId = R.string.validation_invalid_format))

                            }
                        }

                        is Email -> {
                            if (isNotEmail(value)) {
                                errors.getOrPut(fieldName) { mutableListOf() }
                                    .add(error(resId = R.string.validation_invalid_format))
                            }
                        }

                        is Phone -> {
                            if (isNotPhone(value)) {
                                errors.getOrPut(fieldName) { mutableListOf() }
                                    .add(error(resId = R.string.validation_invalid_format))
                            }
                        }

                        is PastDate -> {
                            val date = dateFrom(value.toString(), annotation.format)
                            if (date == null) {
                                errors.getOrPut(fieldName) { mutableListOf() }
                                    .add(error(resId = R.string.validation_invalid_format))
                            } else {
                                if (isFutureDate(date)) {
                                    errors.getOrPut(fieldName) { mutableListOf() }
                                        .add(error(resId = R.string.validation_invalid_format))
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
        return value == null || value.toString().isEmpty()
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

    private fun error(resId: Int) =
        UiString.StringResource(
            resId = resId
        )
}


