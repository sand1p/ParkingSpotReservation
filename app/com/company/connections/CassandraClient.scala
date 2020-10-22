package com.ridecell.connections

import com.datastax.driver.core.{Cluster, Session}
import javax.inject.Singleton

@Singleton
class CassandraClient {
  private val serverIP = "10.0.1.53"
  private val cluster = Cluster.builder().addContactPoint(serverIP).build()

  def getSession(keyspace: String): Session = cluster.connect(keyspace)

}
