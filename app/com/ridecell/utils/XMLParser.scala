package com.ridecell.utils

import scala.xml.NodeSeq

class XMLParser(body: NodeSeq) {

  def get(key: String): Option[String] = {
    (body \\ key).headOption.map { value =>
      value.text.trim
    }
  }


}
