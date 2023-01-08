package com.soungho.studyolle.mail

import mu.KotlinLogging
import org.springframework.context.annotation.Profile
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.stereotype.Component
import java.io.InputStream
import javax.mail.internet.MimeMessage

val logger = KotlinLogging.logger {}

@Profile("local")
@Component
class ConsoleMailSender: JavaMailSender {

    override fun send(mimeMessage: MimeMessage) {
        TODO("Not yet implemented")
    }

    override fun send(vararg mimeMessages: MimeMessage?) {
        TODO("Not yet implemented")
    }

    override fun send(mimeMessagePreparator: MimeMessagePreparator) {
        TODO("Not yet implemented")
    }

    override fun send(vararg mimeMessagePreparators: MimeMessagePreparator?) {
        TODO("Not yet implemented")
    }

    override fun send(simpleMessage: SimpleMailMessage) {
        //        log.info(simpleMailMessage.getText())
        logger.info { simpleMessage.text }
    }

    override fun send(vararg simpleMessages: SimpleMailMessage?) {
    }

    override fun createMimeMessage(): MimeMessage {
        TODO("Not yet implemented")
    }

    override fun createMimeMessage(contentStream: InputStream): MimeMessage {
        TODO("Not yet implemented")
    }
}