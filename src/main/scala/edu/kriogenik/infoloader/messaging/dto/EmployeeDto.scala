package edu.kriogenik.infoloader.messaging.dto

import edu.kriogenik.infoloader.model.{Employee, Names}

/**
 * Класс, содержащий представоение класса [[edu.kriogenik.infoloader.model.Employee]],
 * предназначенного для транспортировки.
 * @param id
 *           Порядковый номер работника.
 * @param firstName
 *           Имя работника.
 * @param secondName
 *           Фамилия работника.
 * @param patronymic
 *           Отчество работника.
 * @param date
 *           Дата рождения работника.
 * @param position
 *           Должность работника.
 * @param department
 *           Отдел/институт, к которому приписан работник.
 * @param insuranceNum
 *           Номер страховки.
 */
case class EmployeeDto(id:           Long,
                       firstName:    String,
                       secondName:   String,
                       patronymic:   String,
                       date:         String,
                       position:     String,
                       department:   String,
                       insuranceNum: String)

/**
 * Объект-компаньон, содержащий полезный реализаци классов типов.
 */
object EmployeeDto{

  /**
   * Неявный класс-обертка для удобного преобразования сущности в ее внешнее представление.
   * @param x
   *          Объект для преобразования.
   * @param tc
   *           Реализация класса типов для конвертации, неявный параметр.
   */
  implicit class DtoOps(x: Employee)(implicit tc: Dto[Employee, EmployeeDto]){

    /**
     * Функция преобразования сущности.
     * @return
     *         Внешнее представление сущности [[x]].
     */
    def toDto: EmployeeDto = tc.toDto(x)

  }

  /**
   * Неявный класс-обертка для удобного преобразования внешнего представления сущности в внутреннее.
   * @param x
   *          Объект для преобразования.
   * @param tc
   *           Реализация класса типов для конвертации, неявный параметр.
   */
  implicit class EntityOps(x: EmployeeDto)(implicit tc: Dto[Employee, EmployeeDto]){

    /**
     * Функция преобразования сущности.
     * @return
     *         Внутренне представление сущности [[x]].
     */
    def fromDto: Employee = tc.fromDto(x)

  }

  /**
   * Реализация класса типа [[edu.kriogenik.infoloader.messaging.dto.Dto]].
   */
  implicit val dtoEmployee: Dto[Employee, EmployeeDto] = new Dto[Employee, EmployeeDto]{

    override def toDto(x: Employee): EmployeeDto = EmployeeDto(
      x.id, x.names.firstName, x.names.lastName, x.names.patronymic, x.date, x.position, x.department, x.insuranceNum)

    override def fromDto(x: EmployeeDto): Employee = Employee(
      x.id, Names(x.firstName, x.secondName, x.patronymic), x.date, x.position, x.department, x.insuranceNum)

  }

}
