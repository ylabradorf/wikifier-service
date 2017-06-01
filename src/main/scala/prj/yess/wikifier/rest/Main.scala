package prj.yess.wikifier.rest

import edu.illinois.cs.cogcomp.wikifier.common.GlobalParameters
import org.http4s.server.blaze._
import prj.yess.wikifier.rest.service.WikifiedService

object Main extends App {
  GlobalParameters.loadConfig("configs/STAND_ALONE_NO_INFERENCE.xml")

  val builder = BlazeBuilder.bindHttp(8080, "localhost")
    .mountService(WikifiedService.service, "/wikifier_rest/")

  val server = builder.run.awaitShutdown()
}