package edu.kriogenik.infoloader.loader.mail

import java.util.{Properties => JProperties}

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * Класс, хранящий в себе некоторые настроечные данные
 * для почтового клиента.
 *
 * @param protocol
 *                 Название протокола подключения.
 * @param host
 *             Адресс почтового ящика.
 * @param port
 *             Порт почтового сервера.
 * @param timeout
 *                Время между попытками подключения.
 */

@Component
case class Properties(@Value("${mail.protocol}")
                      protocol: String,
                      @Value("${mail.host}")
                      host: String,
                      @Value("${mail.port}")
                      port: String,
                      @Value("${mail.timeout}")
                      timeout: String)

object Properties{

  /**
   * Неявный метод для представления данных, содержащихся в данном класса,
   * в формате класса [[java.util.Properties]]. Требуется в связи с тем, что только с этим
   * форматом работает почтовый клиент.
   * @param props
   *              Объект с настроечными данными.
   * @return
   *         Настроечные данные, записанные в стандартном классе для хранения настроек.
   */
  def jProperties(implicit props: Properties): JProperties = new JProperties(){
    put("mail.store.protocol", props.protocol)
    put("mail.imap.host",      props.host)
    put("mail.imap.port",      props.port)
    put("mail.imap.timeout",   props.timeout)
  }

}
