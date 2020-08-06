package edu.kriogenik.infoloader.parsing.parsers

import edu.kriogenik.infoloader.typeclasses.Read

/**
 * Объект, содержащий базовые функции для чтения строк формата CSV.
 */
object CsvParsers {

  /**
   * Тип результата при случае, когда разделителем является символ `(")`.
   */
  type DoubleQuotedString = String

  /**
   * Реализация получения части строки до разделителя в случае когда
   * разделителем явялется символ `(")`.
   */
  implicit val parseDoubleQuotedString: Read[(String, DoubleQuotedString)] = {
    case "" => ("", "")
    case target => target.head match {
      case '"' =>
        val res = target.drop(1).takeWhile(_ != '"')
        val newValue = target.drop(res.length + 2)
        (newValue, res)
      case _ => parseString.read(target)
    }
  }

  /**
   * Реализация получения части строки до разделителя в случае когда
   * разделителем явялется символ `(")`.
   */
  implicit val parseString: Read[(String, String)] = {
    case "" => ("", "")
    case target =>
      val res = target.takeWhile(_ != ';')
      val newValue = target.drop(res.length + 1)
      (newValue, res)
  }

}
