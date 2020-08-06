package edu.kriogenik.infoloader.parsing

import edu.kriogenik.infoloader.typeclasses.{Applicative, Functor}
import edu.kriogenik.infoloader.typeclasses.Functor.FunctorOps

/**
 * Класс, хранящий в себе функцию разбора строки.
 * @param parse
 *           Функция, преобразующая строку на объект класса `A`
 *           и остаток от преобразования.
 * @tparam A
 *           Тип получаемого в результате разбора объекта.
 */
case class Parser[A](parse: String => (String, A)){

  /**
   * Функция, применяющая аргумент-строку к хранящейся функции разбора строки.
   * @param input
   *         Строка для обработки.
   * @return
   *         Возвращает Right[A] при успешном разборе. Failure в противном случае.
   */
  def apply(input: String): Either[Exception, A] = {
    try{
      val (xs, res) = parse(input)
      xs match{
        case _ if xs.isEmpty => Right(res)
        case _               => Left(new Exception("Caused error when parsing line \"" + input + "\"!"))
      }
    } catch {
      case e: Exception => Left(new Exception("Caused error when parsing line \"" + input + "\"!"))
    }
  }

}

/**
 * Объект, хранящий функции создания объекта данного класса и реализации
 * некоторых классов типов.
 */
object Parser{

  /**
   * Тип для более удобного описания типа получаемого объекта.
   * @tparam A
   *           Тип результата разбора.
   */
  type ParseResult[A] = Either[Exception, A]

  /**
   * Конструктор для аргумента, который будет являться
   * результатом вне зависимости от входных данных.
   * @param a
   *          Аргумент-результат.
   * @tparam A
   *          Тип результата.
   * @return
   *          Объект с функцией, возвращающей аргумент `a`.
   */
  def apply[A](a: A): Parser[A] = Parser{ (_, a) }

  /**
   * Реализация класса типов [[edu.kriogenik.infoloader.typeclasses.Applicative]].
   */
  implicit val applicative: Applicative[Parser] = new Applicative[Parser] {

    override def pure[A](x: A): Parser[A] = Parser(x)

    override def apply[A, B](fa: Parser[A])(f: Parser[A => B]): Parser[B] = Parser{ x: String=>
      val (res, newState) = f parse x
      val newParser = fa fmap newState
      newParser parse res
    }
  }

  /**
   * Реализация класса типов [[edu.kriogenik.infoloader.typeclasses.Functor]].
   */
  implicit val functor: Functor[Parser] = new Functor[Parser] {

    override def fmap[A, B](fa: Parser[A])(f: A => B): Parser[B] = Parser{ x: String =>
      val (xs, res) = fa parse x
      (xs, f(res))
    }

    override def pure[A](a: A): Parser[A] = Parser{ (_, a) }
  }

}
