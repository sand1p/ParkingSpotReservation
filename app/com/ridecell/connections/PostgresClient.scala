package com.ridecell.connections


import java.sql.Connection

import javax.inject._
import play.db._

import play.api.Configuration

/*
 * Copyright (C) Lightbend Inc. <https://www.lightbend.com>
 */

import scala.concurrent.ExecutionContext.global
@Singleton
class PostgresClient @Inject()(config: Configuration, database: Database) {
  def getConnection: Connection = {
    database.getConnection()
  }
}