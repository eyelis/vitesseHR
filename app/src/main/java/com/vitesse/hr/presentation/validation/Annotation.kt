package com.vitesse.hr.presentation.validation

@Target( AnnotationTarget.PROPERTY)
annotation class Mandatory()

@Target( AnnotationTarget.PROPERTY)
annotation class Email()

@Target( AnnotationTarget.PROPERTY)
annotation class Phone()

@Target( AnnotationTarget.PROPERTY)
annotation class Digits()

@Target( AnnotationTarget.PROPERTY)
annotation class PastDate(val format: String)