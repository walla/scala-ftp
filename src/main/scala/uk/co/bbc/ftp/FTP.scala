package uk.co.bbc.ftp

import org.apache.commons.net.ftp._

class FTP(client: FTPClient = new FTPClient) {

  def connect(host: String): Boolean = {
    client.connect(host)
    connected
  }

  def connectWithAuth(host: String, username: String, password: String): Boolean = {
    client.connect(host)
    client.login(username, password)
    connected
  }

  def listFiles(path: String): Seq[(String, FTPFile)] = {
    client.listFiles(path).map { f => (f.getName, f) }
  }

  // Predicate functions

  def connected: Boolean = client.isConnected
}

object Client {

  def apply() = new FTP()
  def apply(client: FTPHTTPClient) = new FTP(client)
}