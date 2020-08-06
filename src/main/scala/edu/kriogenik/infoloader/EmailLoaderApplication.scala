package edu.kriogenik.infoloader

import edu.kriogenik.infoloader.loader.InfoLoader
import edu.kriogenik.infoloader.messaging.producer.EmployeeMessageProducer
import edu.kriogenik.infoloader.model.{Employee, Names}
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.PropertySource
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.data.redis.connection.Message
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * Класс, содержащий исходную конфигурацию контейнера IoC для приложения.
 */
@PropertySource(Array("classpath:application.properties"))
@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
class EmailLoaderApplication

/**
 * Объект, содержащий точку входа.
 */
object EmailLoaderApplication{

  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[EmailLoaderApplication], args: _*)

  }

}
