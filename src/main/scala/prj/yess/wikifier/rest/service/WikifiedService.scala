package prj.yess.wikifier.rest.service

import java.util

import edu.illinois.cs.cogcomp.wikifier.common.GlobalParameters
import edu.illinois.cs.cogcomp.wikifier.inference.InferenceEngine
import edu.illinois.cs.cogcomp.wikifier.models.{LinkingProblem, Mention, ReferenceInstance, WikiCandidate}
import org.apache.commons.lang.StringEscapeUtils
import org.http4s.HttpService
import org.http4s.circe.jsonOf
import org.http4s.dsl.{->, /, Ok, POST, Root}
import prj.yess.wikifier.rest.model.{Candidate, TopDisambiguation, WikifierEntities, WikifierEntity}
import io.circe.syntax._
import io.circe.generic.auto._
import _root_.io.circe.Json
import gurobi.GRBEnv

import scala.collection.JavaConverters._
import org.http4s.MediaType._
import org.http4s.dsl._
import org.http4s.headers._
import org.http4s.circe._

case class Form(text: String)

object WikifiedService {

  val service = HttpService {
    case req @ POST -> Root / "entities" =>
      for {
        form <- req.as(jsonOf[Form])
        resp <- Ok(getEntities(form.text)).putHeaders(`Content-Type`(`application/json`))
      } yield (resp)

    case req @ POST -> Root / "shortEntities" =>
      for {
        form <- req.as(jsonOf[Form])
        resp <- Ok(getShortEntities(form.text)).putHeaders(`Content-Type`(`application/json`))
      } yield (resp)
  }

  def getEntities(text: String): Json = {
    println("Init getEntities Service")
    getJson(analyzeProblem(text))
  }

  def getShortEntities(text: String): Json = {
    println("Init getShortEntities Service")
    getShortJson(analyzeProblem(text))
  }

  def analyzeProblem(text: String): LinkingProblem = {
    val t1 = System.currentTimeMillis()

    val inference = new InferenceEngine(false)
    System.out.println("Processing text")
    System.out.println("Constructing the problem...")
    val ta = GlobalParameters.curator.getTextAnnotation(text)
    val problem = new LinkingProblem("", ta, new util.ArrayList[ReferenceInstance]())

    println("Done constructing the problem; running the inference")
    inference.annotate(problem, null, false, false, 0)
    println("Done  running the inference");

    val t2 = System.currentTimeMillis()
    println(s"Total time for analyze: ${t2-t1} ms")

    problem
  }

  def getJson(problem: LinkingProblem): Json =  {
    val entities = new WikifierEntities(scala.collection.mutable.MutableList[WikifierEntity]())

    problem.components.asScala.foreach(entity =>
      if (entity.topCandidate != null) entities.addEntity(createEntity(entity)))

    entities.asJson
  }

  def getShortJson(problem: LinkingProblem): Json =  {
    val entities = new WikifierEntities(scala.collection.mutable.MutableList[WikifierEntity]())

    problem.components.asScala.foreach(entity =>
      if (entity.topCandidate != null) entities.addEntity(createShortEntity(entity)))

    println(entities.asJson)
    entities.asJson
  }

  def createEntity(entity: Mention): WikifierEntity =
    new WikifierEntity(StringEscapeUtils.escapeXml(entity.surfaceForm.replace('\n', ' ')), entity.charStart,
      (entity.charStart + entity.charLength),
      entity.linkerScore, entity.getLinkability(), Some(getTopDisambiguation(entity)), Some(createCandidates(entity)))

  def createShortEntity(entity: Mention): WikifierEntity =
    new WikifierEntity(StringEscapeUtils.escapeXml(entity.surfaceForm.replace('\n', ' ')), entity.charStart,
      (entity.charStart + entity.charLength),
      entity.linkerScore, entity.getLinkability(), Some(getTopDisambiguation(entity)), None)

  def createCandidates(entity: Mention): scala.collection.mutable.MutableList[Candidate] = {
    val candidates = scala.collection.mutable.MutableList[Candidate]()
    entity.candidates.get(0).asScala.foreach(candidate => candidates += getCandidate(candidate))
    candidates
  }

  def getTopDisambiguation(entity: Mention) =
    new TopDisambiguation(StringEscapeUtils.escapeXml(entity.topCandidate.titleName),
      "http://en.wikipedia.org/wiki/" + StringEscapeUtils.escapeXml(entity.topCandidate.titleName),
      entity.topCandidate.getTid(), entity.topCandidate.rankerScore, getTitleCategories(entity.topCandidate.titleName))

  def getTitleCategories(title: String) =
    GlobalParameters.getCategories(title).mkString("-")

  def getCandidate(c: WikiCandidate) =
    new Candidate(StringEscapeUtils.escapeXml(c.titleName), c.getTid(), c.rankerScore)

}