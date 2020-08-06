package edu.kriogenik.infoloader.typeclasses.instances

import edu.kriogenik.infoloader.typeclasses.Traversing

/**
 * Объект, содержащий реализацию класса типа
 * [[edu.kriogenik.infoloader.typeclasses.Traversing]]
 * для часто используемых классов стандартной библиотеки.
 */
object TraversingInstances {

  /**
   * Тип для удобного определения типа-контейнера Either, где первый
   * аргумент - исключение.
   * @tparam A
   *           Тип результата успешных вычислений.
   */
  type EitherException[A] = Either[Exception, A]

  /**
   * Реализация для списка, содержащего Either как внутренний контейнер,
   * хранящего исключение при ошибочных результатах.
   */
  implicit val ListEitherTraversable: Traversing[List, EitherException] = new Traversing[List, EitherException] {
    override def traverse[A](target: List[Either[Exception, A]]): Either[Exception, List[A]] = {
      println("Target contains error: " + target.find(p => p.isLeft))
      target find {_.isLeft} match {
        case Some(_) => Left(new Exception)
        case None    => Right(target.foldRight(List[A]()){case (x, acc) => x match{
          case Right(value) => value :: acc
          case Left(_)      => throw new Exception()
        }})
      }
    }
  }

}
