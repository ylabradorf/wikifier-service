# Rest Service for Illinois Wikifier
This is a Rest Service for [Illinois Wikifier](https://cogcomp.cs.illinois.edu/page/software_view/Wikifier). The REST service has some endpoints to use Wikifier functionalities.

All of the REST service endpoints has as input a JSON with the following structure:
```sh
{
	"text": "This is the text"
}
```

Endpoints definition:
- POST - wikifierService/entities: return an array of Wiki Entities given a text. The result is a Json with the following structure:
```sh
  "wikifierEntities" : [
    {
      "entitySurfaceForm" : "Word or phrase",
      "entityTextStart" : int,
      "entityTextEnd" : int,
      "linkerScore" : double
      "linkability" : double
      "topDisambiguation" : {
        "wikiTitle" : "Word",
        "url" : "http://en.wikipedia.org/wiki/Word",
        "wikiTitleID" : int,
        "rankerScore" : double
        "attributes" : "words separeted by \t"
      },
      "disambiguationCandidates" : [
        {
          "wikiTitle" : "Word",
          "wikiTitleID" : int,
          "rankerScore" : double
        },...
      ]
    }, .....
   }
```

- POST - wikifierService/shortEntities: return an array of Wiki Entities given a text, without candidates. The result is a Json with the following structure:
```sh
"wikifierEntities" : [
    {
      "entitySurfaceForm" : "Word or phrase",
      "entityTextStart" : int,
      "entityTextEnd" : int,
      "linkerScore" : double
      "linkability" : double
      "topDisambiguation" : {
        "wikiTitle" : "Word",
        "url" : "http://en.wikipedia.org/wiki/Word",
        "wikiTitleID" : int,
        "rankerScore" : double
        "attributes" : "words separeted by \t"
      },
      "disambiguationCandidates": null
    }, .....
   }
```

This project uses a [wikifier-3.0-jar-with-dependencies.jar](https://cogcomp.cs.illinois.edu/page/download_view/Wikifier) library, which you can find it in the Wikifier2013 project, folder dist.
