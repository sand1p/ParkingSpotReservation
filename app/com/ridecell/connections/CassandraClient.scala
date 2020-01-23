package com.ridecell.connections

import com.datastax.driver.core.Cluster

object CassandraClient {
  private val serverIP = "10.0.1.53"
  val cluster = Cluster.builder().addContactPoint(serverIP).build()
  def getSession(keyspace: String) = cluster.connect(keyspace)
}
