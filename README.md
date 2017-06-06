# Rest Service for Illinois Wikifier
This is a Rest Service  that wraps some of [Illinois Wikifier](https://cogcomp.cs.illinois.edu/page/software_view/Wikifier)
functionalities through wikifierService/entities and wikifierService/shortEntities endpoints.

## Input / Output
All endpoints has as input a JSON with the following structure:
```sh
{
	"text": "This is the text"
}
```

Endpoints definition:
- POST - wikifierService/entities: Based on the value of the input "text" field  returns an array of Wiki Entities given a text. The result is a Json with the following structure:
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

- POST - wikifierService/shortEntities: Based on the value of the input "text" field  it returns an array of Wiki Entities given a text, without candidates. The result is a Json with the following structure:
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

## Configuration
Like in the Illinois Wikifier Project, you can use different predefined configurations to run Wikifier, you must change this configuration in the Main.scala class.

The configurations are:
* configs/STAND_ALONE_NO_INFERENCE.xml: Use this configuration if you do not have Gurobi and wish to run Wikifier for baseline performance.
* configs/STAND_ALONE_GUROBI.xml: Use this configuration if you have installed Gurobi for inference. You must import the goruby library into the project and it has to be before wikifier-3.0-jar-with-dependencies.jar in the project dependencies. **_Remember, requires Gurobi to be installed first_**.

## External Dependencies
* This project uses a [wikifier-3.0-jar-with-dependencies.jar](https://cogcomp.cs.illinois.edu/page/download_view/Wikifier) library, which you can find it in the Wikifier2013 project, folder dist.
* If you use STAND_ALONE_GUROBI configuration, you need import gurobi.jar in the project. Gurobi is free for academic uses as of today [2017/06/06].

## Data Files
You need to copy the following folders, from [Illinois Wikifier Project](https://cogcomp.cs.illinois.edu/page/software_view/Wikifier) to the project data folder:
* Lucene4Index
* Models
* NER_Data
* NESimdata
* OtherData
* WikiData
* WordNet
