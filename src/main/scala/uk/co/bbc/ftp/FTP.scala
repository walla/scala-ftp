package uk.co.bbc.ftp

import org.apache.commons.net.ftp._

class FTP(client: FTPClient) {

  def connectWithAuth(host: String, username: String, password: String) = {
    client.connect(host)
    client.login(username, password)
  }

  def connected: Boolean = client.isConnected

  def listFiles(path: String): Array[FTPFile] = {
    client.listFiles(path)
  }
}

object Client {

  val client = new FTPClient

  def apply () = new FTP(client)
}