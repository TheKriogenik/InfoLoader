package edu.kriogenik.infoloader.parsing.parsers

import edu.kriogenik.infoloader.model.{Employee, Names}
import edu.kriogenik.infoloader.parsing.Parser.ParseResult
import edu.kriogenik.infoloader.parsing.parsers.NamesParser._
import edu.kriogenik.infoloader.parsing.parsers.CsvParsers.parseString
import edu.kriogenik.infoloader.parsing.Parser
import edu.kriogenik.infoloader.typeclasses.Applicative._
import edu.kriogenik.infoloader.typeclasses.Functor._
import edu.kriogenik.infoloader.typeclasses.Read

/**
 * Объект, содержащий реализацию парсера для объекта типа [[edu.kriogenik.infoloader.model.Employee]].
 */
object EmployeeParser {

  /**
   * Реализация типа класса [[edu.kriogenik.infoloader.typeclasses.Read]]
   * для получения объекта класса [[edu.kriogenik.infoloader.model.Employee]]
   * из строки.
   */
  implicit val parseEmployee: Read[ParseResult[Employee]] = (target: String) => parser(target)

  /**
   * Каррированная функция создания объекта класса [[edu.kriogenik.infoloader.model.Employee]].
   */
  private val model = Parser(Employee.curried)

  /**
   * Реализация парсера для класса [[edu.kriogenik.infoloader.model.Employee]].
   */
  private lazy val parser = model <*> long <*> names <*> string <*> string <*> string <*> string

  /**
   * Базовый парсер для получения строк.
   */
  private val string = Parser{Read[(String, String)].read _}

  /**
   * Парсер для получения имен.
   */
  private val names = Parser{Read[(String, Names)].read _}

  /**
   * Парсер для получения целочисленных значений.
   */
  private val long = string fmap (x => x.toLong)

}
