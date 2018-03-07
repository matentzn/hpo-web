package hpo.api

class UrlMappings {

  //exclude the paths to:
  // 1) Tell grails to avoid routing for these static resources, but instead have direct access to these via http://<host>/app/<filename>
  // 2) Allow routing of /app/* to angular app (index.html), but ignore these paths
  static excludes = [
    '/app/*.html',
    '/app/*.ico',
//    '/app/*.js',
//    '/app/*.css',
//    '/app/*.map',
    '/app/assets/*.png'
  ]

  static mappings = {
    delete "/$controller/$id(.$format)?"(action: "delete")
    get "/$controller(.$format)?"(action: "index")
    get "/$controller/$id(.$format)?"(action: "show")
    post "/$controller(.$format)?"(action: "save")
    put "/$controller/$id(.$format)?"(action: "update")
    patch "/$controller/$id(.$format)?"(action: "patch")

    "/"(uri: '/app/index.html')
    "/app/"(uri: '/app/index.html')
    "/app/**"(uri: '/app/index.html')

    "/app/$name**.js"(controller: 'contentTypeAsset', action:'setCharEncoding') {
      contentType = 'text/javascript; charset=UTF-8'
      extention = 'js'
    }
    "/app/$name**.map"(controller: 'contentTypeAsset', action:'setCharEncoding') {
      contentType = 'application/json; charset=UTF-8'
      extention = 'map'
    }
    "/app/assets/$name**.js"(controller: 'contentTypeAsset', action:'setCharEncoding') {
      contentType = 'text/javascript; charset=UTF-8'
      extention = 'js'
    }
    "/app/$name**.css"(controller: 'contentTypeAsset', action:'setCharEncoding') {
      contentType = 'text/css; charset=UTF-8'
      extention = 'css'
    }

    "/api/hpo/search"(controller: 'hpoSearch', action: 'searchAll')
    "/api/hpo/search/gene"(controller: 'hpoGeneDetails', action: 'searchGene')
    "/api/hpo/search/disease"(controller: 'hpoDiseaseDetails', action: 'searchDisease')
    "/api/hpo/term/$id"(controller: 'hpoTerm', action: 'searchTerm')
    "/api/hpo/term/$id/genes"(controller: 'hpoTerm', action: 'searchGenesByTerm')
    "/api/hpo/term/$id/diseases"(controller: 'hpoTerm', action: 'searchDiseasesByTerm')

    "500"(view: '/error')
    "404"(view: '/notFound')
  }
}

