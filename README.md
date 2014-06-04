# Scala FTP

A small library for working with FTP in Scala

```scala

object E {

  private val client: FTP = FTPClient() // create a new FTP client instance

  def downloadFileExample() : Unit = {
    client.connectWithAuth("ftp.mozilla.org", "anonymous", "")

    client.cd("pub")

    if (client.filesInCurrentDirectory.contains("README")) {
      client.downloadFile("README")
    }

    client.disconnect()
  }
}

```