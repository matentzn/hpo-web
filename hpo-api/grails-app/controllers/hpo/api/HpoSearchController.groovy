package hpo.api

class HpoSearchController {
    static responseFormats = ['json']

    HpoSearchService hpoSearchService

    def search(String q) {
        render(view: 'search', model: [resultMap: hpoSearchService.search(q)])
    }
}
