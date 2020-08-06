package edu.kriogenik.infoloader.model

/**
 * Класс, хранящий в себе данные об имени.
 *
 * @param firstName
 *                  Имя.
 * @param lastName
 *                 Фамилия.
 * @param patronymic
 *                   Отчество.
 */
case class Names(firstName:  String,
                 lastName:   String,
                 patronymic: String)
