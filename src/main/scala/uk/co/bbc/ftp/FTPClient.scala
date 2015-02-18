package uk.co.bbc.ftp

import org.apache.commons.net.ftp.FTPClient

object FTPClient {
  def apply (): FTP =
    new FTP(new FTPClient)
}
