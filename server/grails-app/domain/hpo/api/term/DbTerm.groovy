package hpo.api.term

import groovy.sql.GroovyRowResult
import org.monarchinitiative.phenol.ontology.data.Term
import hpo.api.gene.DbGene

/**
 * Minimal term representation in a table, doesn't have all info on a term currenlty but info useful
 * for search
 */
class DbTerm {

  String ontologyId
  String name
  boolean isObsolete
  String definition
  String comment
  /**
   * the number of child terms including self
   */
  Integer numberOfChildren


  static constraints = {
    definition nullable: true
    comment nullable: true
    numberOfChildren nullable: true
  }
  static mapping = {
    name index: 'name_index'
    definition type: 'text'
    comment type: 'text'
    version false
  }

  static hasMany = [dbTermPaths: DbTermPath,
                    dbGenes: DbGene,
                    dbTermParents: DbTermRelationship,
                    dbTermChildren: DbTermRelationship,
                    dbTermSynonyms: DbTermSynonym]

  static mappedBy = [dbTermParents: 'termParent', dbTermChildren: 'termChild']

  Set<DbTermPath> dbTermPaths = [] as Set<DbTermPath>
  Set<DbGene> dbGenes = [] as Set<DbGene>
  Set<DbTermRelationship> dbTermParents = [] as Set<DbTermRelationship>
  Set<DbTermRelationship> dbTermChildren = [] as Set<DbTermRelationship>

  DbTerm() {}

  DbTerm(Term term) {
    name = term.name
    definition = term.definition
    comment = term.comment
    ontologyId = term.id().toString()
    isObsolete = term.isObsolete()
  }

  DbTerm(GroovyRowResult result){
    name = result.name
    numberOfChildren = result.number_of_children
    ontologyId = result.ontology_id
  }

  static transients = ['children', 'parents']

  /**
   * gets the term (DbTerm) children objects of this term object
   * @return
   */
  Set<DbTerm> getChildren()
  {
    dbTermParents*.termChild
  }

  /**
   * gets the term (DbTerm) parents objects of this term object
   * @return
   */
  Set<DbTerm> getParents()
  {
    dbTermChildren*.termParent
  }

  boolean equals(o) {
    if (this.is(o)) return true
    if (getClass() != o.class) return false

    DbTerm dbTerm = (DbTerm) o

    if (isObsolete != dbTerm.isObsolete) return false
    if (comment != dbTerm.comment) return false
    if (definition != dbTerm.definition) return false
    if (name != dbTerm.name) return false
    if (ontologyId != dbTerm.ontologyId) return false

    return true
  }

  int hashCode() {
    int result
    result = (ontologyId != null ? ontologyId.hashCode() : 0)
    result = 31 * result + (name != null ? name.hashCode() : 0)
    result = 31 * result + (isObsolete ? 1 : 0)
    result = 31 * result + (definition != null ? definition.hashCode() : 0)
    result = 31 * result + (comment != null ? comment.hashCode() : 0)
    return result
  }
}
