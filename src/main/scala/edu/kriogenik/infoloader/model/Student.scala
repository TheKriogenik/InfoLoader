package edu.kriogenik.infoloader.model

/**
 * Класс, хранящий в себе данные о студенте.
 *
 * @param id
 *           Индивидуальный номер студента.
 * @param names
 *           Информация о его имени.
 * @param date
 *           Дата рождения.
 * @param educationLevel
 *           Уровень образования.
 * @param educationForm
 *           Вид обучения.
 * @param instituteName
 *           Название института, где происходит обучение.
 * @param bookNum
 *           Номер зачетной книжки.
 * @param insuranceNum
 *           Номер страховки.
 * @param email
 *           Электронный адрес.
 */
case class Student(id:             Long,
                   names:          Names,
                   date:           String,
                   educationLevel: EducationLevel,
                   educationForm:  EducationForm,
                   instituteName:  String,
                   bookNum:        String,
                   insuranceNum:   String,
                   email:          String)

