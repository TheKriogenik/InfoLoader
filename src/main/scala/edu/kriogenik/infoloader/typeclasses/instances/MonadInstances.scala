package edu.kriogenik.infoloader.typeclasses.instances

import edu.kriogenik.infoloader.typeclasses.Monad

object MonadInstances {

  implicit val monadListInstance: Monad[List] = new Monad[List] {
    override def bind[A, B](fa: List[A])(f: A => List[B]): List[B] =
      for{
        x <- fa
        y <- f(x)
      } yield y
  }

}
