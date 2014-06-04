package uk.co.bbc.ftp

import org.apache.commons.net.ftp._
import scala.util.Try
import scala.io.Source.fromInputStream
import java.io.{File, FileOutputStream, InputStream}

class FTP(client: FTPClient) {

  def login(username: String, password: String) = Try {
    client.login(username, password)
  }

  def connected: Boolean = client.isConnected

  def connect(host: String) = Try {
    client.connect(host)
    client.enterLocalPassiveMode()
  }

  def disconnect(): Unit = client.disconnect()

  /**
   * Utility method for testing a connection that disconnects automatically
   */
  def canConnect(host: String): Boolean = {
    client.connect(host)
    val connectionWasEstablished = connected
    client.disconnect()
    connectionWasEstablished
  }

  def listFiles(dir: Option[String]): Array[FTPFile] = dir match {
    case Some(d) => client.listFiles(d)
    case None    => client.listFiles
  }

  /**
   * Make a connection to a given host and try to login
   */
  def connectWithAuth(host: String,
                      username: String = "anonymous",
                      password: String = "") : Try[Boolean] = {
    for {
      connection <- connect(host)
      login      <- login(username, password)
    } yield login
  }

  def extractNames(f: Option[String] => Array[FTPFile]) = {
    f(None).map(_.getName).toSeq
  }

  def cd(path: String) = client.changeWorkingDirectory(path)

  /* Return a sequence of files in the current directory */
  def filesInCurrentDirectory: Seq[String] = extractNames(listFiles)

  /* Download a file as a stream that can be pushed directly to S3 */
  def downloadFileStream(remote: String): InputStream = {
    val stream = client.retrieveFileStream(remote)
    client.completePendingCommand() // make sure it actually completes!!
    stream
  }

  /* Download a single file i.e downloadFile("data.csv") */
  def downloadFile(remote: String): Boolean = {
    val os = new FileOutputStream(new File(remote))
    client.retrieveFile(remote, os)
  }

  /* Given a file name read the file content as a string */
  def streamAsString(stream: InputStream): String = {
    fromInputStream(stream).getLines().mkString("\n")
  }
}

object Client {
  def apply () = new FTP(new FTPClient)

}