package prj.yess.wikifier.rest.model

case class TopDisambiguation (wikiTitle: String, url: String, wikiTitleID: Int, rankerScore: Double, attributes: String)

case class Candidate(wikiTitle: String, wikiTitleID: Int, rankerScore: Double)

case class WikifierEntity (entitySurfaceForm: String, entityTextStart: Int, entityTextEnd: Int, linkerScore: Double,
                           linkability: Double, topDisambiguation: Option[TopDisambiguation],
                           disambiguationCandidates: Option[scala.collection.mutable.MutableList[Candidate]])

case class WikifierEntities (wikifierEntities: scala.collection.mutable.MutableList[WikifierEntity]) {
  def addEntity(entity: WikifierEntity): Unit = {
    wikifierEntities += entity
  }
}
