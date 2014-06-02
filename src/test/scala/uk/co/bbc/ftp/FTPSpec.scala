package uk.co.bbc.ftp

import org.scalatest._

class FTPSpec extends FlatSpec with Matchers {

  "An FTP Client" should "connect" in {

    val client: FTP = Client()

    client.connectWithAuth("ftp.mozilla.org", "anonymous", "")

    client.connected should equal(true)

    for(file <- client.listFiles(".")) {
      println(file.getName)
    }
  }
}

