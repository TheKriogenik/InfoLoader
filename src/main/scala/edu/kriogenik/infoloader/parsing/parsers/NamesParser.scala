package edu.kriogenik.infoloader.parsing.parsers

import edu.kriogenik.infoloader.model.Names
import edu.kriogenik.infoloader.parsing.parsers.CsvParsers.parseString
import edu.kriogenik.infoloader.typeclasses.Read

/**
 * Объект, содержащий функцию преобразования строки в объект
 * класса [[edu.kriogenik.infoloader.model.Names]].
 */
object NamesParser {

  /**
   * Реализация получения объекта класса
   * [[edu.kriogenik.infoloader.model.Names]] из заданной строки
   * в формате `фамилия имя отчество`.
   */
  implicit val parseNames: Read[(String, Names)] = { target =>
    val (xs, x) = Read[(String, String)].read(target)
    val res = x.split(' ').filterNot(_.isEmpty).toList match{
      case firstName :: Nil => Names(firstName, "", "")
      case lastName :: firstName :: Nil => Names(lastName, firstName, "")
      case lastName :: firstName :: patronymic :: Nil => lastName match {
        case "ван" => Names(patronymic, s"$lastName $firstName", "")
        case _     => Names(firstName, lastName, patronymic)
      }
      case xs => Names(xs(2), s"${xs.head} ${xs(1)}", xs(3))
    }
    (xs, res)
  }

}
