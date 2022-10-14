package hpo.api.term

class DbTermSynonym {

  DbTerm dbTerm
  String synonym

  static mapping = {
    version false
  }

  static belongsTo = [dbTerm: DbTerm]

  DbTermSynonym(DbTerm term, String syn){
    dbTerm = term
    synonym = syn
  }

}
