package edu.kriogenik.infoloader.model

/**
 * Класс, хранящий данные о работнике.
 *
 * @param id
 *           Порядковый номер работника.
 * @param names
 *           Информация об имени работника.
 * @param date
 *           Дата рождения работника.
 * @param position
 *           Должность работника.
 * @param department
 *           Отдел/институт, к которому приписан работник.
 * @param insuranceNum
 *           Номер страховки.
 */
case class Employee(id:           Long,
                    names:        Names,
                    date:         String,
                    position:     String,
                    department:   String,
                    insuranceNum: String)
