package edu.kriogenik.infoloader

import edu.kriogenik.infoloader.loader.InfoLoader
import edu.kriogenik.infoloader.model.{EducationForm, EducationLevel, Employee, Names, Student}
import edu.kriogenik.infoloader.loader.mail.extractor.StudentMessageExtractor._
import edu.kriogenik.infoloader.loader.mail.extractor.EmployeeMessageExtractor._
import edu.kriogenik.infoloader.loader.mail.extractor.MessageExtractor._
import edu.kriogenik.infoloader.messaging.producer.MessageProducer
import javax.mail.Message
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class MailLoader(client: InfoLoader[Message],
                 studentProducer: MessageProducer[Student],
                 employeeProducer: MessageProducer[Employee]) {

  private final val log = LoggerFactory.getLogger(this.getClass)

  @Scheduled(cron = "0 0 0 ? * 5/7")
  def start(): Unit = {
    client.getInfo match {
      case Left(value)  => log.error("MESSAGE NOT RECEIVED: " + value)
      case Right(value) =>
        val students  = value.extract[List[Student]]
        val employees = value.extract[List[Employee]]
        (students.size, employees.size) match {
          case (0, _) => log.info("Something goes wrong with data from message!")
          case (_, 0) => log.info("Something goes wrong with data from message!")
          case _      =>
            val endedStudent = Student(id = -1, Names("", "", ""), "",
              EducationLevel.NOTHING_LEVEL, EducationForm.NOTHING_FORM, "", "", "", "")
            val endedEmployee = Employee(id = -1, Names("", "", ""), "", "", "", "")
            log.info("Start sending actual info to system...")
            log.info("Sending students...")
            (students  ::: List(endedStudent))  foreach studentProducer.send
            log.info("Sending employees...")
            (employees ::: List(endedEmployee)) foreach employeeProducer.send
            log.info("All data sended!")
        }
    }
  }

}
