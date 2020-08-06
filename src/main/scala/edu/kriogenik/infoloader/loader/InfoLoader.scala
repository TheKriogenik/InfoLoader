package edu.kriogenik.infoloader.loader

/**
 * Интерфейс, позволяющий получить некий набор информации.
 * @tparam A
 *           Тип получаемой информации.
 */
trait InfoLoader[A] {

  /**
   * Функция, используемая для получения информации из некоего
   * достоверного источника.
   * @return
   *         При ошибке возвращает Left[Exception] с описанием ошибки.
   *         В противном случае Right[A].
   */
  def getInfo: Either[Exception, A]

}

