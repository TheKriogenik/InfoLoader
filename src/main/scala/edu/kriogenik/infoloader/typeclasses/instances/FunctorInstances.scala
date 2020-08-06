package edu.kriogenik.infoloader.typeclasses.instances

import edu.kriogenik.infoloader.typeclasses.Functor

/**
 * Объект, содержащий реализацию класса типа
 * [[edu.kriogenik.infoloader.typeclasses.Functor]]
 * для часто используемых классов стандартной библиотеки.
 */
object FunctorInstances {

  /**
   * Реализация функтора для `Option`.
   */
  implicit val optionFunctor: Functor[Option] = new Functor[Option]{
    override def fmap[A, B](fa: Option[A])(f: A => B): Option[B] = fa match{
      case Some(x) => Option(f(x))
      case None    => None
    }

    override def pure[A](a: A): Option[A] = Option(a)
  }

  /**
   * Реализация функтора для `List`.
   */
  implicit val listFunctor: Functor[List] = new Functor[List]{
    override def fmap[A, B](fa: List[A])(f: A => B): List[B] = fa map f

    override def pure[A](a: A): List[A] = List(a)
  }

}
