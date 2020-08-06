package edu.kriogenik.infoloader.messaging.dto

import edu.kriogenik.infoloader.model.{Names, Student}
import edu.kriogenik.infoloader.model.{EducationLevel, EducationForm}
import edu.kriogenik.infoloader.typeclasses.Show._
import edu.kriogenik.infoloader.typeclasses.Read._

/**
 * Класс, содержащий представоение класса [[edu.kriogenik.infoloader.model.Student]],
 * предназначенного для транспортировки.
 * @param id
 *           Индивидуальный номер студента.
 * @param firstName
 *           Имя студента.
 * @param lastName
 *           Фамилия студента.
 * @param patronymic
 *           Отчество студента.
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
case class StudentDto(id:             Long,
                      firstName:      String,
                      lastName:       String,
                      patronymic:     String,
                      date:           String,
                      educationLevel: String,
                      educationForm:  String,
                      instituteName:  String,
                      bookNum:        String,
                      insuranceNum:   String,
                      email:          String)

/**
 * Объект-компаньон, содержащий полезный реализаци классов типов.
 */
object StudentDto{

  /**
   * Неявный класс-обертка для удобного преобразования сущности в ее внешнее представление.
   * @param x
   *          Объект для преобразования.
   * @param tc
   *           Реализация класса типов для конвертации, неявный параметр.
   */
  implicit class DtoOps(x: Student)(implicit tc: Dto[Student, StudentDto]){

    /**
     * Функция преобразования сущности.
     * @return
     *         Внешнее представление сущности [[x]].
     */
    def toDto: StudentDto = tc.toDto(x)

  }

  /**
   * Неявный класс-обертка для удобного преобразования внешнего представления сущности в внутреннее.
   * @param x
   *          Объект для преобразования.
   * @param tc
   *           Реализация класса типов для конвертации, неявный параметр.
   */
  implicit class EntityOps(x: StudentDto)(implicit tc: Dto[Student, StudentDto]){

    /**
     * Функция преобразования сущности.
     * @return
     *         Внутренне представление сущности [[x]].
     */
    def fromDto: Student = tc.fromDto(x)

  }

  /**
   * Реализация класса типов [[edu.kriogenik.infoloader.messaging.dto.Dto]].
   */
  implicit val dtoStudent: Dto[Student, StudentDto] = new Dto[Student, StudentDto] {

    override def toDto(x: Student): StudentDto = StudentDto(
      x.id,
      x.names.firstName,
      x.names.lastName,
      x.names.patronymic,
      x.date,
      x.educationLevel.show,
      x.educationForm.show,
      x.instituteName,
      x.bookNum,
      x.insuranceNum,
      x.email)

    override def fromDto(x: StudentDto): Student = Student(
      x.id,
      Names(x.firstName, x.lastName, x.patronymic),
      x.date,
      x.educationLevel.read[EducationLevel],
      x.educationForm.read[EducationForm],
      x.instituteName,
      x.bookNum,
      x.insuranceNum,
      x.email)
  }

}
