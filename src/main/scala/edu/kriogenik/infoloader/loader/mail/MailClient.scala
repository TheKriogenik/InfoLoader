package edu.kriogenik.infoloader.loader.mail

import edu.kriogenik.infoloader.loader.InfoLoader
import javax.mail.{Folder, Message, Session}
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import scala.util.{Failure, Success, Try}
import Properties.jProperties

/**
 * Класс, позволяющий получить информацию с почтового ящика.
 * @param properties
 *            Объект с информацией о подключении.
 * @param userName
 *            Имя пользователя для получения доступа к почтовому ящику.
 * @param password
 *            Пароль пользователя для получения доступа к ящику.
 * @param storeFolder
 *            Папка, в которой требуется искать сообщение.
 */
@Component(value = "client")
class MailClient(implicit properties: Properties,
                 @Value("${mail.user}")
                 val userName: String,
                 @Value("${mail.pass}")
                 val password: String,
                 @Value("${mail.store.folder}")
                 val storeFolder: String) extends InfoLoader[Message] {

  override def getInfo: Either[Exception, Message] = {
    val msg = for {
      s      <- Try(Session.getDefaultInstance(jProperties, null))
      store  <- Try(s.getStore(properties.protocol))
      _      <- Try(store.connect(userName, password))
      folder <- Try(store.getFolder(storeFolder))
      _      <- Try(folder.open(Folder.READ_ONLY))
      count  <- Try(folder.getMessageCount)
      msg    <- Try(folder.getMessage(count))
    } yield msg
    msg match {
      case Success(x: Message)   => Right(x)
      case Failure(e: Exception) => Left(e)
    }
  }

}

