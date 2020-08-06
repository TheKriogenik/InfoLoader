package edu.kriogenik.infoloader.typeclasses.instances

import edu.kriogenik.infoloader.typeclasses.Applicative
import edu.kriogenik.infoloader.typeclasses.Functor.FunctorOps
import edu.kriogenik.infoloader.typeclasses.Monad.MonadOps
import edu.kriogenik.infoloader.typeclasses.instances.FunctorInstances._
import edu.kriogenik.infoloader.typeclasses.instances.MonadInstances._

/**
 * Объект, содержащий реализацию класса типа
 * [[edu.kriogenik.infoloader.typeclasses.Applicative]]
 * для часто используемых классов стандартной библиотеки.
 */
object ApplicativeInstances {

  /**
   * Реализация аппликативного функтора для `Option`.
   */
  implicit val applicativeOption: Applicative[Option] = new Applicative[Option]{

    override def pure[A](x: A): Option[A] = Some(x)

    override def apply[A, B](fa: Option[A])(fab: Option[A => B]): Option[B] = (fa, fab) match {
      case (None, _) => None
      case (_, None) => None
      case (Some(value), Some(f)) => pure(f(value))
    }
  }

  /**
   * Реализация аппликативного функтора для `List`.
   */
  implicit val applicativeList: Applicative[List] = new Applicative[List]{

    override def pure[A](x: A): List[A] = List(x)

    override def apply[A, B](fa: List[A])(f: List[A => B]): List[B] = f >>= (fa fmap _)
  }

}
